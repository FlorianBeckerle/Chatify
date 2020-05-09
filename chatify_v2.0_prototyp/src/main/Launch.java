package main;

import java.sql.*;
import java.util.logging.*;
import javafx.application.Application;
import javafx.stage.Stage;
import chatify.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

public class Launch extends Application {
    
    public static Stage stage = null;
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/chatify/views/loginV.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        try {
            String url = "jdbc:derby://localhost:1527/chatifyDb";
            String user = "app";
            String pwd = "app";
            Connection connection = DriverManager.getConnection(url, user, pwd);
            Statement statement = connection.createStatement();
            
            loginC.show(stage, statement);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Launch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
