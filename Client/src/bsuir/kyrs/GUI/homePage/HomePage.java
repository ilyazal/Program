package bsuir.kyrs.GUI.homePage;


import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.Export;
import bsuir.kyrs.bean.entity.Import;
import bsuir.kyrs.command.constant.Cmd;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Phil
 */
public class HomePage implements Initializable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;


    @FXML
    private TextField filter_box;
    @FXML
    private Label labanalyze;

    @FXML
    private TextField filter_box_exp;

    @FXML
    private TableView<Export> tablexport;

    @FXML
    private TableColumn<Integer,Export> ide;
    @FXML
    private TableColumn<String,Export> callede;
    @FXML
    private TableColumn<String,Export> quantitye;
    @FXML
    private TableColumn<Integer,Export> materiale;
    @FXML
    private TableColumn<Date,Export> date2e;
    @FXML
    private TableColumn<String,Export> factory_exp;
    @FXML
    private TableColumn<Integer,Export> monye_exp;



    @FXML
    private Label invalid_label_exp;

    @FXML
    private Label label_invalid;

    @FXML
    private Label savelabel;


    @FXML
    private TableView<Import> tables;


    final Tooltip tooltip = new Tooltip();

    @FXML
    private TableColumn<Integer,Import> id;
    @FXML
    private TableColumn<String,Import> called;
    @FXML
    private TableColumn<String,Import> quantity;
    @FXML
    private TableColumn<Integer,Import> material;
    @FXML
    private TableColumn<Date,Import> date2;

    @FXML
    private TableColumn<String,Import> factory;
    @FXML
    private TableColumn<Integer,Import> monye;

    @FXML
    private PieChart analyzimp;
    @FXML
    private PieChart analyzexp;





    @FXML
    private void handlefilterexp(ActionEvent event){

        if(filter_box_exp.getText().isEmpty() ){
            filter_box_exp.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}


        System.out.println("Делаем фильтр экспорта по компании");
        try {
            Request request = new Request();
            request.setCommand(Cmd.DO_FILTER_EXPORT);
            String factory;
                factory = filter_box_exp.getText();
            invalid_label_exp.setText("");
            request.setAttribute("filterdateexp", factory );
            filter_box_exp.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            out.writeObject(request);
            Response response = (Response) in.readObject();
            if (!response.isError()) {

                System.out.println("чет не то");
                ObservableList<Export> list = FXCollections.observableArrayList();
                ((List<Export>) response.getParameter("filterexp")).forEach(list ::add);
                tablexport.setItems(list);

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO обработку исключения
        }

    }

    private void handlexport(){
        System.out.println("Делаем выборку всех данных таблиц экспорта");
        try{
            Request request = new Request();
            request.setCommand(Cmd.SELECT_ONLY_EXPORT);
            Export export=new Export();
            request.setAttribute("exports", export);
            out.writeObject(request);
            Response response = (Response) in.readObject();

            if (!response.isError()) {
                System.out.println("не то");
                ObservableList<Export> list = FXCollections.observableArrayList();
                ((List<Export>) response.getParameter("exports")).forEach(list :: add);
                tablexport.setItems(list);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO обработку исключения
        }
    }

    private void handleimport(){

        //Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        System.out.println("Делаем выборку всех данных таблиц импорта");

        try {
            Request request = new Request();
            request.setCommand(Cmd.SELECT_ONLY_IMPORTS);
            Import imports = new Import();
            request.setAttribute("imports", imports);
            out.writeObject(request);
            Response response = (Response) in.readObject();

            if (!response.isError()) {

                System.out.println("то");
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
    private void handlefilter(ActionEvent event){

        if(filter_box.getText().isEmpty() ){
            filter_box.setStyle("-fx-background-color: rgba(255, 0, 0, 0.4);");
            return;}

        System.out.println("Делаем фильтр импорта по компании");

        try {
            Request request = new Request();
            request.setCommand(Cmd.DO_FILTER);
            Import imports = new Import();
            String factory;
            factory = filter_box.getText();
            filter_box.setText("");
            filter_box.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
            request.setAttribute("filterdate", factory );
            out.writeObject(request);
            Response response = (Response) in.readObject();
            if (!response.isError()) {
                System.out.println("не то");
                ObservableList<Import> list = FXCollections.observableArrayList();
                ((List<Import>) response.getParameter("filterimpr")).forEach(list :: add);
                tables.setItems(list);
                tables.getItems();


            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO обработку исключения
        }

    }


      @FXML
      private void updateexport(ActionEvent event){
          System.out.println("Выборка из таблиц экспорта");
          try{
              Request request = new Request();
              request.setCommand(Cmd.SELECT_ONLY_EXPORT);
              Export export=new Export();
              request.setAttribute("exports", export);
              out.writeObject(request);
              Response response = (Response) in.readObject();

              if (!response.isError()) {
                  System.out.println("чет не то");
                  ObservableList<Export> list = FXCollections.observableArrayList();
                  ((List<Export>) response.getParameter("exports")).forEach(list::add);
                  tablexport.setItems(list);
              }
          } catch (IOException | ClassNotFoundException e) {
              e.printStackTrace();
              //TODO обработку исключения
          }
      }

      @FXML
      private void updateimport(ActionEvent event){


          System.out.println("Выборка из таблиц импорта");

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

        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            labanalyze.setText("Анализ данных");
            labanalyze.setAlignment(Pos.TOP_LEFT);
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            called.setCellValueFactory(new PropertyValueFactory<>("called"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("material"));
            material.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            date2.setCellValueFactory(new PropertyValueFactory<>("date2"));
            monye.setCellValueFactory(new PropertyValueFactory<>("sum"));
            factory.setCellValueFactory(new PropertyValueFactory<>("factory"));


            ide.setCellValueFactory(new PropertyValueFactory<>("id"));
            callede.setCellValueFactory(new PropertyValueFactory<>("called"));
            quantitye.setCellValueFactory(new PropertyValueFactory<>("material"));
            materiale.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            date2e.setCellValueFactory(new PropertyValueFactory<>("date2"));
            factory_exp.setCellValueFactory(new PropertyValueFactory<>("factory"));
            monye_exp.setCellValueFactory(new PropertyValueFactory<>("sum"));

        }




    public void setInputs(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        handlexport();
        handleimport();
    }
    @FXML
    public void analyze(ActionEvent actionEvent) {

        int sum=0,sum2=0,saldo=0,turnover=0;
        String strValue,strValue2;
        for(Import value: tables.getItems()){
            sum +=value.getSum();
        }

        for(Export value:tablexport.getItems()){
            sum2 +=value.getSum();
        }

        saldo=sum2-sum;
        turnover=sum2+sum;

        strValue = String.valueOf(sum);
        strValue2 = String.valueOf(sum2);
        String strValue3 =String.valueOf(saldo);
        System.out.println(sum);
        System.out.println(sum2);


        labanalyze.setText("Анализ данных " + "\n" + "Сумма иморта за весь период: " + strValue + "\n" + "Сумма экспорта за весь период: " + strValue2 + "\n" + "Внешнеторговое сальдо: " + saldo + "\n" + "Внешнеторговый оборот: " + turnover);

    }

    @FXML
    private void saveAnalyze() throws FileNotFoundException, UnsupportedEncodingException {
        int sum=0,sum2=0,saldo=0,turnover=0;
        String strValue,strValue2;
        for(Import value: tables.getItems()){
            sum +=value.getSum();
        }

        for(Export value:tablexport.getItems()){
            sum2 +=value.getSum();
        }

        saldo=sum2-sum;
        turnover=sum2+sum;
        System.out.println(sum);
        System.out.println(sum2);

        strValue = String.valueOf(sum);
        strValue2 = String.valueOf(sum2);
        String strValue3 =String.valueOf(saldo);
        PrintWriter writer = new PrintWriter("analyze.txt","UTF-8");
        writer.println("Анализ данных " + "\n" + "Сумма иморта за весь период: " + strValue + "\n" + "Сумма экспорта за весь период: " + strValue2 + "\n" + "Внешнеторговое сальдо: " + saldo + "\n" + "Внешнеторговый оборот: " + turnover);
        savelabel.setText("Анализ сохранен");
        writer.close();
    }
    @FXML
    public void charts (){

        try{
            Request request = new Request();
            request.setCommand(Cmd.SELECT_ONLY_EXPORT);
            Export export=new Export();
            request.setAttribute("exports", export);
            out.writeObject(request);
            Response response = (Response) in.readObject();

            if (!response.isError()) {
                System.out.println("чет не то");
                ObservableList<Export> list = FXCollections.observableArrayList();
                ((List<Export>) response.getParameter("exports")).forEach(list :: add);

        ObservableList<PieChart.Data> export_charts=FXCollections.observableArrayList();
                for (int i = 0; i <list.size() ; i++) {
                    export_charts.add(new PieChart.Data(list.get(i).getFactory(),list.get(i).getSum()));
                }
                analyzexp.setData(export_charts);
    }
} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        charts2();
    }


    public void charts2(){

        try{
            Request request = new Request();
            request.setCommand(Cmd.SELECT_ONLY_IMPORTS);
            Import importc =new Import();
            request.setAttribute("imports", importc);
            out.writeObject(request);
            Response response = (Response) in.readObject();

            if (!response.isError()) {
                System.out.println("чет не то");
                ObservableList<Import> list = FXCollections.observableArrayList();
                ((List<Import>) response.getParameter("imports")).forEach(list :: add);

                ObservableList<PieChart.Data> import_charts=FXCollections.observableArrayList();
                for (int i = 0; i <list.size() ; i++) {
                    import_charts.add(new PieChart.Data(list.get(i).getFactory(), list.get(i).getSum()));
                }
                analyzimp.setData(import_charts);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}