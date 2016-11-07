/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.rest;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author Liu Zhenchang
 */
@Stateless
@Path("pod")
public class PodRest {
    
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void add(MultivaluedMap form, @Suspended AsyncResponse async) {
        
    }
    
    @GET
    public void ackCallback(@Suspended AsyncResponse async) {
    
    }
    
}
