/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.view;

import edu.nus.iss.ca3.entity.Delivery;
import edu.nus.iss.ca3.entity.Pod;
import edu.nus.iss.ca3.service.DeliveryService;
import edu.nus.iss.ca3.service.PodService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author dingyi
 */
public class ViewPackageView {
    @EJB
    private DeliveryService deliveryService;
    
    private String name;
    private String address;
    private String phone;
    private String note;
    private String ackId;

    private List<Delivery> deliveries =  new ArrayList<>();
    private List<Pod> pods = new ArrayList<>();
    
    public void allDelivery() {
        deliveries = deliveryService.findAll();
        for(Delivery del : deliveries) {
            name = del.getName();
            address = del.getAddress();
            phone = del.getPhone();
            note = del.getPod().getNote();
            ackId = del.getPod().getAck_id();
        }
        //pods = podService.findById(deliveries.get(0).getId());
    }
    
    public void post() {
        
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }

    public String getAckId() {
        return ackId;
    }
    
     public void setAckId(String ackId) {
        this.ackId = ackId;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }
    
     public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}
