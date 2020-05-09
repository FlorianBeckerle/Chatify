package chatify.loginregister;

// imports
import chatify.application.ApplicationC;
import java.io.IOException;
import java.sql.Statement;
import java.util.logging.*;
import java.util.regex.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import chatify.models.User;

public class LoginRegisterC {

    // register
    @FXML
    private TextField inputUsernameR;
    @FXML
    private TextField inputPasswordR;
    @FXML
    private TextField inputPasswordRepeatR;
    @FXML
    private TextField inputEmailR;
    @FXML
    private Button registerUserBtn;
    @FXML
    private Pane contentRegister;
    
    // register error
    @FXML
    public Label errorUsernameR;
    @FXML
    private Label errorPasswordR;
    @FXML
    private Label errorPasswordRepeatR;
    @FXML
    private Label errorEmailR;
    
    // login
    @FXML
    private TextField inputUsernameL;
    @FXML
    private TextField inputPasswordL;
    @FXML
    private Button loginUserBtn;
    @FXML
    private Pane contentLogin;
    
    // login error
    @FXML
    private Label errorUsernameL;
    @FXML
    private Label errorPasswordL;
    
    // sonstiges
    @FXML
    private Label registerBtn;
    @FXML
    private Label passwordForgotBtn;
    @FXML
    private CheckBox inputAGBR;
    @FXML
    private Button closeWindowBtn;
    @FXML
    private Button minimizeWindowBtn;
    @FXML
    private Button backToLoginBtn;
    
    // sonstiges
    private final static String VIEWNAME = "LoginRegisterV.fxml";
    private static Statement statement;
    private static Stage stage;
    public User current;
    private boolean isLogin = true;
    
    public void resetBindings(){
        inputUsernameR.textProperty().unbindBidirectional(current.nameProperty());
        inputEmailR.textProperty().unbindBidirectional(current.emailProperty());
        inputPasswordR.textProperty().unbindBidirectional(current.passwordProperty());
    } 
    
    // show funktion, zeigt die fxml an
    public static void show(Stage stage, Statement statement) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginRegisterC.class.getResource(VIEWNAME)); // view und controller erstellen
            
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);                                                  // scene erstellen
            if (stage == null) {                                                            // stage verwenden oder neue erzeugen
                stage = new Stage();
            }
            stage.setScene(scene);
            stage.setTitle("CHATIFY! Register");
            LoginRegisterC LoginRegisterC = (LoginRegisterC) loader.getController();        // controller erstellen
            LoginRegisterC.statement = statement;                                           // datenbankzugriff merken
            LoginRegisterC.init();
            stage.show();                                                                  // view anzeigen
        } catch (IOException ex) {
            Logger.getLogger(LoginRegisterC.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Something wrong with " + VIEWNAME + "!");
            ex.printStackTrace(System.err);
            System.exit(1);
        } catch (Exception ex) {
            Logger.getLogger(LoginRegisterC.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.err);
            System.exit(2);
        }
    }
    
    private void init(){
        setCurrent(new User());
        inputUsernameR.textProperty().bindBidirectional(current.nameProperty());
        inputEmailR.textProperty().bindBidirectional(current.emailProperty());
        inputPasswordR.textProperty().bindBidirectional(current.passwordProperty());
    }

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    // switch to register
    @FXML
    private void switchToRegister(MouseEvent event) {
        contentLogin.setVisible(false);
        contentRegister.setVisible(true);
        backToLoginBtn.setVisible(true);
    }

    // switch to login
    @FXML
    private void switchToLogin(ActionEvent event) {
        contentRegister.setVisible(false);
        contentLogin.setVisible(true);
        backToLoginBtn.setVisible(false);
    }

    // login user
    @FXML
    private void loginUser(ActionEvent event) {
    }

    // register user
    @FXML
    public void registerUser(ActionEvent event) {
        try {
            System.out.println("#########USER#########");
            System.out.println("Username: " + inputUsernameR.getText());
            System.out.println("Passwort: " + inputPasswordR.getText());
            System.out.println("Passwort wiederholen: " + inputPasswordRepeatR.getText());
            System.out.println("EMail: " + inputEmailR.getText());
            current.registerVerify(inputUsernameR.getText(),
                                   inputPasswordR.getText(), 
                                   inputPasswordRepeatR.getText(), 
                                   inputEmailR.getText(), 
                                   
                                   errorUsernameR,
                                   errorPasswordR,
                                   errorPasswordRepeatR,
                                   errorEmailR
                                   );
            current.createNewUser(inputUsernameR.getText(), inputEmailR.getText(), inputPasswordR.getText(), statement);
            
            resetBindings();
            
            ApplicationC.show(stage, statement, inputUsernameR.getText(), inputEmailR.getText());
        } catch (Exception ex) {
            System.out.println("Fehler:");
            Logger.getLogger(LoginRegisterC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}