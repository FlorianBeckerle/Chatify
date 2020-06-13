package chatify.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chatroom {

    private final StringProperty chatroomId = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> createdAt = new SimpleObjectProperty<>();
    

    public Chatroom(Statement statement, String chatroomid) {
        try {
            if (chatroomid == null) {
                setChatroomId(null);
                setName(null);
            } else {
                String sql = "select * from chatroom where chatroomId = " + chatroomid;
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    setChatroomId(rs.getString("chatroomid"));
                    setName(rs.getString("name"));
                    //setCreatedAt(rs.getDate("createdat"));

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Chatroom.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Chatroom(String chatroomid, String name) {
        setName(name);
        setChatroomId(chatroomid);
    }
    
    public ObservableList<Chatroom> getChatrooms(Statement statement, String username) {
        try {
            String sql = "select cr.* from chatroom cr, chatparticipants crp, benutzer b where cr.CHATROOMID = crp.CHATROOM_CHATROOMID and crp.USER_USERID = b.USERID and b.USERNAME = '"+username+"' order by cr.name"; //Einschränkung fehlt nocht
            ResultSet rs = statement.executeQuery(sql);
            ObservableList<Chatroom> chatrooms = FXCollections.observableArrayList();
            while (rs.next()) {
                chatrooms.add(new Chatroom(rs.getString("chatroomid"), rs.getString("name")));
            }

            return chatrooms;
        } catch (SQLException ex) {
            Logger.getLogger(Chatroom.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Chatroom createNewChatroom(String name, String password, Statement statement) throws Exception{
        try {
            if(password == null || "".equals(password)){
                password = "empty";
            }
            
            //Checken ob der ChatroomName schon vergeben ist
            String sqlQName = "select name from chatroom where name='"+name+"'";
            ResultSet rsName = statement.executeQuery(sqlQName);
            
            if(rsName.next()){
                throw new Exception("Servername bereits vergeben.");
            }
            
            
            String sql = "insert into CHATROOM(CHATROOMID, CREATEDAT, NAME, password) values(next value for seq_chatroom, CURRENT_DATE, "
                    + "'"+ name +"', "
                    + "'"+ password +"')";
            statement.executeUpdate(sql);
            System.out.println("Chatroom erstellt.");
            
            String sqlQ = "select CHATROOMID from chatroom where name='"+name+"' and password='"+password+"'"; //Einschränkung fehlt nocht
            ResultSet rs = statement.executeQuery(sqlQ);
            
            rs.next();
            return new Chatroom(rs.getString("CHATROOMID"), name);
        } catch (SQLException ex) {
            Logger.getLogger(Chatroom.class.getName()).log(Level.SEVERE, null, ex);
            return new Chatroom(statement ,null);
        }
    }
    
    public String checkJoinInputs(String chatroomName, String chatroomPassword, Statement statement){
        try {
            if("".equals(chatroomPassword) || chatroomPassword == null){
                chatroomPassword = "empty";
            }
            String sql = "select chatroomid from chatroom where name='"+chatroomName+"' and password='"+chatroomPassword+"'";
            ResultSet rs = statement.executeQuery(sql);
            
            if(rs.next()){
                return rs.getString("chatroomid");
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Chatroom.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String toString() {
        return name.getValue();
    }

    private void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    private void setChatroomId(String value) {
        chatroomId.set(value);
    }

    public StringProperty chatroomIdProperty() {
        return chatroomId;
    }

    public String getChatroomId() {
        return chatroomId.get();
    }

    private void setCreatedAt(LocalDate value) {
        createdAt.set(value);
    }

    public ObjectProperty<?> createdAtProperty() {
        return createdAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt.get();
    }
    
    
}
