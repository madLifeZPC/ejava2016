/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.service;

import edu.nus.iss.ca3.entity.Delivery;
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
public class DeliveryService {
    
    @PersistenceContext( unitName = "week05_PU") private EntityManager em;
    
    public void addDelivery( Delivery delivery ){
        if( delivery!=null )
        {
            int deliveryId = addDeliveryWithoutPod(delivery);
            delivery.setId(deliveryId);
            Pod pod = new Pod();
            pod.setDelivery(delivery);
            em.persist(pod);
        }       
    }
    
    public int addDeliveryWithoutPod( Delivery delivery ){
        if( delivery!=null )
        {
            em.persist(delivery);
            em.flush();
            System.out.println(">>> delivery id: " + delivery.getId());
            return delivery.getId();
        }
        return 0;
    }
    
    public List<Delivery> findAllByName(String name) {
        String queryString = "select d from Delivery d where d.name = :name";
        TypedQuery<Delivery> query = em.createQuery(queryString, Delivery.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
    
    public List<Delivery> findAll() {
        String queryString = "select d from Delivery d";
        TypedQuery<Delivery> query = em.createQuery(queryString, Delivery.class);
        return query.getResultList();
    }
    
}
