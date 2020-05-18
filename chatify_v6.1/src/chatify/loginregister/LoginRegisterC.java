package chatify.loginregister;

// imports
import chatify.application.ApplicationC;
import java.io.IOException;
import java.sql.Statement;
import java.util.logging.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import chatify.models.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class LoginRegisterC implements Initializable {
 
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
    @FXML
    private MediaView backgroundVideo;
    
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
        inputUsernameL.textProperty().unbindBidirectional(current.nameProperty());
        inputPasswordL.textProperty().unbindBidirectional(current.passwordProperty());
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Media media = new Media("file:///C:/Users/limjo/Desktop/chatify_v6.1/src/chatify/components/background.mp4"); //
        MediaPlayer player = new MediaPlayer(media);
        backgroundVideo.setMediaPlayer(player);
        player.setVolume(0);
        player.play();
    }
    
    // show funktion, zeigt die fxml an
    public static void show(Stage stage, Statement statement) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginRegisterC.class.getResource(VIEWNAME)); // view und controller erstellen
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);                                                  // scene erstellen
            stage.initStyle(StageStyle.UNDECORATED);                                        // titel bar entfernen
            if (stage == null) {                                                            // stage verwenden oder neue erzeugen
                stage = new Stage();
            }
            stage.setScene(scene);
            stage.setTitle("CHATIFY! Register");
            LoginRegisterC loginRegisterC = (LoginRegisterC) loader.getController();        // controller erstellen
            loginRegisterC.statement = statement;                                           // datenbankzugriff merken
            loginRegisterC.init();
            stage.show();                                                                   // view anzeigen
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
        //Anpassen auf Login/Register
        setCurrent(new User());
        if(isLogin == false){
            //setCurrent(new User()); 
            inputUsernameR.textProperty().bindBidirectional(current.nameProperty());
            inputEmailR.textProperty().bindBidirectional(current.emailProperty());
            inputPasswordR.textProperty().bindBidirectional(current.passwordProperty());
        } else {
            inputUsernameL.textProperty().bindBidirectional(current.nameProperty());
            inputPasswordL.textProperty().bindBidirectional(current.passwordProperty());
        }
    }
    
    private void changeBindings(){
        if(isLogin == false){
            resetBindings();
            //setCurrent(new User()); 
            inputUsernameR.textProperty().bindBidirectional(current.nameProperty());
            inputEmailR.textProperty().bindBidirectional(current.emailProperty());
            inputPasswordR.textProperty().bindBidirectional(current.passwordProperty());
        } else {
            resetBindings();
            inputUsernameL.textProperty().bindBidirectional(current.nameProperty());
            inputPasswordL.textProperty().bindBidirectional(current.passwordProperty());
        }
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
        
        isLogin = false;
        changeBindings();
    }

    // switch to login
    @FXML
    private void switchToLogin(ActionEvent event) {
        contentRegister.setVisible(false);
        contentLogin.setVisible(true);
        backToLoginBtn.setVisible(false);
        
        isLogin = true;
        changeBindings();
    
    }

    // login user
    @FXML
    private void loginUser(ActionEvent event) {
        try {
            System.out.println("#########USER#########");
            System.out.println("Username: " + inputUsernameL.getText());
            System.out.println("Passwort: " + inputPasswordL.getText());
            current.loginVerify(statement, inputUsernameL.getText(),inputPasswordL.getText(), errorUsernameL, errorPasswordL);
            //current.createNewUser(statement); //inputUsernameR.getText(), inputEmailR.getText(), inputPasswordR.getText(), 
            
            resetBindings();
            
            ApplicationC.show(stage, statement, inputUsernameL.getText(), inputPasswordL.getText());
        } catch (Exception ex) {
            Logger.getLogger(LoginRegisterC.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            current.createNewUser(statement); //inputUsernameR.getText(), inputEmailR.getText(), inputPasswordR.getText(), 
            
            resetBindings();
            
            ApplicationC.show(stage, statement, inputUsernameR.getText(), inputEmailR.getText());
        } catch (Exception ex) {
            System.out.println("Fehler:");
            Logger.getLogger(LoginRegisterC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeWindowBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeWindow(ActionEvent event) {
        Stage stage = (Stage) closeWindowBtn.getScene().getWindow();
        minimizeWindowBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                stage.setIconified(true);
            }
        });
    }
}