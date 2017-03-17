package bsuir.kyrs;
import bsuir.kyrs.server.Server;

import java.io.IOException;


public class StartServer {
    public static void main(String[] args){
        try{
            Server server = new Server();
            server.startupServer();
        } catch(IOException e){
            System.out.println("Сервер не запущен");
        }
    }
}
