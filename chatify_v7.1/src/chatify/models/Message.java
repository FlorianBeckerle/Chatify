package chatify.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Message {

    private final StringProperty messageid = new SimpleStringProperty();
    private final StringProperty messageContent = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> createdAt = new SimpleObjectProperty<>();

    private final StringProperty userid = new SimpleStringProperty();
    private final StringProperty chatroomId = new SimpleStringProperty();

    public Message(String messageContent, String userid, String chatroomId) {
        setMessageContent(messageContent);
        setUserId(userid);
        setChatroomId(chatroomId);
    }

    public Message(Statement statement, String messageContent, String userid, String chatroomid) {
        createNewMessage(statement, messageContent, userid, chatroomid);
        setMessageContent(messageContent);
        setUserId(userid);
        setChatroomId(chatroomid);
    }

    public void createNewMessage(Statement statement, String messageContent, String userid, String chatroomid) {
        try {
            //String sql = "insert into MESSAGE (MESSAGEID, MESSAGECONTENT, CREATEDAT, USER_USERID, CHATROOM_CHATROOMID) values(next value for seq_message, 'Willkommen in Chatify! Das hier ist eine Testnachricht.', CURRENT_DATE, 2, 1)";
            String sql = "insert into MESSAGE (MESSAGEID, MESSAGECONTENT, CREATEDAT, USER_USERID, CHATROOM_CHATROOMID) values(next value for seq_message, '"+ messageContent+"', CURRENT_DATE, "+ userid+", "+chatroomid+")";
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<Message> getMessages(Statement statement, String chatroomId) {
        try {
            String sql = "select * from message where chatroom_chatroomid ="+ chatroomId; //Einschränkung fehlt nocht
            ResultSet rs = statement.executeQuery(sql);
            ObservableList<Message> messages = FXCollections.observableArrayList();
            while (rs.next()) {
                messages.add(new Message(rs.getString("messagecontent"), rs.getString("user_userid"), rs.getString("chatroom_chatroomid")));
            }

            return messages;
        } catch (SQLException ex) {
            Logger.getLogger(Chatroom.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /*
    public ObservableList<Message> showMessages(Statement statement, String chatroomid){
        String sql = "select * from message";
        
    }*/
    
    @Override
    public String toString(){
        return messageContent.get();
    }

    public void setMessageId(String value) {
        messageid.set(value);
    }

    public StringProperty messageIdProperty() {
        return messageid;
    }

    public String getMessageId() {
        return messageid.get();
    }

    private void setMessageContent(String value) {
        messageContent.set(value);
    }

    public StringProperty messageContentProperty() {
        return messageContent;
    }

    public String getMessageContent() {
        return messageContent.get();
    }

    public void setChatroomId(String value) {
        chatroomId.set(value);
    }

    public StringProperty chatroomIdProperty() {
        return chatroomId;
    }

    public String getChatroomId() {
        return chatroomId.get();
    }
    
    public void setUserId(String value) {
        userid.set(value);
    }

    public StringProperty userIdProperty() {
        return userid;
    }

    public String getUserId() {
        return userid.get();
    }

    public ObjectProperty<?> createdAtProperty() {
        return createdAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt.get();
    }

}
