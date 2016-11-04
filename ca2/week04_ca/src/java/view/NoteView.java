/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import business.NoteManager;
import business.UserManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import jpa.Note;
import jpa.User;
import websocket.NoteQueue;

/**
 *
 * @author madLife
 */
@RequestScoped
@Named
public class NoteView {
    
    private String title;
    private String content;
    private String category;
    
    private List<Note> notes = new ArrayList<>();
    
    @EJB
    NoteManager noteManager;
    
    @EJB
    UserManager userManager;
    
    @Inject
    LoginView loginView;
    
    @Inject
    NoteQueue noteQueue;
    
    @PostConstruct
    private void populateNotes(){
        User user = userManager.findById(loginView.getLoginUser()).get();
        notes = noteManager.findAllByUser(user);
    }

    public void post(){
        if( title!=null && title.length()>0 &&
            category!=null && category.length()>0 &&
            content!=null && content.length()>0){

            Note note = new Note();
            note.setCategory(category);
            note.setContent(content);
            note.setTitle(title);
            note.setDate(new Timestamp(System.currentTimeMillis()));
            User user = userManager.findById(loginView.getLoginUser()).get();
            note.setUser( user );
            
            noteManager.addNote(note);
            // put the new object to queue.
            try {
                noteQueue.getNoteQueue().put(note);
            } catch (InterruptedException ex) {
                Logger.getLogger(NoteView.class.getName()).log(Level.SEVERE, null, ex);
            }
            // update notes.
            notes = noteManager.findAllByUser(user);
        }
        else{
            FacesMessage msg = new FacesMessage("Wrong Information Format!");
            FacesContext.getCurrentInstance().addMessage("postNotes", msg);
        }
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
    
    
    
}
