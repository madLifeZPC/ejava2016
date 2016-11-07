/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.service;

import edu.nus.iss.ca3.entity.Pod;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author madLife
 */
@Stateless
public class PodService {
    
    @PersistenceContext private EntityManager em;
    
    public void addPod( Pod pod ){
         if( pod!=null )
         {
            em.persist(pod);
         }       
    }
    
    public void updatePod( Pod pod ) throws IllegalArgumentException{
       if(em.find(Pod.class, pod.getId()) == null){
           throw new IllegalArgumentException("Unknown Employee id");
       }
       em.merge(pod);  
    }
    
    public List<Pod> findAll() {
        String queryString = "select p from Pod p";
        TypedQuery<Pod> query = em.createQuery(queryString, Pod.class);
        return query.getResultList();
    }
    
}
