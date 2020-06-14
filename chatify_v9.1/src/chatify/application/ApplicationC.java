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
import java.util.Timer;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    //User Filtern
    @FXML
    private TextField tfInputUserSuche;
    @FXML
    private Button btUserSuche;

    //Refresh Timer
    Timer refreshTimer = new Timer();
    Timer refreshTimer2 = new Timer();

    // stage dragable machen
    private double x = 0;
    private double y = 0;
    @FXML
    private VBox mediaMenu;
    @FXML
    private Button btJoinPrvServer;
    @FXML
    private Button btJoinPublicServer;
    @FXML
    private VBox publicServer;
    @FXML
    private VBox prvServer;
    @FXML
    private TextField tfInputServerNamePublic;
    @FXML
    private Label joinServerErrorPublic;
    @FXML
    private Label joinServerErrorPrivate;
    
    // SERVER SETTINGS ERRORS
    @FXML
    private Label createServerErrorName;
    @FXML
    private Label createServerErrorPassword;
    @FXML
    private Label createServerErrorPassword2;
    @FXML
    private Label createServerErrorUser;
    
    // USER SETTING ERRORS
    @FXML
    private Label userSettingErrorName;
    @FXML
    private Label userSettingErrorPassword;
    @FXML
    private Label userSettingErrorPasswordNew;
    @FXML
    private Label userSettingErrorPasswordNew2;
    @FXML
    private Label userSettingErrorEmail;
    @FXML
    private Label aktuelleServer;
    @FXML
    private Label userCreatedAt;
    @FXML
    private Label userServerAnzahl;

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
        setCurrentUserList(current.getUsers(statement, "-1"));
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
                if (chatroomsContainer.getSelectionModel().getSelectedItem() != null) {
                    changeChatroom(chatroomsContainer.getSelectionModel().getSelectedItem().getChatroomId());
                }
            }
        });

        //changeChatroom(currentChatrooms.get(0).getChatroomId());
        userContainer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println(""+ chatroomsContainer.getSelectionModel().getSelectedItem().getChatroomId());
                if (userContainer.getSelectionModel().getSelectedItem() != null) {
                    removeUser(userContainer.getSelectionModel().getSelectedItem().getUserId(), currentChatroom.getChatroomId());
                    setCurrentUserList(current.getUsers(statement, "-1"));
                }
            }
        });

        refreshTimer.schedule(new chatifyTimerTask(this, false), 2000, 1000);
        refreshTimer2.schedule(new chatifyTimerTask(this, true), 1000, 10000);
        
        // Show userinfo: createdAt
        userCreatedAt.setText(current.sendInfoCreatedAt(statement, current.getName()));
    }

    public void removeUser(String userId, String chatroomId) {
        ChatParticipant cp = new ChatParticipant(chatroomId, userId, statement);
        cp.delete(statement);
        currentUserList.clear();
        //setCurrentUserList(current.getUsers(statement, chatroomId));
        //userContainer.setItems(currentUserList);
    }

    public void changeChatroom(String chatroomId) {
        currentChatroom = new Chatroom(statement, chatroomId);
        //setCurrentMessages(currentMessage.getMessages(statement, chatroomId));
        //messageContainer.setItems(currentMessages);

        //setCurrentUserList(current.getUsers(statement, chatroomId));
        //userContainer.setItems(currentUserList);
        //setCurrentUserList(current.getUsers(statement, chatroomId)); //Zeigt alle User innerhalb dieses Servers
        //userContainer.setItems(currentUserList);
        System.out.println("Aktuelle Chatroom-ID:" + currentChatroom.getChatroomId());
    }

    //Aktualisiert Chatlisten, Nachrichtenverläufe, Teilnehmerlisten
    public synchronized void timerRefresh(boolean all) {
        
            String filter = tfInputUserSuche.getText() + "%";
            
            //Chatroom Liste
            if (all){
                // SERVER ANZAHL AKTUALISIEREN
                aktuelleServer.setText(currentChatroom.countChatrooms(statement));
                userServerAnzahl.setText(currentChatroom.countChatroomsUser(statement, current.getUserId()));
                
                setCurrentChatrooms(currentChatroom.getChatrooms(statement, current.getName()));
                chatroomsContainer.setItems(currentChatrooms);
                boolean found = false;
                if (currentChatrooms != null && currentChatroom != null) {
                    for (int j = 0; j < currentChatrooms.size(); j++) {
                        if (currentChatrooms.get(j).getChatroomId().equalsIgnoreCase(currentChatroom.getChatroomId())){
                            chatroomsContainer.getSelectionModel().select(j);
                            chatroomsContainer.getFocusModel().focus(j);
                            found = true;
                            j = currentChatrooms.size();
                        }                   
                    }
                    System.out.println("Chatroom " + currentChatroom.getChatroomId()+ " gesetzt:" + found);
                    if (!found){
                           currentChatroom = null;
                    }
                }
            }       
            if (currentChatroom != null && currentChatroom.getChatroomId()!= null){
                //neue Nachrichten anzeigen
                setCurrentMessages(currentMessage.getMessages(statement, currentChatroom.getChatroomId()));
            
                //User Liste aktualisieren.
                //userContainer.getItems().clear();
                setCurrentUserList(current.getUsers(statement, currentChatroom.getChatroomId(), filter));

                
            } else {
                //neue Nachrichten anzeigen
                setCurrentMessages(currentMessage.getMessages(statement, "-1"));
            
                //User Liste aktualisieren.
                setCurrentUserList(current.getUsers(statement, "-1"));
            } 
            messageContainer.setItems(currentMessages);
            System.out.println("currentUserList:"+currentUserList.size());
            userContainer.setItems(currentUserList);

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
        /*if (this.currentUserList != null) {
            this.currentUserList.clear();
        }*/
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

    // PRIVATEN SERVER BEITRETEN
    @FXML
    public void joinServer(ActionEvent event) {
        try {
            //Response liefert NULL (Daten falsch eingegeben) oder die ChatroomId (Daten wahren richtig) zurück
            if (currentChatroom == null){
               currentChatroom = new Chatroom(statement,"-1"); 
            }
            String response = currentChatroom.checkJoinInputs(tfInputServerName.getText(), tfInputServerPassword.getText(), current.getUserId(), statement);

            new ChatParticipant(response, current.getUserId(), statement);
            changeChatroom(response);
            
            setCurrentChatrooms(currentChatroom.getChatrooms(statement, current.getName()));
            chatroomsContainer.setItems(currentChatrooms);
            timerRefresh(true);
            
            tfInputServerName.setText("");
            tfInputServerPassword.setText("");
            
            prvServer.setVisible(false);

        } catch (Exception ex) {
            //System.out.println(ex.toString());
            joinServerErrorPrivate.setText(ex.toString().substring(21, (ex.toString().length())));
        }
    }
    
    // ÖFFENTLICHEN SERVER BEITRETEN
    @FXML
    public void joinPublicServer(ActionEvent event) {
        try {
            //Response liefert NULL (Daten falsch eingegeben) oder die ChatroomId (Daten wahren richtig) zurück
            if (currentChatroom == null){
               currentChatroom = new Chatroom(statement,"-1"); 
            }
            String response = currentChatroom.checkJoinInputs(tfInputServerNamePublic.getText(), "empty", current.getUserId(), statement);

            new ChatParticipant(response, current.getUserId(), statement);
            changeChatroom(response);
            
            setCurrentChatrooms(currentChatroom.getChatrooms(statement, current.getName()));
            chatroomsContainer.setItems(currentChatrooms);
            timerRefresh(true);
            
            tfInputServerNamePublic.setText("");
            
            publicServer.setVisible(false);

        } catch (Exception ex) {
            joinServerErrorPublic.setText(ex.toString().substring(21, (ex.toString().length())));
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
           
            // CHATROOM NAME
            if(tfCreateServerName.getText() == null || tfCreateServerName.getText() == "") {
                createServerErrorName.setText("Servername darf nicht leer stehen!");
                throw new Exception("servername fehler: darf nicht leer stehen!");
            } else {
                createServerErrorName.setText("");
            }
            
            if(tfCreateServerName.getText().length() < 3) {
                createServerErrorName.setText("Servername darf nicht kürzer als 3 sein!");
                throw new Exception("servername fehler: nicht kürzer als 3!");
            } else {
                createServerErrorName.setText("");
            }
            
            if(tfCreateServerName.getText().length() > 19) {
                createServerErrorName.setText("Servername darf nicht länger als 20 sein!");
                throw new Exception("servername fehler: nicht länger als 22!");
            } else {
                createServerErrorName.setText("");
            }
            
            String regexServer = "[a-zA-Z0-9]*";
            Pattern patternServer = Pattern.compile(regexServer);
            Matcher matcherServer = patternServer.matcher(tfCreateServerName.getText());
            if (!matcherServer.matches()) {
                createServerErrorName.setText("Servername darf nur alphanumerische Charaktere beinhalten!");
                throw new Exception("servername fehler: beinhaltet spezielle symbole!");
            } else {
                createServerErrorName.setText("");
            }
            //-----------------------------------------------------------------
            
            // CHATROOM PASSWORT
            if(tfCreateServerPwd.getText() != null && tfCreateServerPwd.getText() != "" && tfCreateServerPwd.getText().length() > 0) {
                if(tfCreateServerPwd.getText().length() < 3) {
                    createServerErrorPassword.setText("Passwort darf nicht kürzer als 3 sein!");
                    throw new Exception("passwort fehler: nicht kürzer als 3!");
                } else {
                    createServerErrorPassword.setText("");
                }
                
                if(tfCreateServerPwd.getText().length() > 50) {
                    createServerErrorPassword.setText("Passwort darf nicht länger als 50 sein!");
                    throw new Exception("passwort fehler: nicht länger als 50!");
                } else {
                    createServerErrorPassword.setText("");
                }
                
                String regexPassword = "[a-zA-Z0-9]*";
                Pattern patternPassword = Pattern.compile(regexPassword);
                Matcher matcherPassword = patternPassword.matcher(tfCreateServerPwd.getText());
                if (!matcherPassword.matches()) {
                    createServerErrorPassword.setText("Passwort darf nur alphanumerische Charaktere beinhalten!");
                    throw new Exception("passwort fehler: beinhaltet spezielle symbole!");
                } else {
                    createServerErrorPassword.setText("");
                }
                
                if(!tfCreateServerPwd.getText().equals(tfCreateServerPwdRe.getText())) {
                    createServerErrorPassword2.setText("Passwort muss übereinstimmen!");
                    throw new Exception("passwort fehler: stimmt nicht mit der zweiten eingabe überein!");
                } else {
                    createServerErrorPassword2.setText("");
                }
            }
            //-----------------------------------------------------------------
            
            // CHATROOM BENUTZERANZAHL
            if(tfCreateServerUserCount.getText() != null && tfCreateServerUserCount.getText() != "" && tfCreateServerUserCount.getText().length() > 0) {
                if(Integer.parseInt(tfCreateServerUserCount.getText()) < 2) {
                    createServerErrorUser.setText("Benutzeranzahl darf nicht kleiner als 2 sein!");
                    throw new Exception("benutzeranzahl fehler: nicht kleiner als 2");
                } else {
                    createServerErrorUser.setText("");
                }
                
                if(Integer.parseInt(tfCreateServerUserCount.getText()) > 10000) {
                    createServerErrorUser.setText("Benutzeranzahl darf nicht größer als 10000 sein!");
                    throw new Exception("benutzeranzahl fehler: nicht größer als 10000");
                } else {
                    createServerErrorUser.setText("");
                }
            }
            //-----------------------------------------------------------------
            
            if (currentChatroom == null){
               currentChatroom = new Chatroom(statement,"-1"); 
            }
            
            Chatroom tempChatroom = currentChatroom.createNewChatroom(tfCreateServerName.getText(), tfCreateServerPwd.getText(), tfCreateServerUserCount.getText(), statement);
            new ChatParticipant(tempChatroom.getChatroomId(), current.getUserId(), statement);
            currentChatrooms.add(tempChatroom);
            changeChatroom(tempChatroom.getChatroomId());
            timerRefresh(true);

            tfCreateServerName.setText("");
            tfCreateServerPwd.setText("");
            tfCreateServerPwdRe.setText("");
            tfCreateServerUserCount.setText("");

            serverSettings.setVisible(false);
        } catch (Exception ex) {
            //Fehlermeldung das Name bereits vorhanden ist Anzeigen lassen.
            //Logger.getLogger(ApplicationC.class.getName()).log(Level.SEVERE, null, ex);
            createServerErrorName.setText(ex.toString().substring(21, (ex.toString().length())));
        }
    }

    //User in Chatroom suchen
    @FXML
    private void clearFilter(ActionEvent event) {
        tfInputUserSuche.setText("");
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
        refreshTimer.cancel();
        refreshTimer2.cancel();
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
        String username = current.getName();
        String newUsername = tfUsernameNew.getText();
            
        try {
            // USER NEW USERNAME
            if (tfUsernameNew.getText() != null && tfUsernameNew.getText() != "" && tfUsernameNew.getText().length() > 0) {
                if(tfUsernameNew.getText() == "") {
                    userSettingErrorName.setText("Username darf nicht leer sein!");
                    throw new Exception("username fehler: darf nicht leer sein!");
                } else {
                    userSettingErrorName.setText("");
                }
                
                if(tfUsernameNew.getText().length() < 3) {
                    userSettingErrorName.setText("Username darf nicht kürzer als 3 sein!");
                    throw new Exception("username fehler: nicht kürzer als 3!");
                } else {
                    userSettingErrorName.setText("");
                }

                if(tfUsernameNew.getText().length() > 22) {
                    userSettingErrorName.setText("Username darf nicht länger als 22 sein!");
                    throw new Exception("username fehler: nicht länger als 22!");
                } else {
                    userSettingErrorName.setText("");
                }

                String regexUsernameNew = "[a-zA-Z0-9]*";
                Pattern patternUsernameNew = Pattern.compile(regexUsernameNew);
                Matcher matchNewUsername = patternUsernameNew.matcher(tfUsernameNew.getText());
                if (!matchNewUsername.matches()) {
                    userSettingErrorName.setText("Username darf nur alphanumerische Charaktere beinhalten!");
                    throw new Exception("username fehler: beinhaltet spezielle symbole!");
                } else {
                    userSettingErrorName.setText("");
                }
            }
            //-----------------------------------------------------------------
            
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
            // USER NEW PASWORD
            if (tfCurrentPasswort.getText() != null && tfCurrentPasswort.getText() != "" && tfCurrentPasswort.getText().length() > 0) {
                if (!tfCurrentPasswort.getText().equals(current.checkPasswordTrue(statement, current.getName()))) {
                    userSettingErrorPassword.setText("Passwort stimmt nicht überein!");
                    throw new Exception("userpasswort fehler: stimmt nicht überein!");
                } else {
                    userSettingErrorPassword.setText("");
                    
                    
                    if (tfPasswortNew.getText() == "") {
                         userSettingErrorPasswordNew.setText("Neues Passwort darf nicht leer stehen!");
                        throw new Exception("passwort fehler: darf nicht leer sein!");
                    } else {
                        userSettingErrorPasswordNew.setText("");
                    }

                    if(tfPasswortNew.getText().length() < 8) {
                        userSettingErrorPasswordNew.setText("Passwort darf nicht kürzer als 8 sein!");
                        throw new Exception("passwort fehler: nicht kürzer als 8!");
                    } else {
                        userSettingErrorPasswordNew.setText("");
                    }

                    if(tfPasswortNew.getText().length() > 50) {
                        userSettingErrorPasswordNew.setText("Passwort darf nicht länger als 50 sein!");
                        throw new Exception("passwort fehler: nicht länger als 50!");
                    } else {
                        userSettingErrorPasswordNew.setText("");
                    }

                    String regexNewPassord = "[a-zA-Z0-9]*";
                    Pattern patternNewPassword = Pattern.compile(regexNewPassord);
                    Matcher matcherNewPassword = patternNewPassword.matcher(tfPasswortNew.getText());
                    if (!matcherNewPassword.matches()) {
                        userSettingErrorPasswordNew.setText("Passwort darf nur alphanumerische Charaktere beinhalten!");
                        throw new Exception("passwort fehler: beinhaltet spezielle symbole!");
                    } else {
                        userSettingErrorPasswordNew.setText("");
                    }

                    if(!tfPasswortNew.getText().equals(tfPasswortNewRe.getText())) {
                        userSettingErrorPasswordNew2.setText("Passwort muss übereinstimmen!");
                        throw new Exception("passwort fehler: stimmt nicht mit der zweiten eingabe überein!");
                    } else {
                        userSettingErrorPasswordNew2.setText("");
                    }
                    

                }
            }
            
            
            //-----------------------------------------------------------------
            
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
        String username = current.getName();
        String newEmail = tfEmailNew.getText();
            
        try {
            // USER NEW EMAIL
            if (tfEmailNew.getText() != null && tfEmailNew.getText() != "" && tfEmailNew.getText().length() > 0) {
                String regexNewEmail = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";
                Pattern patternNewEmail = Pattern.compile(regexNewEmail);
                Matcher matcherNewEmail = patternNewEmail.matcher(tfEmailNew.getText());
                if (!matcherNewEmail.matches()) {
                    userSettingErrorEmail.setText("E-Mail entspricht nicht der syntax 'mustermann@mail.com'!");
                    throw new Exception("email fehler: entspricht nicht der syntax 'mustermann@mail.com'!");
                } else {
                    userSettingErrorEmail.setText("");
                }
            }
            //-----------------------------------------------------------------

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

    


    

    
    // PRIVATE SERVER
    @FXML
    private void openPrivServer(ActionEvent event) {
        prvServer.setVisible(true);
    }
    @FXML
    private void closePrivServer(ActionEvent event) {
        prvServer.setVisible(false);
    }

    // PUBLIC SERVER
    @FXML
    private void openPublicServer(ActionEvent event) {
        publicServer.setVisible(true);
    }
    @FXML
    private void closePublicServer(ActionEvent event) {
        publicServer.setVisible(false);
    }

 
}
