/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import business.NoteManager;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import jpa.Note;

/**
 *
 * @author madLife
 */
@RequestScoped
@ServerEndpoint("/noticeboard")
public class NoticeBoard {
    
    @EJB
    private NoteManager noteManager;
    
    @Inject 
    private NoteHandler noteHandler;

    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(">>> new session: " + session.getId());
        List<Note> notes = noteManager.findAll();
        noteHandler.addSession(session);
        if( !noteHandler.isIsSubmitted() ){
            noteHandler.sendToAllConnectedSessions();
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        try {
            notes.stream().map(n -> {
                return n.toJsonObject();
            }).forEach(j -> {
                arrayBuilder.add(j);
            });
            session.getBasicRemote().sendText(arrayBuilder.build().toString());
        } catch (IOException ex) {
            Logger.getLogger(NoticeBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);
    }

    @OnClose
    public void onClose(Session session) {
        noteHandler.removeSession(session);
    }

}
