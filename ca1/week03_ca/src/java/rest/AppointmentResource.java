/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import business.AppointmentManager;
import business.PeopleManager;
import java.sql.Timestamp;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import model.Appointment;
import model.People;

/**
 *
 * @author madLife
 */
@RequestScoped
@Path("/appointment")
public class AppointmentResource {

    @Resource(lookup = "concurrent/myPool")
    private ManagedScheduledExecutorService service;

    @EJB
    AppointmentManager appointmentManager;
    @EJB
    PeopleManager peopleManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{email}")
    public void getAllAppointmentsByEmail(@PathParam("email") String email,
            @Suspended AsyncResponse asyncResponse) {
        service.submit(() -> {
            List<Appointment> appointments = appointmentManager.findAllAppointmentByEmail(email);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            appointments.stream().map(a -> {
                return a.toJson();
            }).forEach(j -> {
                arrayBuilder.add(j);
            });
            asyncResponse.resume(Response.ok(arrayBuilder.build()).build());
        });
    }

    @POST
    @Consumes("application/x-www-form-urlencoded") //
    public void post(MultivaluedMap<String, String> formData,
            @Suspended AsyncResponse asyncResponse) {
        String date = formData.getFirst("date");
        String email = formData.getFirst("email");
        String description = formData.getFirst("description");
        if (date != null && date != "" && email != null && email != ""
                && description != null) {
            service.submit(() -> {
                Appointment appointment = new Appointment();
                Optional<People> people = peopleManager.findByEmail(email);
                if (!people.isPresent()) {
                    asyncResponse.resume(Response.status(Response.Status.NOT_FOUND).build());
                } else {
                    appointment.setPeople(people.get());
                    appointment.setDescription(description);
                    appointment.setDate( new Timestamp(Long.valueOf(date)));
                    appointmentManager.addAppointment(appointment);
                    asyncResponse.resume(Response.ok().build());
                }
            });
        } else {
            service.submit(() -> {
                asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST).build());
            });
        }
    }
}
