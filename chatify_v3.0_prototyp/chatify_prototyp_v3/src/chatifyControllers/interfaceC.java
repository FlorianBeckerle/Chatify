package chatifyControllers;

/* imports */
import java.io.IOException;
import java.sql.Statement;
import java.util.logging.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class interfaceC {

    
    /* fxml variablen */
    @FXML
    private Text staticUsername;
    
    
    /* sonstiges */
    private final static String VIEWNAME = "/chatifyViews/interfaceV.fxml";
    private static Statement statement;
    
    
    // fxml anzeigen
    public static void show(Stage stage, Statement statement) {
        try {
            // view und controller erstellen
            FXMLLoader loader = new FXMLLoader(interfaceC.class.getResource(VIEWNAME));
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
            interfaceC interfaceC = (interfaceC) loader.getController();
            
            // datenbankzugriff merken
            interfaceC.statement = statement;
            
            // view anzeigen
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(interfaceC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with " + VIEWNAME + "!");
            ex.printStackTrace(System.err);
            System.exit(1);
        } catch (Exception ex) {
            Logger.getLogger(interfaceC.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.err);
            System.exit(2);
        }
    }

}
