package chatify.application;

/* imports */
import java.io.IOException;
import java.sql.Statement;
import java.util.logging.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ApplicationC {
    
    @FXML
    private Label usernameLabel;
    @FXML
    private ListView<?> messageContainer;
    @FXML
    private TextField sendMsgInput;
    @FXML
    private Button sendMsgButton;

    /* sonstiges */
    private final static String VIEWNAME = "ApplicationV.fxml";
    private static Statement statement;
    private static Stage stage;
    private static String username2;
    
    // fxml anzeigen
    public static void show(Stage stage, Statement statement, String username, String email) {
        try {
            username2 = username;
            // view und controller erstellen
            FXMLLoader loader = new FXMLLoader(ApplicationC.class.getResource(VIEWNAME));
            Parent root = (Parent) loader.load();

            // scene erstellen
            Scene scene = new Scene(root);
            stage=null;
            // stage verwenden oder neue erzeugen
            if (stage == null) {
                stage = new Stage();
            }
            stage.setScene(scene);
            stage.setTitle("CHATIFY! Register");
            // controller erstellen
            ApplicationC applicationC = (ApplicationC) loader.getController();
            // datenbankzugriff merken
            applicationC.statement = statement;
            System.out.println(username);
            //staticUsername.setText("Test"+username);
            // view anzeigen
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ApplicationC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with " + VIEWNAME + "!");
            ex.printStackTrace(System.err);
            System.exit(1);
        } catch (Exception ex) {
            Logger.getLogger(ApplicationC.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.err);
            System.exit(2);
        }
    }
    
    @FXML
    public void changeText(ActionEvent event) {
        //System.out.println(labelUsername + " | " + staticUsername);
        usernameLabel.setText(username2);
    }

}
