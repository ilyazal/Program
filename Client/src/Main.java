import bsuir.kyrs.GUI.ControllerInput;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static bsuir.kyrs.GUI.Util.closeProgram;

public class Main extends Application {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Main(){
        try {
            socket = new Socket("localhost", 9000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e){
            System.out.println("Не удалось подключиться к серверу");
            //TODO корректное отображение инфы в случае неудачи
        }
    }

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Regiistrarion.fxml"));
        Parent root = loader.load();
        ControllerInput controller = loader.getController();
        controller.setInputs(socket, in, out);
        Scene scene_login = new Scene(root);
        stage.setScene(scene_login);
        stage.show();

        stage.setOnCloseRequest((e) -> {e.consume(); closeProgram(socket,in,out,stage);} );
    }


    public static void main(String[] args) {

        launch(args);
    }
}
