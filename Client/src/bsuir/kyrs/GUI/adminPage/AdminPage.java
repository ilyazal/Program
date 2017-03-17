package bsuir.kyrs.GUI.adminPage;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.Export;
import bsuir.kyrs.bean.entity.Import;
import bsuir.kyrs.command.constant.Cmd;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class AdminPage implements Initializable {
    private  Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    final Tooltip tooltip = new Tooltip();

 @FXML
    private Button add_export;

    @FXML
    private TableColumn<Integer,Import> id;
    @FXML
    private TableColumn<String,Import> called;
    @FXML
    private TableColumn<String,Import> material;
    @FXML
    private TableColumn<Integer,Import> quantity;
    @FXML
    private TableColumn<Date,Import> date;
    @FXML
    private TableColumn<String,Import> factory;

    @FXML
    private TableColumn<Integer,Import> money;

    @FXML
    private  TableView<Import> tables;
    @FXML
    private TableView<Export> tables_exp;
    @FXML
    private TableColumn<Integer,Export> ide;
    @FXML
    private TableColumn<String,Export> callede;
    @FXML
    private TableColumn<String,Export> materiale;
    @FXML
    private TableColumn<Integer,Export> quantitye;
    @FXML
    private TableColumn<Date,Export> datee;
    @FXML
    private TableColumn<Integer,Export> moneye;
    @FXML
    private TableColumn<String,Export> factorye;
    @FXML
    private TextField delete_export;
    @FXML
    private TextField delete_import;

    @FXML
    private TextField calledi;
    @FXML
    private TextField materiali;
    @FXML
    private TextField quantityi;
    @FXML
    private TextField factoryi;

    @FXML
    private TextField moneyi;

    @FXML
    private DatePicker date2i;
    @FXML
    private TextField calledex;
    @FXML
    private TextField materialex;
    @FXML
    private TextField quantityex;
    @FXML
    private TextField factoryex;

    @FXML
    private TextField moneyex;

    @FXML
    private DatePicker date2ex;


    @FXML
    private void addImport(){
        if(calledi.getText().isEmpty() ){
            calledi.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        if(materiali.getText().isEmpty() ){
            materiali.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        if(quantityi.getText().isEmpty() ){
            quantityi.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        if(date2i.getValue() == null){
            date2i.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        if(factoryi.getText().isEmpty() ){
            factoryi.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        if(moneyi.getText().isEmpty() ){
            moneyi.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        System.out.println("Делаем выборку всех данных таблин экспорта");
        try{
            Request request = new Request();
            request.setCommand(Cmd.ADD_IMPORT);
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String called_text;
            String material_text;
            int quantity;
            String factor;
            int Monye;
            LocalDate Date;
            Date = date2i.getValue();
            called_text=calledi.getText();
            material_text=materiali.getText();
            factor=factoryi.getText();
            Monye= Integer.parseInt(moneyi.getText());
            quantity= Integer.parseInt(quantityi.getText());
            request.setAttribute("date2",Date);
            request.setAttribute("called",called_text);
            request.setAttribute("material",material_text);
            request.setAttribute("quantity",quantity);
            request.setAttribute("factory",factor);
            request.setAttribute("monye",Monye);
            date2i.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            quantityi.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            factoryi.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            materiali.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            calledi.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            moneyi.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            out.writeObject(request);
            Response response = (Response) in.readObject();
            if (!response.isError()) {
                calledi.setText("");
                materiali.setText("");
                quantityi.setText("");
                factoryi.setText("");
                date2i.getEditor().clear();
                moneyi.setText("");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO обработку исключения
        }
        addTableImport();
    }




    public void addTableExport(){
        System.out.println("Делаем выборку всех данных таблиц экспорта");
        try{
            Request request = new Request();
            request.setCommand(Cmd.SELECT_ONLY_EXPORT);
            Export export=new Export();
            request.setAttribute("exports", export);
            out.writeObject(request);
            Response response = (Response) in.readObject();

            if (!response.isError()) {
                ObservableList<Export> list = FXCollections.observableArrayList();
                ((List<Export>) response.getParameter("exports")).forEach(list :: add);
                tables_exp.setItems(list);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO обработку исключения
    }}

    public  void addTableImport(){
        System.out.println("Делаем выборку всех данных таблин импорта");
        try {
            Request request = new Request();
            request.setCommand(Cmd.SELECT_ONLY_IMPORTS);
            Import imports = new Import();
            request.setAttribute("imports", imports);
            out.writeObject(request);
            Response response = (Response) in.readObject();
            if (!response.isError()) {

                ObservableList<Import> list = FXCollections.observableArrayList();
                ((List<Import>) response.getParameter("imports")).forEach(list :: add);
                tables.setItems(list);

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO обработку исключения
        }
    }

    @FXML
    private void deleteImport(){
        System.out.println("Делаем удаление импорта по иду");
        try {
            Request request = new Request();
            request.setCommand(Cmd.DELETE_IMPORT);
            if (delete_import.getText().isEmpty()){
return;}
            int id;
            id = new Integer (delete_import.getText());
            request.setAttribute("deleteimpr", id );
            out.writeObject(request);
            Response response = (Response) in.readObject();
            if (!response.isError()) {
//                window.close();
                System.out.println("чет не то");
                ObservableList<Import> list = FXCollections.observableArrayList();
              //  ((List<Import>) response.getParameter("delimpr")).forEach(list::add);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO обработку исключения
        }
        addTableImport();
        delete_import.clear();
    }
    @FXML
    private void addExport(){
        if(calledex.getText().isEmpty() ){
            calledex.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        if(materialex.getText().isEmpty() ){
            materialex.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        if(quantityex.getText().isEmpty() ){
            quantityex.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        if(date2ex.getValue() == null){
            date2ex.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        if(factoryex.getText().isEmpty() ){
            factoryex.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        if(moneyex.getText().isEmpty() ){
            moneyex.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}
        System.out.println("Делаем выборку всех данных таблин экспорта");
        try{
            Request request = new Request();
            request.setCommand(Cmd.ADD_EXPORT);
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String called_text;
            String material_text;
            int quantity;
            String factor;
            int Monye;
            LocalDate Date;
            Date = date2ex.getValue();
            called_text = calledex.getText();
            material_text = materialex.getText();
            factor = factoryex.getText();
            Monye= Integer.parseInt(moneyex.getText());
            quantity= Integer.parseInt(quantityex.getText());
            request.setAttribute("date2e",Date);
            request.setAttribute("callede",called_text);
            request.setAttribute("materiale",material_text);
            request.setAttribute("quantitye", quantity);
            request.setAttribute("factory_ex", factor);
            request.setAttribute("monye_ex", Monye);
            date2ex.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            quantityex.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            factoryex.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            materialex.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            calledex.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            moneyex.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            out.writeObject(request);
            Response response = (Response) in.readObject();
            if (!response.isError()) {
                calledex.setText("");
                materialex.setText("");
                quantityex.setText("");
                factoryex.setText("");
                date2ex.getEditor().clear();
                moneyex.setText("");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO обработку исключения
        }

        addTableExport();

    }

    @FXML
    private void deleteExport(){
        System.out.println("Делаем удаление экспорта по иду");

        try {
            Request request = new Request();
            request.setCommand(Cmd.DELETE_EXPORT);
            Import imports = new Import();
            int id;
            id = new Integer (delete_export.getText());
            request.setAttribute("deleteexp", id );
            out.writeObject(request);
            Response response = (Response) in.readObject();
            if (!response.isError()) {
//                window.close();
                System.out.println("чет то");
                ObservableList<Export> list = FXCollections.observableArrayList();
//                ((List<Export>) response.getParameter("delexp")).forEach(list::add);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO обработку исключения
        }
        addTableExport();
        delete_export.clear();
    }

    public void start(Stage window) throws Exception{

    }

    public void setInputs(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        addTableImport();
        addTableExport();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        called.setCellValueFactory(new PropertyValueFactory<>("called"));
        material.setCellValueFactory(new PropertyValueFactory<>("material"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        date.setCellValueFactory(new PropertyValueFactory<>("date2"));
        factory.setCellValueFactory(new PropertyValueFactory<>("factory"));
        money.setCellValueFactory(new PropertyValueFactory<>("sum"));


        ide.setCellValueFactory(new PropertyValueFactory<>("id"));
        callede.setCellValueFactory(new PropertyValueFactory<>("called"));
        materiale.setCellValueFactory(new PropertyValueFactory<>("material"));
        quantitye.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        datee.setCellValueFactory(new PropertyValueFactory<>("date2"));
        moneye.setCellValueFactory(new PropertyValueFactory<>("sum"));
        factorye.setCellValueFactory(new PropertyValueFactory<>("factory"));


        calledex.setPromptText("Название");
        materialex.setPromptText("Вид Материала");
        quantityex.setPromptText("Количество");
        date2ex.setPromptText("MM-dd-yyyy");
        factoryex.setPromptText("Компания");
        moneyex.setPromptText("Сумма $");
        delete_export.setPromptText("ID");

        delete_import.setPromptText("ID");
        calledi.setPromptText("Название");
        materiali.setPromptText("Вид Материала");
        quantityi.setPromptText("Количество");
        date2i.setPromptText("MM-dd-yyyy");
        factoryi.setPromptText("Компания");
        moneyi.setPromptText("Сумма $");

    }
}
