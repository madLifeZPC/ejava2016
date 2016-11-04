/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import jpa.User;

/**
 *
 * @author madLife
 */
@Stateless
public class UserManager {
    
    @PersistenceContext private EntityManager em;
    
    @EJB
    GroupManager groupManager;
    
    public void addUser( User user ){
         if( user!=null )
         {
            em.persist(user);
            groupManager.addToGroup(user, "USERS");
         }       
    }

    public Optional<User> findById(String userid) {
        String queryString = "select u from User u where u.userid = :userid";
        TypedQuery<User> query = em.createQuery(queryString, User.class);
        query.setParameter("userid", userid);
        List<User> result = query.getResultList();
        if( result.isEmpty() )
            return Optional.ofNullable(null);
        return Optional.ofNullable(result.get(0));
    }
    
}
