package chatify.application;

/* imports */
import chatify.models.ChatParticipant;
import chatify.models.Chatroom;
import chatify.models.Message;
import chatify.models.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ApplicationC implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private ListView<Message> messageContainer;
    @FXML
    private ListView<User> userContainer;
    @FXML
    private TextField sendMsgInput;
    @FXML
    private Button sendMsgButton;
    @FXML
    private ListView<Chatroom> chatroomsContainer; //Liste aller Server
    @FXML
    private VBox userSettings;
    @FXML
    private VBox serverSettings;
    @FXML
    private Label usernameLabel1;

    /* neuen server anlegen */
    @FXML
    private TextField tfCreateServerName;
    @FXML
    private TextField tfCreateServerPwd;
    @FXML
    private TextField tfCreateServerPwdRe;

    @FXML
    private TextField tfCreateServerUserCount;

    @FXML
    private Button closeWindowBtn;
    @FXML
    private Button minimizeWindowBtn;
    @FXML
    private AnchorPane parent;

    /* User (Email, Name, Passwort) ändern */
    // username
    @FXML
    private TextField tfUsernameNew;
    @FXML
    private Button btChangeUsername;

    // passwort
    @FXML
    private TextField tfCurrentPasswort;
    @FXML
    private TextField tfPasswortNew;
    @FXML
    private TextField tfPasswortNewRe;
    @FXML
    private Button btChangePasswort;

    // email
    @FXML
    private TextField tfEmailNew;
    @FXML
    private Button btChangeEmail;


    // sonstiges
    private final static String VIEWNAME = "ApplicationV.fxml";
    private static Statement statement;
    private static Stage stage;
    private static String username2;

    // user
    private User current;
    private ObservableList<User> currentUserList;

    // chatroom
    private Chatroom currentChatroom;
    private ObservableList<Chatroom> currentChatrooms;

    // message
    private Message currentMessage;
    private ObservableList<Message> currentMessages;
    
    // mediaMenu
    @FXML
    private Button closeMediaMenu;
    
    // news
    @FXML
    private HBox news;
    
    //Server beitreten
    @FXML
    private TextField tfInputServerName;
    @FXML
    private TextField tfInputServerPassword;
    @FXML
    private Button btJoinServer;
    
    
    // stage dragable machen
    private double x = 0;
    private double y = 0;
    @FXML
    private VBox mediaMenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeDragable();
    }

    // stage dragable machen
    private void makeDragable() {
        parent.setOnMousePressed(((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        }));

        parent.setOnMouseDragged((event) -> {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }

    // fxml anzeigen
    public static void show(Stage stage, Statement statement, String username, String email) {
        try {
            username2 = username;
            FXMLLoader loader = new FXMLLoader(ApplicationC.class.getResource(VIEWNAME));           // view und controller erstellen
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);                                                          // scene erstellen
            stage = null;

            if (stage == null) {                                                                    // stage verwenden oder neue erzeugen
                stage = new Stage();
            }
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);                                                // titel bar entfernen
            stage.setTitle("CHATIFY! Register");
            ApplicationC applicationC = (ApplicationC) loader.getController();                      // controller erstellen
            applicationC.statement = statement;                                                     // datenbankzugriff merken
            System.out.println(username);
            applicationC.init(statement, username);                                                 //staticUsername.setText("Test"+username);
            stage.show();                                                                          // view anzeigen
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

    private void init(Statement statement, String username) {
        setCurrent(new User());
        setCurrentChatroom(new Chatroom(statement, null));
        setCurrentMessage(new Message(null, null, null));
        setCurrentUserList(current.getUsers(statement, null));
        userContainer.setItems(currentUserList);

        current.getUser(statement, username);
        usernameLabel.textProperty().bindBidirectional(current.nameProperty());
        usernameLabel1.textProperty().bindBidirectional(current.nameProperty());

        //currentChatrooms.setAll(currentChatroom.getChatrooms(statement, username));
        setCurrentChatrooms(currentChatroom.getChatrooms(statement, username));

        chatroomsContainer.setItems(currentChatrooms);

        chatroomsContainer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println(""+ chatroomsContainer.getSelectionModel().getSelectedItem().getChatroomId());
                changeChatroom(chatroomsContainer.getSelectionModel().getSelectedItem().getChatroomId());
            }
        });

        //changeChatroom(currentChatrooms.get(0).getChatroomId());

        userContainer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println(""+ chatroomsContainer.getSelectionModel().getSelectedItem().getChatroomId());
                if(userContainer.getSelectionModel().getSelectedItem() != null){
                    removeUser(userContainer.getSelectionModel().getSelectedItem().getUserId(), currentChatroom.getChatroomId());
                }
            }
        });
        
    }
    
    public void removeUser(String userId, String chatroomId){
        ChatParticipant cp = new ChatParticipant(chatroomId, userId, statement);
        cp.delete(statement);
        setCurrentUserList(current.getUsers(statement, chatroomId));
        userContainer.setItems(currentUserList);
    }

    public void changeChatroom(String chatroomId) {
        currentChatroom = new Chatroom(statement, chatroomId);
        setCurrentMessages(currentMessage.getMessages(statement, chatroomId));
        messageContainer.setItems(currentMessages);
        
        
        setCurrentUserList(current.getUsers(statement, chatroomId));
        userContainer.setItems(currentUserList);

        //setCurrentUserList(current.getUsers(statement, chatroomId)); //Zeigt alle User innerhalb dieses Servers
        //userContainer.setItems(currentUserList);
        System.out.println("Aktuelle Chatroom-ID:" + currentChatroom.getChatroomId());
    }
    
    

    public User getCurrent() {
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public ObservableList<User> getCurrentUserList() {
        return currentUserList;
    }

    public void setCurrentUserList(ObservableList<User> currentUserList) {
        this.currentUserList = currentUserList;
    }

    public Chatroom getCurrentChatroom() {
        return currentChatroom;
    }

    public void setCurrentChatroom(Chatroom currentChatroom) {
        this.currentChatroom = currentChatroom;
    }

    public ObservableList<Chatroom> getCurrentChatrooms() {
        return currentChatrooms;
    }

    public void setCurrentChatrooms(ObservableList<Chatroom> currentChatrooms) {
        this.currentChatrooms = currentChatrooms;
    }

    public Message getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(Message currentMessage) {
        this.currentMessage = currentMessage;
    }

    public ObservableList<Message> getCurrentMessages() {
        return currentMessages;
    }

    public void setCurrentMessages(ObservableList<Message> currentMessages) {
        this.currentMessages = currentMessages;
    }

    //Neuem Server Beitreten, wenn alle Eingaben (Name/Passwort) stimmen
    @FXML
    public void joinServer(ActionEvent event){
        //Response liefert NULL (Daten falsch eingegeben) oder die ChatroomId (Daten wahren richtig) zurück
        String response = currentChatroom.checkJoinInputs(tfInputServerName.getText(), tfInputServerPassword.getText(), statement);
        if(response != null){
            new ChatParticipant(response, current.getUserId(), statement);
            changeChatroom(response);
            setCurrentChatrooms(currentChatroom.getChatrooms(statement, current.getName()));
            chatroomsContainer.setItems(currentChatrooms);
            
        }else{
            //Ins FXML einbauen
            System.out.println("Kein Server mit diesen Daten gefunden");
        }
    }
    
    
    //Neue Nachricht senden
    @FXML
    public void sendMessage(ActionEvent event) {
        System.out.println(sendMsgInput.getText() + current.getUserId() + currentChatroom.getChatroomId());
        currentMessage = new Message(statement, sendMsgInput.getText(), current.getUserId(), currentChatroom.getChatroomId(), current.getName());
        currentMessages.add(currentMessage);
        messageContainer.setItems(currentMessages);
        sendMsgInput.setText("");
    }

    //Neuen Chatroom erstellen
    @FXML
    public void createNewChatroom(ActionEvent event) {
        try {
            /*
            *
            * Hier die Eingabeüberprüfung machen :)
            *
            */
            currentChatrooms.add(currentChatroom.createNewChatroom(tfCreateServerName.getText(), tfCreateServerPwd.getText(), statement));
            serverSettings.setVisible(false);
        } catch (Exception ex) {
            //Fehlermeldung das Name bereits vorhanden ist Anzeigen lassen.
            Logger.getLogger(ApplicationC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // user settings öffnen
    @FXML
    private void openUserSettings(ActionEvent event) {
        userSettings.setVisible(true);
        serverSettings.setVisible(false);
    }

    // user settings schließen
    @FXML
    private void closeUserSettings(ActionEvent event) {
        userSettings.setVisible(false);
        serverSettings.setVisible(false);
    }

    // server settings öffnen
    @FXML
    private void openServerSettings(ActionEvent event) {
        serverSettings.setVisible(true);
        userSettings.setVisible(false);
    }

    // server settings schließen
    @FXML
    private void closeServerSettings(ActionEvent event) {
        serverSettings.setVisible(false);
        userSettings.setVisible(false);
    }

    // fenster schließen
    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeWindowBtn.getScene().getWindow();
        stage.close();
    }

    // fenster verkleinern
    @FXML
    private void minimizeWindow(ActionEvent event) {
        Stage stage = (Stage) closeWindowBtn.getScene().getWindow();
        minimizeWindowBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                stage.setIconified(true);
            }
        });
    }

    @FXML
    public void changeUsername(ActionEvent event) {
        try {
            String username = current.getName();
            String newUsername = tfUsernameNew.getText();

            if (current.changeUsername(username, newUsername, statement)) {
                current.setName(newUsername);
                setCurrentUserList(current.getUsers(statement, null));
                userContainer.setItems(currentUserList);
                tfUsernameNew.setText("");
            }
        } catch (Exception ex) {
            Logger.getLogger(ApplicationC.class.getName()).log(Level.SEVERE, null, ex); //Hier die Fehlermeldung abfangen
        }

    }

    @FXML
    public void changePasswort(ActionEvent event) {
        String password = tfCurrentPasswort.getText();
        String newPassword = tfPasswortNew.getText();
        String newPasswordRe = tfPasswortNewRe.getText();
        String username = current.getName();

        try {
            if (!newPassword.equals(newPasswordRe)) {
                throw new Exception("Neues Passwort stimmt nicht überein!"); // Hier fehlermeldung abfangen.
                //+ REGEX überpfüfung einbauen.
            } else {
                if (current.changePassword(username, password, newPassword, statement)) {
                    System.out.println("Passwort geändert");
                    tfCurrentPasswort.setText("");
                    tfPasswortNew.setText("");
                    tfPasswortNewRe.setText("");
                    current.setPassword(newPassword);
                    setCurrentUserList(current.getUsers(statement, null));
                    userContainer.setItems(currentUserList);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ApplicationC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void changeEmail(ActionEvent event) {
        try {
            String username = current.getName();
            String newEmail = tfEmailNew.getText();

            if (current.changeEmail(username, newEmail, statement)) {
                current.setEmail(newEmail);
                setCurrentUserList(current.getUsers(statement, null));
                userContainer.setItems(currentUserList);
                tfEmailNew.setText("");
            }
        } catch (Exception ex) {
            Logger.getLogger(ApplicationC.class.getName()).log(Level.SEVERE, null, ex); //Hier die Fehlermeldung abfangen
        }
    }

    @FXML
    private void closeNews(ActionEvent event) {
        news.setVisible(false);
    }

    @FXML
    private void openNews(ActionEvent event) {
        news.setVisible(true);
    }

    @FXML
    private void openMediaMenu(ActionEvent event) {
        mediaMenu.setVisible(true);
    }

    @FXML
    private void closeMediaMenu(ActionEvent event) {
        mediaMenu.setVisible(false);
    }
}
