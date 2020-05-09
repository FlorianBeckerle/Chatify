package chatify.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Launch;

public class loginC {
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField inputUsername;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private Label btnPasswordForgot;
    @FXML
    private Button btnLogin;
    @FXML
    private Label btnRegister;
    @FXML
    private Button btnCloseLogin;
    @FXML
    private Button btnMiniLogin;
    @FXML
    private Pane content;

    private final static String VIEWNAME = "loginV.fxml";
    private static Statement statement;
    
    private double xOffSet = 0;
    private double yOffSet = 0;
   
    
    
    
    private void makeStageDrabable() {
        parent.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        parent.setOnMouseDragged((event) ->{
            Launch.stage.setX(event.getScreenX() - xOffSet);
            Launch.stage.setY(event.getScreenY() - yOffSet);
            Launch.stage.setOpacity(0.8f);
        });
        parent.setOnDragDone((event) ->{
            Launch.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((event) ->{
            Launch.stage.setOpacity(1.0f);
        });
    }
    
    @FXML
    private void closeApplication(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void minimizeApplication(ActionEvent event) {
      
    }
    
    @FXML
    private void openRegister(MouseEvent event) throws IOException {
        Parent register = FXMLLoader.load(getClass().getResource("/chatify/views/registerV.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(register);
    }
    
    // fxml anzeigen
    public static void show(Stage stage, Statement statement) {
        try {
            // view und controller erstellen
            FXMLLoader loader = new FXMLLoader(loginC.class.getResource(VIEWNAME));
            Parent root = (Parent) loader.load();
            
            // scene erstellen
            Scene scene = new Scene(root);
            
            // stage verwenden oder neue erzeugen
            if (stage == null) {
                stage = new Stage();
            }
            stage.setScene(scene);
            stage.setTitle("CHATIFY! Register");
            
            // controller erstellen
            loginC loginC = (loginC) loader.getController();
            
            // datenbankzugriff merken
            loginC.statement = statement;
            
            // view anzeigen
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(loginC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with " + VIEWNAME + "!");
            ex.printStackTrace(System.err);
            System.exit(1);
        } catch (Exception ex) {
            Logger.getLogger(loginC.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.err);
            System.exit(2);
        }
    }

    

}
