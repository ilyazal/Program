package bsuir.kyrs.GUI;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Util {

    public static void closeProgram(Socket socket, ObjectInputStream in, ObjectOutputStream out, Stage window) {
        Request request = new Request();
        request.setCommand("sign out");

        try {
            out.writeObject(request);
            Response response = (Response) in.readObject();

            if(response.isError()){
                System.out.println("Невозможно выйти в данный момент"); //TODO переделать
            } else {
                in.close();
                out.close();
                socket.close();
                window.close();
            }
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("Отработал");
    }

}
