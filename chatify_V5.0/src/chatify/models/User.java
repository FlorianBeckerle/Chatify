package chatify.models;

import chatify.loginregister.LoginRegisterC;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class User {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public User(){
        name.setValue(null);
        email.setValue(null);
        password.setValue(null);
    }
    
    //daten einfügen
    public void createNewUser(String username, String email, String password, Statement statement){
        try {
            String sql = "insert into benutzer (userid, "
                            + "username, "
                            + "email, "
                            + "password, "
                            + "createdat) "
                            + "values(next value for seq_user, "
                            + "'"+ username
                            +"', '"+ email
                            +"', '"+ password
                            +"', CURRENT_DATE)";
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(LoginRegisterC.class.getName()).log(Level.SEVERE, null, ex);
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
        if(inputUsernameR == null) {
            errorUsernameR.setText("Username darf nicht leer stehen!");
            throw new Exception("username fehler: darf nicht leer stehen!");
        } else {
            errorUsernameR.setText("");
        }
        if(inputUsernameR.length() < 3) {
            errorUsernameR.setText("Username darf nicht kürzer als 3 sein!");
            throw new Exception("username fehler: nicht kürzer als 3!");
        } else {
            errorUsernameR.setText("");
        }
        if(inputUsernameR.length() > 22) {
            errorUsernameR.setText("Username darf nicht länger als 22 sein!");
            throw new Exception("username fehler: nicht länger als 22!");
        } else {
            errorUsernameR.setText("");
        }
        String regexUsername = "[a-zA-Z0-9]*";
        Pattern patternUsername = Pattern.compile(regexUsername);
        Matcher matcherUsername = patternUsername.matcher(inputUsernameR);
        if(!matcherUsername.matches()) {
            errorUsernameR.setText("Username darf nur alphanumerische Charaktere beinhalten!");
            throw new Exception("username fehler: beinhaltet spezielle symbole!");
        } else {
            errorUsernameR.setText("");
        }
        
        // PASSWORT
        if(inputPasswordR == null) {
            errorPasswordR.setText("Passwort darf nicht leer stehen!");
            throw new Exception("passwort fehler: darf nicht leer stehen!");
        } else {
            errorPasswordR.setText("");
        }
        if(inputPasswordR.length() < 8) {
            errorPasswordR.setText("Passwort darf nicht kürzer als 8 sein!");
            throw new Exception("passwort fehler: nicht kürzer als 8!");
        } else {
            errorPasswordR.setText("");
        }
        if(inputPasswordR.length() > 50) {
            errorPasswordR.setText("Passwort darf nicht länger als 50 sein!");
            throw new Exception("passwort fehler: nicht länger als 50!");
        } else {
            errorPasswordR.setText("");
        }
        String regexPassword = "[a-zA-Z0-9]*";
        Pattern patternPassword = Pattern.compile(regexPassword);
        Matcher matcherPassword = patternPassword.matcher(inputPasswordR);
        if(!matcherPassword.matches()) {
            errorPasswordR.setText("Passwort darf nur alphanumerische Charaktere beinhalten!");
            throw new Exception("passwort fehler: beinhaltet spezielle symbole!");
        } else {
            errorPasswordR.setText("");
        }
        
        // PASSWORT REPEAT
        if(!inputPasswordR.equals(inputPasswordRepeatR)) {
            errorPasswordRepeatR.setText("Passwort muss übereinstimmen!");
            throw new Exception("passwort fehler: stimmt nicht mit der zweiten eingabe überein!");
        } else {
            errorPasswordRepeatR.setText("");
        }
        
        // EMAIL
        if(inputEmailR == null) {
            errorEmail.setText("E-Mail darf nicht leer stehen!");
            throw new Exception("email fehler: darf nicht leer stehen!");
        } else {
            errorEmail.setText("");
        }
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
        Pattern patternEmail = Pattern.compile(regexEmail); 
        Matcher matcherEmail = patternEmail.matcher(inputEmailR);
        if(!matcherEmail.matches()) {
            errorEmail.setText("E-Mail entspricht nicht der syntax 'mustermann@mail.com'!");
            throw new Exception("email fehler: entspricht nicht der syntax 'mustermann@mail.com'!");
        } else {
            errorEmail.setText("");
        }
    }

    private void setName(String value) {
      name.set(value);
    }

    public StringProperty nameProperty() {
      return name;
    }
    
    private void setPassword(String value) {
      password.set(value);
    }

    public StringProperty passwordProperty() {
      return password;
    }
    
    private void setEmail(String value) {
      email.set(value);
    }

    public StringProperty emailProperty() {
      return email;
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
