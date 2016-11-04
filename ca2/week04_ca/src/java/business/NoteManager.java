/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import jpa.Note;
import jpa.User;

/**
 *
 * @author madLife
 */
@Stateless
public class NoteManager {
    
    @PersistenceContext private EntityManager em;
    
    public void addNote( Note note ){
         if( note!=null )
         {
            em.persist(note);
         }       
     }

    public List<Note> findAllByUser(User user) {
        String queryString = "select a from User u join u.notes a where u.userid = :userid";
        TypedQuery<Note> query = em.createQuery(queryString, Note.class);
        query.setParameter("userid", user.getUserid());
        return query.getResultList();
    }

    public List<Note> findAll() {
        String queryString = "select n from Note n";
        TypedQuery<Note> query = em.createQuery(queryString, Note.class);
        return query.getResultList();
    }
    
}
