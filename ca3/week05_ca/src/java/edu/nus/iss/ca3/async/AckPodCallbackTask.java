/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.async;

import edu.nus.iss.ca3.entity.Pod;
import edu.nus.iss.ca3.service.PodService;
import java.util.Optional;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Liu Zhenchang
 */
public class AckPodCallbackTask implements Runnable {
    
    private final AsyncResponse async;
    private final int podId;
    private final String ackId;
    private final PodService podService;
    
    public AckPodCallbackTask(AsyncResponse async, int podId, String ackId, PodService podService) {
        this.async = async;
        this.podId = podId;
        this.ackId = ackId;
        this.podService = podService;
    }
    
    @Override
    public void run() {
        Optional<Pod> opt = podService.findById(podId);
        if(opt.isPresent()) {
            opt.get().setAck_id(ackId);
            podService.updatePod(opt.get());
            async.resume(Response.ok().build());
        } else {
           async.resume(Response.status(Status.NOT_FOUND).build());
        }
    }
    
}
