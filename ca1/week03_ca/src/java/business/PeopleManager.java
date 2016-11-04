/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.People;

/**
 *
 * @author madLife
 */
@Stateless
public class PeopleManager {
    
     @PersistenceContext private EntityManager em;
     
     public void addPeople( People people ){
         if( people!=null )
         {
            String id = UUID.randomUUID().toString().substring(0,8);
            people.setPid(id);
            em.persist(people);
         }       
     }
     
    public Optional<People> findByEmail ( final String email ){
        String queryString = "select p from People p where p.email = :email";
        TypedQuery<People> query = em.createQuery(queryString, People.class);
        query.setParameter("email", email);
        List<People> result = query.getResultList();
        if( result.isEmpty() )
            return Optional.ofNullable(null);
        return Optional.ofNullable(result.get(0));
    }
     
}
