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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author dingyi
 */
@RequestScoped
@Named
public class ViewPackageView {
    @EJB
    private DeliveryService deliveryService;
     @EJB
    private PodService podService;

    private List<Delivery> deliveries =  new ArrayList<>();
    private List<Pod> pods = new ArrayList<>();
    
    
    @PostConstruct
    public void populate(){
        deliveries = deliveryService.findAll();
        pods = podService.findAll();
    }

    

    public List<Delivery> getDeliveries() {
        return deliveries;
    }
    
     public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public List<Pod> getPods() {
        return pods;
    }

    public void setPods(List<Pod> pods) {
        this.pods = pods;
    }
     
     
}
