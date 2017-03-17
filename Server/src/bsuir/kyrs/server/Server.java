package bsuir.kyrs.server;

import bsuir.kyrs.connection.ConnectionPool;
import bsuir.kyrs.dao.exception.DAOException;
import bsuir.kyrs.server.handler.RequestHandler;
import bsuir.kyrs.util.ResourceManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private final ExecutorService threadPool;
    private final ServerSocket serverSocket;
    private final StopServerMonitor monitor;
    private static final AtomicInteger clients = new AtomicInteger();
    private final ResourceManager svrRes;
    private static boolean run = false;


    public static void disconnectClient(){
        System.out.println("Отключился клиент. Общее количество: " + clients.decrementAndGet());
    }

    /**
     * Конструктор, создающий сервер с настройками из файла и слушателей сервера
     * @throws IOException если создать сокет не удалось
     */
    public Server() throws IOException {
        svrRes = new ResourceManager("server");
        int corePoolSize = Integer.parseInt(svrRes.getValue("server.corePoolSize"));
        int maximumPoolSize = Integer.parseInt(svrRes.getValue("server.maximumPoolSize"));
        long keepAliveTime = Long.parseLong(svrRes.getValue("server.keepAliveTime"));
        TimeUnit unit = TimeUnit.valueOf(svrRes.getValue("server.timeUnit"));
        int queueSize = Integer.parseInt(svrRes.getValue("server.queueSize"));
        int serverPort = Integer.parseInt(svrRes.getValue("server.port"));


        threadPool = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTime, unit,
                new ArrayBlockingQueue<>(queueSize));//TODO наличие очереди не даёт создаться новым клиентам

        serverSocket = new ServerSocket(serverPort);
        monitor = new StopServerMonitor();

        System.out.println("Запущен сервер на минимум " + corePoolSize + " соединения");
        System.out.println("Запущен сервер на максимум " + maximumPoolSize + " соединений");
        System.out.println("Порт сервера " + serverPort);
    }

    /**
     * Запускает сервер
     * @throws IOException если запустить сервер не удалось
     * @throws IllegalStateException если сервер уже запущен
     */
    public void startupServer() throws IOException{
        if(run){
            throw new IllegalStateException();
        }
        run = true;

        try {
            ConnectionPool.getInstance();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        //запускаем монитор слушать прекращение работы сервера
        new Thread(monitor).start();

        while(run){
            final Socket socket = serverSocket.accept();
            threadPool.submit(new RequestHandler(socket));
            System.out.println("Подклчился новый клиент. Общее количество " + clients.incrementAndGet());
        }

        //остановка пула потоко (новые запросы пользователей больше не обрабатываются)
        threadPool.shutdown();

        //ожидание завершения сессии уже открытых соединений
        while(!threadPool.isTerminated()){
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();//TODO переделать
            }
        }

        try {
            ConnectionPool.getInstance().closeAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        serverSocket.close();
        System.out.println("Конец");
    }

    /**
     * Останавливает сервер
     */
    public void shutdown(){
        run = false;
    }


    private class StopServerMonitor extends Thread {
        private ServerSocket stopSocket;

        public StopServerMonitor(){
            setDaemon(true);
            setName("stop monitor");
            try {
                stopSocket = new ServerSocket(9001); //TODO исправить задани порта
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run(){
            try {
                Socket socket = stopSocket.accept();
                run = false;
                socket.close();
                stopSocket.close();
            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}

