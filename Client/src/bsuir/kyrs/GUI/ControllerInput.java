package bsuir.kyrs.GUI;

import bsuir.kyrs.GUI.adminPage.AdminPage;
import bsuir.kyrs.GUI.homePage.HomePage;
import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.User;
import bsuir.kyrs.command.constant.Cmd;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static bsuir.kyrs.GUI.Util.closeProgram;

public class ControllerInput implements Initializable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    final Tooltip tooltip = new Tooltip();
    @FXML
    private Label label;
    @FXML
    private AnchorPane home_page;
    @FXML
    private TextField username_box;
    @FXML
    private TextField password_box;
    @FXML
    private Label invalid_label;

    @FXML
    private boolean ha(ActionEvent event) throws IOException {
        User user1 = new User();
        boolean valid = validate(user1);
        if (!valid) return false;
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomePage.fxml"));
        Parent root = loader.load();
        HomePage controller = loader.getController();
        controller.setInputs(socket, in, out);
        Scene home_page_scene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setOnCloseRequest((e) -> {
            e.consume();
            closeProgram(socket, in, out, app_stage);
        });
        try {
            Request request = new Request();
            request.setCommand(Cmd.SIGN_IN_COMMAND);

            User user = new User();
            user.setUsername(username_box.getText());
            try {
                int pass = Integer.parseInt(password_box.getText());
                user.setPassword(pass);
            } catch (NumberFormatException e) {

                password_box.clear();
                invalid_label.setText("Sorry, invalid credentials");
                return valid;
            }
            request.setAttribute("user", user);


            out.writeObject(request);
            Response response = (Response) in.readObject();

            if (!response.isError()) {
//
                if(((User) response.getParameter("user")).isAdmin()){
                    FXMLLoader loaderadmin=new FXMLLoader(getClass().getResource("/AdminPage.fxml"));
                    Parent rootadmin=loaderadmin.load();
                    AdminPage controllerAdn=loaderadmin.getController();
                    controllerAdn.setInputs(socket,in,out);
                    Scene admin_page_scene=new Scene(rootadmin);
                    Stage app_stage_admin = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_stage.setScene(admin_page_scene);
                    System.out.println(((User) response.getParameter("user")).isAdmin());
                } else{
                app_stage.setScene(home_page_scene);
                    System.out.println("поле юзера");
                    System.out.println(response.getParameter("user"));
                }

            } else {

                password_box.clear();
                invalid_label.setText("Неверный логин или пароль");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO обработку исключения
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tooltip.setText(
                "\n пароль состоит\n" +
                        "минимум из 4 символов\n");

        password_box.setTooltip(tooltip);
    }

    public void setInputs(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    private boolean validate(User user) {
        boolean flag = true;
        if(password_box.getText().isEmpty()){
            password_box.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            flag = false;
        }
        else
            user.setPassword(Integer.parseInt(password_box.getText()));
return flag;
    }
}
