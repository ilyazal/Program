package bsuir.kyrs.connection;

import bsuir.kyrs.dao.exception.DAOException;
import bsuir.kyrs.util.ResourceManager;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



public class ConnectionPool {
    private BlockingQueue<java.sql.Connection> freeConnections;
    private BlockingQueue<java.sql.Connection> busyConnections;
    private static Lock lock = new ReentrantLock();
    private static ConnectionPool instance;

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/work";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";


    public static ConnectionPool getInstance() throws DAOException {
        lock.lock();
        try{
            if(instance == null){
                instance = new ConnectionPool();
            }
        } finally{
            lock.unlock();
        }
        return instance;
    }

    private ConnectionPool() throws DAOException {
        ResourceManager dbResMng = new ResourceManager("db");
        int poolSize = Integer.parseInt(dbResMng.getValue("db.poolsize"));
        freeConnections = new ArrayBlockingQueue<>(poolSize);
        busyConnections = new ArrayBlockingQueue<>(poolSize);

        try {
            Class.forName(DB_DRIVER);

            for(int i = 0; i < poolSize; i++){
                java.sql.Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER ,DB_PASSWORD);

                freeConnections.add(connection);
            }
        } catch(ClassNotFoundException | SQLException e){
            throw new DAOException(e);
        }
    }

    public java.sql.Connection getFreeConnection() {
        java.sql.Connection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.add(connection);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        return connection;
    }

    public void dispose(java.sql.Connection connection) throws DAOException {
        if(busyConnections.contains(connection)){
            try {
                freeConnections.put(connection);
                busyConnections.remove(connection);
            } catch(InterruptedException e){
               e.printStackTrace();
            }
        } else {
            throw new DAOException();
        }
    }

    public void closeAll() {
        try{
            for(java.sql.Connection freeConnection : freeConnections){
                freeConnection.close();
            }
            for(java.sql.Connection busyConnection : busyConnections){
                busyConnection.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}


