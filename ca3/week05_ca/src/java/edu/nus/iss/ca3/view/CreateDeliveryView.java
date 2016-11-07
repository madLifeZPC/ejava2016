/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.view;

import edu.nus.iss.ca3.entity.Delivery;
import edu.nus.iss.ca3.service.DeliveryService;
import java.sql.Timestamp;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

/**
 *
 * @author dingyi
 */
@Named
@RequestScoped
public class CreateDeliveryView {

    @EJB
    private DeliveryService deliveryService;
    
    private String name;
    private String address;
    private String phone;
    private Timestamp date;
    
    public String createDeliveryView() {
        return ("createDelivery");
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
    
    public Timestamp getDate() {
        return date;
    }
    
    public void setDate() {
        this.date = new Timestamp(System.currentTimeMillis());
    }
    
    public String createDelivery() {
        Delivery delivery = new Delivery();
        if(name != null && name.length() > 0
                && address != null && address.length() > 0
                && phone != null && phone.length() > 0) {
            delivery.setName(name);
            delivery.setAddress(address);
            delivery.setPhone(phone);
            delivery.setDate(date);
            deliveryService.addDelivery(delivery);
            return null;
        }
        FacesMessage msg = new FacesMessage("Wrong Format!");
	//FacesContext.getCurrentInstance().addMessage("registerForm:password", msg);
        return null;
    }
}
