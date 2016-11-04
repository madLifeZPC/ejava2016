/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import business.UserManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import jpa.User;

/**
 *
 * @author madLife
 */
@RequestScoped
@Named
public class RegisterView {
    
    @EJB
    UserManager userManager;
    @Inject
    LoginView loginView;
    
    private String username;
    private String password;
    
    
     public String register() {
        User user = new User();
        if (username != null && username.length() > 0
                && password != null && password.length() > 0) {
            String digestedPassword = digest(password, "SHA-256");
            if( !"".equals(password) ){
                user.setUserid(username);
                user.setPassword(digestedPassword);
                userManager.addUser(user);
                loginView.setUsername(username);
                loginView.setPassword(password);
                return loginView.login();
            }
            FacesMessage msg = new FacesMessage("Wrong Password Format!");
            FacesContext.getCurrentInstance().addMessage("registerForm:password", msg);
            return null;
        }
        FacesMessage msg = new FacesMessage("Wrong Username or Password Format!");
	FacesContext.getCurrentInstance().addMessage("registerForm:password", msg);
        return null;
    }
     
    private String digest(String string, String algorithm) {
        String resultString = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(password.getBytes());
            byte[] byteBuffer = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < byteBuffer.length; i++) {
                String hexString = Integer.toHexString(0xff & byteBuffer[i]);
                if (hexString.length() == 1) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(hexString);
            }
            resultString = stringBuilder.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultString;
    }
    
    public String registerView() {
        return ("register");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
