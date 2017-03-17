package bsuir.kyrs.server.handler;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.command.CommandManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

public class RequestHandler implements Callable<Void> {
    private final Socket socket;

    private boolean flag;
    public static final String REQUEST_HANDLER = "requesthandler";

    public RequestHandler(Socket socket){
        this.socket = socket;
    }


    public void disconnectClient(){
        flag = false;
    }

    @Override
    public Void call() throws IOException, ClassNotFoundException{
        try(ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            flag = true;
            while(flag){
                Request request = (Request) in.readObject();
            request.setAttribute(REQUEST_HANDLER, this);

                Response response = CommandManager.getInstance().execute(request);

                out.writeObject(response);
            }
        } finally{
            socket.close();
        }
        return null;
    }
}
