package chatify.application;

/* imports */
import chatify.models.Chatroom;
import chatify.models.Message;
import chatify.models.User;
import java.io.IOException;
import java.sql.Statement;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ApplicationC {
    
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

    /* sonstiges */
    private final static String VIEWNAME = "ApplicationV.fxml";
    private static Statement statement;
    private static Stage stage;
    private static String username2;
    
    private User current;
    private ObservableList<User> currentUserList;
    
    private Chatroom currentChatroom;
    private ObservableList<Chatroom> currentChatrooms;
    
    private Message currentMessage;
    private ObservableList<Message> currentMessages;
    
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

    private void init(Statement statement, String username){
        setCurrent(new User());
        setCurrentChatroom(new Chatroom(statement,null));
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
        
        changeChatroom(currentChatrooms.get(0).getChatroomId());
        
    }
    
    public void changeChatroom(String chatroomId){
        currentChatroom= new Chatroom(statement, chatroomId);
        setCurrentMessages(currentMessage.getMessages(statement, chatroomId));
        messageContainer.setItems(currentMessages);
        
        //setCurrentUserList(current.getUsers(statement, chatroomId)); //Zeigt alle User innerhalb dieses Servers
        //userContainer.setItems(currentUserList);
        
        System.out.println("Aktuelle Chatroom-ID:"+ currentChatroom.getChatroomId());
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
    
    
    @FXML
    public void sendMessage(ActionEvent event){
        System.out.println(sendMsgInput.getText()+ current.getUserId()+ currentChatroom.getChatroomId());
        currentMessage = new Message(statement, sendMsgInput.getText(), current.getUserId(), currentChatroom.getChatroomId());
        currentMessages.add(currentMessage);
        messageContainer.setItems(currentMessages);
        sendMsgInput.setText("");
    }

    @FXML
    private void openUserSettings(ActionEvent event) {
        userSettings.setVisible(true);
    }

    @FXML
    private void closeUserSettings(ActionEvent event) {
        userSettings.setVisible(false);
    }

    @FXML
    private void openServerSettings(ActionEvent event) {
        serverSettings.setVisible(true);
    }
    
    @FXML
    private void closeServerSettings(ActionEvent event) {
        serverSettings.setVisible(false);
    }
}
