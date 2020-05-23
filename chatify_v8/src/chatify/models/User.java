package chatify.models;

import chatify.loginregister.LoginRegisterC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class User {

    private final StringProperty userid = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public User() {
        userid.setValue(null);
        name.setValue(null);
        email.setValue(null);
        password.setValue(null);
    }

    public User(String userid, String name, String email, String password) {
        setUserId(userid);
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    //daten einfügen
    public void createNewUser(Statement statement) { //String username, String email, String password,
        try {
            String sql = "insert into benutzer (userid, "
                    + "username, "
                    + "email, "
                    + "password, "
                    + "createdat) "
                    + "values(next value for seq_user, "
                    + "'" + name.get()
                    + "', '" + email.get()
                    + "', '" + password.get()
                    + "', CURRENT_DATE)";
            statement.executeUpdate(sql);
            System.out.println(name);
        } catch (SQLException ex) {
            Logger.getLogger(LoginRegisterC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loginVerify(Statement statement, String inputUsernameL, String inputPasswordL, Label errorUsernameL, Label errorPasswordL) throws Exception {
        // USERNAME
        if (inputUsernameL == null || inputUsernameL == "") {
            errorUsernameL.setText("Username darf nicht leer stehen!");
            throw new Exception("username fehler: darf nicht leer stehen!");
        } else {
            errorUsernameL.setText("");
        }

        if (inputPasswordL == null || inputPasswordL == "") {
            errorPasswordL.setText("Passwort darf nicht leer stehen!");
            throw new Exception("Passwort fehler: darf nicht leer stehen!");
        } else {
            errorPasswordL.setText("");
        }

        if (!verifyUser(statement, inputUsernameL, inputPasswordL)) {
            errorUsernameL.setText("Username oder Passwort stimmt nicht!");
            throw new Exception("Username oder Passwort stimmt nicht!");
        } else {
            errorUsernameL.setText("");
        }

    }

    public void getUser(Statement statement, String username) {
        try {
            String sql = "select * from benutzer where username = '" + username + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                userid.setValue(rs.getString("userid"));
                name.setValue(rs.getString("username"));
                email.setValue(rs.getString("email"));
                password.setValue(rs.getString("password"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<User> getUsers(Statement statement, String chatroomid) {
        try {
            String sql = "select * from benutzer b"; //Einschränkung fehlt nocht
            if (chatroomid != null) {
                sql += ", chatparticipants c where b.userid = c.user_userid and c.chatroom_chatroomid =" + chatroomid;
            }
            sql += " order by b.username asc";
            ResultSet rs = statement.executeQuery(sql);
            ObservableList<User> users = FXCollections.observableArrayList();
            while (rs.next()) {
                System.out.println(rs.getString("userid") + " | " + rs.getString("username") + " | " + rs.getString("email") + " | " + rs.getString("password"));
                users.add(new User(rs.getString("userid"), rs.getString("username"), rs.getString("email"), rs.getString("password")));
            }

            return users;

        } catch (SQLException ex) {
            Logger.getLogger(Chatroom.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Boolean verifyUser(Statement statement, String inputUsernameL, String inputPasswordL) {
        try {
            String sql = "select password from benutzer where username = '" + inputUsernameL + "' and password = '" + inputPasswordL + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                return true;
            }

            return false;

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    // daten prüfen
    public void registerVerify(String inputUsernameR,
            String inputPasswordR,
            String inputPasswordRepeatR,
            String inputEmailR,
            Label errorUsernameR,
            Label errorPasswordR,
            Label errorPasswordRepeatR,
            Label errorEmail
    ) throws Exception {
        System.out.println("#########FEHLER#########");
        // USERNAME
        if (inputUsernameR == null) {
            errorUsernameR.setText("Username darf nicht leer stehen!");
            throw new Exception("username fehler: darf nicht leer stehen!");
        } else {
            errorUsernameR.setText("");
        }
        if (inputUsernameR.length() < 3) {
            errorUsernameR.setText("Username darf nicht kürzer als 3 sein!");
            throw new Exception("username fehler: nicht kürzer als 3!");
        } else {
            errorUsernameR.setText("");
        }
        if (inputUsernameR.length() > 22) {
            errorUsernameR.setText("Username darf nicht länger als 22 sein!");
            throw new Exception("username fehler: nicht länger als 22!");
        } else {
            errorUsernameR.setText("");
        }
        String regexUsername = "[a-zA-Z0-9]*";
        Pattern patternUsername = Pattern.compile(regexUsername);
        Matcher matcherUsername = patternUsername.matcher(inputUsernameR);
        if (!matcherUsername.matches()) {
            errorUsernameR.setText("Username darf nur alphanumerische Charaktere beinhalten!");
            throw new Exception("username fehler: beinhaltet spezielle symbole!");
        } else {
            errorUsernameR.setText("");
        }

        // PASSWORT
        if (inputPasswordR == null) {
            errorPasswordR.setText("Passwort darf nicht leer stehen!");
            throw new Exception("passwort fehler: darf nicht leer stehen!");
        } else {
            errorPasswordR.setText("");
        }
        if (inputPasswordR.length() < 8) {
            errorPasswordR.setText("Passwort darf nicht kürzer als 8 sein!");
            throw new Exception("passwort fehler: nicht kürzer als 8!");
        } else {
            errorPasswordR.setText("");
        }
        if (inputPasswordR.length() > 50) {
            errorPasswordR.setText("Passwort darf nicht länger als 50 sein!");
            throw new Exception("passwort fehler: nicht länger als 50!");
        } else {
            errorPasswordR.setText("");
        }
        String regexPassword = "[a-zA-Z0-9]*";
        Pattern patternPassword = Pattern.compile(regexPassword);
        Matcher matcherPassword = patternPassword.matcher(inputPasswordR);
        if (!matcherPassword.matches()) {
            errorPasswordR.setText("Passwort darf nur alphanumerische Charaktere beinhalten!");
            throw new Exception("passwort fehler: beinhaltet spezielle symbole!");
        } else {
            errorPasswordR.setText("");
        }

        // PASSWORT REPEAT
        if (!inputPasswordR.equals(inputPasswordRepeatR)) {
            errorPasswordRepeatR.setText("Passwort muss übereinstimmen!");
            throw new Exception("passwort fehler: stimmt nicht mit der zweiten eingabe überein!");
        } else {
            errorPasswordRepeatR.setText("");
        }

        // EMAIL
        if (inputEmailR == null) {
            errorEmail.setText("E-Mail darf nicht leer stehen!");
            throw new Exception("email fehler: darf nicht leer stehen!");
        } else {
            errorEmail.setText("");
        }
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";
        Pattern patternEmail = Pattern.compile(regexEmail);
        Matcher matcherEmail = patternEmail.matcher(inputEmailR);
        if (!matcherEmail.matches()) {
            errorEmail.setText("E-Mail entspricht nicht der syntax 'mustermann@mail.com'!");
            throw new Exception("email fehler: entspricht nicht der syntax 'mustermann@mail.com'!");
        } else {
            errorEmail.setText("");
        }
    }

    public boolean changeUsername(String username, String newUsername, Statement statement) throws Exception {
        try {

            String sql = "Select * from benutzer where username='" + newUsername + "'";
            ResultSet rs = statement.executeQuery(sql);

            if (!rs.next()) {
                sql = "UPDATE benutzer set username='" + newUsername + "' where username='" + username + "'";
                statement.executeUpdate(sql);
                return true;
            } else {
                throw new Exception("Username existiert bereits");
            }

        } catch (SQLException ex) {
            throw new Exception(ex);
        }

    }

    public boolean changePassword(String username, String password, String newPassword, Statement statement) throws Exception {
        try {

            String sql = "UPDATE benutzer set password='" + newPassword + "' where username='" + username + "' and password ='"+ password+"'";
            statement.executeUpdate(sql);
            return true;

        } catch (SQLException ex) {
            throw new Exception("Das aktuelle Passwort stimmt nicht überein.");
        }
    }
    
    public boolean changeEmail(String username, String newEmail, Statement statement) throws Exception {
        try {

            String sql = "Select * from benutzer where username='" + username + "' and email='"+ newEmail+"'";
            ResultSet rs = statement.executeQuery(sql);

            if (!rs.next()) {
                sql = "UPDATE benutzer set email='" + newEmail + "' where username='" + username + "'";
                statement.executeUpdate(sql);
                return true;
            } else {
                throw new Exception("Email ist gleichgeblieben.");
            }

        } catch (SQLException ex) {
            throw new Exception(ex);
        }

    }

    @Override
    public String toString() {
        return name.get();
    }

    private void setUserId(String value) {
        userid.set(value);
    }

    public StringProperty userIdProperty() {
        return userid;
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setPassword(String value) {
        password.set(value);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public  void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getUserId() {
        return userid.get();
    }

    public String getName() {
        return name.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getEmail() {
        return email.get();
    }

}
