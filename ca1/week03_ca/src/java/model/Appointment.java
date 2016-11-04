/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import model.People;

/**
 *
 * @author madLife
 */
@Entity
@Table(name="appointment")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="appt_id")
    private int appointmentId;
    
    @Column(name="description")
    private String description;
    
    @Column(name="appt_date")
    private Timestamp date;
    
    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid" )
    private People people;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
    
    public JsonObject toJson(){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("appointmentId", appointmentId);
        builder.add("dateTime", date.getTime());
        builder.add("description", description);
        builder.add("personId", people.getPid());
        return builder.build();
    }
    
}
