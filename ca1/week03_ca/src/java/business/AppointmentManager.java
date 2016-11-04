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
import model.Appointment;

/**
 *
 * @author madLife
 */
@Stateless
public class AppointmentManager {
    
    @PersistenceContext private EntityManager em;
    
    public List<Appointment> findAllAppointmentByEmail( String email ){
        String queryString = "select a from People p join p.appointments a where p.email = :email";
        TypedQuery<Appointment> query = em.createQuery(queryString, Appointment.class);
        query.setParameter("email", email);
        return query.getResultList();
    }
    
    public void addAppointment( Appointment appointment ){
        if (appointment!=null) {
            em.persist(appointment);
        }
    }
}
