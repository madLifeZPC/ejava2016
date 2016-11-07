/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.rest;

import edu.nus.iss.ca3.async.GetDeliveryTask;
import edu.nus.iss.ca3.entity.Delivery;
import edu.nus.iss.ca3.service.DeliveryService;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Liu Zhenchang
 */
@Stateless
@Path("/delivery")
public class DeliveryRest {
    
    @Resource(lookup = "concurrent/ejavaThreadPool")
    ManagedExecutorService executors;
    @EJB private DeliveryService delService;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public void getDelivery(@Suspended AsyncResponse async) {
        GetDeliveryTask task = new GetDeliveryTask(async, delService);
        executors.submit(task);
    }
}
