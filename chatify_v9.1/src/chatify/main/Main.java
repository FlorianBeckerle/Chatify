package chatify.main;

import chatify.loginregister.LoginRegisterC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        try {
            // Verbindungsaufbau zur Datenbank
            String url = "jdbc:derby://localhost:1527/chatify_database"; // bitte neue Datenbank erstellen und gleichen Namen nehmen. Wird in Zukunft nicht mehr geändert.
            String user = "app";
            String pwd = "app";
            Connection connection = DriverManager.getConnection(url, user, pwd);
            Statement statement = connection.createStatement();
            
            // Show Statement für Login und Register starten
            LoginRegisterC.show(stage, statement);

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
