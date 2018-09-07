package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.util.Map;

public class Main extends Application {

    private Button start_querry_but;
    private TextArea querry_ta;
    private TextArea querry_result_ta;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene_ = new Scene(root, 300, 400);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene_);
        primaryStage.show();



        
        System.out.println("othe edit");


        System.out.println("my edit 2");
        System.out.println("my edit");


        start_querry_but = (Button) scene_.lookup("#start_querry_but");
        querry_ta = (TextArea) scene_.lookup("#querry_ta");
        querry_result_ta = (TextArea) scene_.lookup("#querry_result_ta");

        querryResult = new SimpleStringProperty("");
        querry_result_ta.textProperty().bindBidirectional(querryResult);

        start_querry_but.setOnAction(new EventHandler< ActionEvent >(){
            @Override
            public void handle(ActionEvent event) {
                current_querry=querry_ta.getText().toString();//"SELECT `name` AS `names` FROM `hasandb`.`brothers_table`;";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        querry();
                    }
                }).start();

            }
        });



    }
    private StringProperty querryResult;
    private String current_querry = "";
    String table = "";
    public void querry(){
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;

        String sample_querry =  current_querry;
        String create_table_quary;
        try {
            if(conn ==null) {
                conn =
                        DriverManager.getConnection("jdbc:mysql://HW7-PC/test?" + "user=hasan&password=password");
            }
            // Do something with the Connection

            //System.out.println("connection ok");
            stmt = conn.createStatement();
            stmt.executeQuery(sample_querry);
            //System.out.println("querry ok");
            rs = stmt.getResultSet();
            table ="";
            while(rs.next()){
                //System.out.print(String.valueOf(rs.getInt(1))+"   ");
                //System.out.println(String.valueOf(rs.getString(1))+"   ");
                //System.out.print(String.valueOf(rs.getInt(3))+"   ");
                //System.out.println(String.valueOf(rs.getDouble(4)));


                table += String.valueOf(rs.getInt(1)) + "  ";
                table += String.valueOf(rs.getString(2)) + "  ";
                table += String.valueOf(rs.getInt(3)) + "  ";
                table += String.valueOf(rs.getInt(4)) + "\n";


            }

            querryResult.set(table);
            //Platform.runLater(()->{querryResult.set(table);});



            System.out.println("Querry done successfully");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }

        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException sqlEx) { } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore
                stmt = null;
            }
        }

    }



    public static void main(String[] args) {
        launch(args);
    }
}
