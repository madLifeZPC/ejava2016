/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.service;

import edu.nus.iss.ca3.entity.Pod;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
