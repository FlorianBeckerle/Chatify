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
    public void registerVerify(String inputUsernameR, String inputPasswordR, String inputPasswordRR, String inputEmailR) throws Exception {
        System.out.println("#########FEHLER#########");
        // USERNAME
        // länge nicht weniger als 3
        if(inputUsernameR.length() < 3) {
            throw new Exception("username fehler: nicht kürzer als 3!");
        }
        // länge nicht mehr als 22
        if(inputUsernameR.length() > 22) {
            throw new Exception("username fehler: nicht länger als 22!");
        }
        // keine sonderzeichen
        String regexUsername = "[a-zA-Z0-9]*";
        Pattern patternUsername = Pattern.compile(regexUsername);
        Matcher matcherUsername = patternUsername.matcher(inputUsernameR);
        if(!matcherUsername.matches()) {
            throw new Exception("username fehler: beinhaltet spezielle symbole!");
        }
        
        // PASSWORT
        // länge nicht weniger als 8
        if(inputPasswordR.length() < 8) {
            throw new Exception("passwort fehler: nicht kürzer als 8!");
        }
        // länge nicht mehr als 50
        if(inputPasswordR.length() > 50) {
            throw new Exception("passwort fehler: nicht länger als 50!");
        }
        // keine sonderzeichen
        String regexPassword = "[a-zA-Z0-9]*";
        Pattern patternPassword = Pattern.compile(regexPassword);
        Matcher matcherPassword = patternPassword.matcher(inputPasswordR);
        if(!matcherPassword.matches()) {
            throw new Exception("passwort fehler: beinhaltet spezielle symbole!");
        }
        // passwort muss gleich sein wie zweite eingabe
        if(!inputPasswordR.equals(inputPasswordRR)) {
            throw new Exception("passwort fehler: stimmt nicht mit der zweiten eingabe überein!");
        }
        
        // EMAIL
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
        Pattern patternEmail = Pattern.compile(regexEmail); 
        Matcher matcherEmail = patternEmail.matcher(inputEmailR);
        if(!matcherEmail.matches()) {
            throw new Exception("email fehler: entspricht nicht der syntax 'mustermann@mail.com'!");
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
