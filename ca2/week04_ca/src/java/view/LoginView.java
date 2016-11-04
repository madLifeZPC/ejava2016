/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author madLife
 */
@RequestScoped
@Named
public class LoginView {

    private String username;
    private String password;

    public String loginView() {
        return ("login");
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

    public String login() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            req.login(username, password);
            return ("user/menu?faces-redirect=true");
        } catch (ServletException ex) {
            fc.addMessage("loginForm:password", new FacesMessage("Incorrect login!"));
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String logout(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            req.logout();
        } catch (ServletException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            return ("/index?faces-redirect=true");
        }
    }
    
    public boolean isLogin(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        return (req.getRemoteUser() != null);
    }

    public String getLoginUser(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        return req.getRemoteUser();
    }
}
