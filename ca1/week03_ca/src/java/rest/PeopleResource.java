/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import business.AppointmentManager;
import business.PeopleManager;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import model.Appointment;
import model.People;

/**
 *
 * @author madLife
 */
@RequestScoped
@Path("/people")
public class PeopleResource {

    @Resource(lookup = "concurrent/myPool")
    private ManagedScheduledExecutorService service;

    @EJB
    PeopleManager peopleManager;

    @POST
    @Consumes("application/x-www-form-urlencoded") //
    public void post(MultivaluedMap<String, String> formData,
            @Suspended AsyncResponse asyncResponse) {
        People people = new People();
        String name = formData.getFirst("name");
        String email = formData.getFirst("email");
        if (name != null && name != "" && email != null && email != "") {
            people.setName(name);
            people.setEmail(email);
            service.submit(() -> {
                peopleManager.addPeople(people);
                asyncResponse.resume(Response.ok().build());
            });
        }
        else{
            service.submit(() -> {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
            });
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void get(@QueryParam("email") String email,
            @Suspended AsyncResponse asyncResponse) {
        service.submit(() -> {
            Optional<People> people = peopleManager.findByEmail(email);
            if (!people.isPresent()) {
                asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
            } else {
                asyncResponse.resume(Response.ok().build());
            }
        });
    }

}
