
package chatify.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatParticipant {
    String chatroomId;
    String userId;
    String createdAt;
    
    public ChatParticipant(String chatroomId, String userId, Statement statement){
        setChatroomId(chatroomId);
        setUserId(userId);
        
        
        //User zu Chat hinzufügen
        createNewChatParticipant(statement);
    }
    
    private void createNewChatParticipant(Statement statement){
        try {
            String sql="select * from chatparticipants where user_userid="+getUserId()+" and chatroom_chatroomid="+getChatroomId();
            ResultSet rs = statement.executeQuery(sql);
            
            //System.out.println(getUserId() + " | " + getChatroomId());
            
            if(!rs.next()){
                sql = "insert into chatparticipants (createdAt,user_userid, chatroom_chatroomid) values(CURRENT_DATE, "+getUserId()+", "+getChatroomId()+")";
                statement.execute(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChatParticipant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(Statement statement){
        try {
            String sql="select * from chatparticipants where user_userid="+getUserId()+" and chatroom_chatroomid="+getChatroomId();
            ResultSet rs = statement.executeQuery(sql);
            
            
            if(rs.next()){
                sql = "delete from chatparticipants where user_userid="+getUserId()+" and chatroom_chatroomid="+getChatroomId();
                statement.execute(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChatParticipant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
}
