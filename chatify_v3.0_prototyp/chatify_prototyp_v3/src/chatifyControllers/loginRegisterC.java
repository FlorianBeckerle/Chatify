package chatifyControllers;

/* imports */
import java.io.IOException;
import java.sql.Statement;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class loginRegisterC  {

    
    /* fxml variablen */
    @FXML
    private TextField inputUsernameL;
    @FXML
    private TextField inputPasswordL;
    @FXML
    private TextField inputUsernameR;
    @FXML
    private TextField inputPasswordR;
    @FXML
    private TextField inputPasswordRR;
    @FXML
    private TextField inputEmailR;
    @FXML
    private Button loginBtn;
    @FXML
    private Button registerBtn;
    
    
    /* sonstiges */
    private final static String VIEWNAME = "/chatifyViews/loginRegisterV.fxml";
    private static Statement statement;
    
    
    /* button "registrieren" drücken*/
    @FXML
    public void register(ActionEvent event) {
        try {
            System.out.println("#########USER#########");
            System.out.println("Username: " + inputUsernameR.getText());
            System.out.println("Passwort: " + inputPasswordR.getText());
            System.out.println("Passwort wiederholen: " + inputPasswordRR.getText());
            System.out.println("EMail: " + inputEmailR.getText());
            registerVerify();
        } catch (Exception ex) {
            Logger.getLogger(loginRegisterC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // daten prüfen
    private void registerVerify() throws Exception {
        System.out.println("#########FEHLERN#########");
        // USERNAME
        // länge nicht weniger als 3
        if(inputUsernameR.getText().length() < 3) {
            throw new Exception("username fehler: nicht kürzer als 3!");
        }
        // länge nicht mehr als 22
        if(inputUsernameR.getText().length() > 22) {
            throw new Exception("username fehler: nicht länger als 22!");
        }
        // keine sonderzeichen
        String regexUsername = "[a-zA-Z0-9]*";
        Pattern patternUsername = Pattern.compile(regexUsername);
        Matcher matcherUsername = patternUsername.matcher(inputUsernameR.getText());
        if(!matcherUsername.matches()) {
            throw new Exception("username fehler: beinhaltet spezielle symbole!");
        }
        
        // PASSWORT
        // länge nicht weniger als 8
        if(inputPasswordR.getText().length() < 8) {
            throw new Exception("passwort fehler: nicht kürzer als 8!");
        }
        // länge nicht mehr als 50
        if(inputPasswordR.getText().length() > 50) {
            throw new Exception("passwort fehler: nicht länger als 50!");
        }
        // keine sonderzeichen
        String regexPassword = "[a-zA-Z0-9]*";
        Pattern patternPassword = Pattern.compile(regexPassword);
        Matcher matcherPassword = patternPassword.matcher(inputPasswordR.getText());
        if(!matcherPassword.matches()) {
            throw new Exception("passwort fehler: beinhaltet spezielle symbole!");
        }
        // passwort muss gleich sein wie zweite eingabe
        if(!inputPasswordR.getText().equals(inputPasswordRR.getText())) {
            throw new Exception("passwort fehler: stimmt nicht mit der zweiten eingabe überein!");
        }
        
        // EMAIL
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
        Pattern patternEmail = Pattern.compile(regexEmail); 
        Matcher matcherEmail = patternEmail.matcher(inputEmailR.getText());
        if(!matcherEmail.matches()) {
            throw new Exception("email fehler: entspricht nicht der syntax 'mustermann@mail.com'!");
        }
    }
    

    // fxml anzeigen
    public static void show(Stage stage, Statement statement) {
        try {
            // view und controller erstellen
            FXMLLoader loader = new FXMLLoader(loginRegisterC.class.getResource(VIEWNAME));
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
            loginRegisterC loginRegisterC = (loginRegisterC) loader.getController();
            
            // datenbankzugriff merken
            loginRegisterC.statement = statement;
            
            // view anzeigen
             stage.show();
        } catch (IOException ex) {
            Logger.getLogger(loginRegisterC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with " + VIEWNAME + "!");
            ex.printStackTrace(System.err);
            System.exit(1);
        } catch (Exception ex) {
            Logger.getLogger(loginRegisterC.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.err);
            System.exit(2);
        }
    }
    
    
}
