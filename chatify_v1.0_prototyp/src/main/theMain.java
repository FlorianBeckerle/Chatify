package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import register.registerC;

public class theMain extends Application {
    
    @Override
    public void start(Stage stage) {
        try {
            String url = "jdbc:derby://localhost:1527/chatifyDb";
            String user = "app";
            String pwd = "app";
            Connection connection = DriverManager.getConnection(url, user, pwd);
            Statement statement = connection.createStatement();
            
            registerC.show(stage, statement);
        } catch (SQLException ex) {
            Logger.getLogger(theMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
