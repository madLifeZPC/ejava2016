/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.async;

import edu.nus.iss.ca3.entity.Delivery;
import edu.nus.iss.ca3.service.DeliveryService;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author Liu Zhenchang
 */
public class GetDeliveryTask implements Runnable {

    private final AsyncResponse async;
    private final DeliveryService delService;

    public GetDeliveryTask(AsyncResponse async, DeliveryService delService) {
        this.async = async;
        this.delService = delService;
    }

    @Override
    public void run() {
        List<Delivery> list = delService.findAll();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        if (list != null) {
            for (Delivery del : list) {
                JsonObjectBuilder job = Json.createObjectBuilder()
                        .add("teamId", "2e6fae47")
                        .add("podId", del.getPod().getId())
                        .add("name", del.getName())
                        .add("address", del.getAddress())
                        .add("phone", del.getPhone());
                jab.add(job);
            }
            async.resume(Response.ok(jab.build().toString()).build());
        } else {
            async.resume(Response.ok().build());
        }
    }

}
