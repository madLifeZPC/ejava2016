/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.async;

import edu.nus.iss.ca3.entity.Pod;
import edu.nus.iss.ca3.service.PodService;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author Liu Zhenchang
 */
public class UpdatePodTask implements Runnable {

    private final AsyncResponse async;
    private final Pod pod;
    private final PodService podService;

    public UpdatePodTask(AsyncResponse async, Pod pod, PodService podService) {
        this.async = async;
        this.pod = pod;
        this.podService = podService;
    }

    @Override
    public void run() {
        podService.updatePod(pod);
        async.resume(Response.ok().build());
    }
}
