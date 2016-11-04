/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import jpa.Note;

/**
 *
 * @author madLife
 */
@ApplicationScoped
public class NoteHandler {

    //@Resource(lookup = "concurrent/myPool")
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Inject
    private NoteQueue noteQueue;

    private final Set<Session> sessions = new HashSet<>();

    private boolean isSubmitted = false;

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public void sendToAllConnectedSessions() {
        executor.submit(() -> {

            try {
                while (true) {
                    System.out.println(noteQueue.getNoteQueue().size());
                    Note note = noteQueue.getNoteQueue().take();
                    for (Session s : sessions) {
                        try {
                            s.getBasicRemote().sendText(note.toJsonObject().toString());
                            
                        } catch (IOException ex) {
                            Logger.getLogger(NoteHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    System.out.println("sent");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(NoteHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        isSubmitted = true;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public boolean isIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(boolean isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

}
