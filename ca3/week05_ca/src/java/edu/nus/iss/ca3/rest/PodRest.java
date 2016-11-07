/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.rest;

import edu.nus.iss.ca3.async.AckPodCallbackTask;
import edu.nus.iss.ca3.async.UpdatePodTask;
import edu.nus.iss.ca3.entity.Pod;
import edu.nus.iss.ca3.service.PodService;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
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

    @Resource(lookup = "concurrent/ejavaThreadPool")
    private ManagedExecutorService executors;
    @EJB
    private PodService podService;

    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public void update(MultivaluedMap form, @Suspended AsyncResponse async) {
        Pod pod = new Pod();
        pod.setId(Integer.parseInt(form.get("podId").toString()));
        pod.setNote(form.get("note").toString());
        byte[] image = null;
        try {
            image = toByteArray(form.get("image"));
            pod.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(PodRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Timestamp time = new Timestamp(Long.parseLong(form.get("time").toString()));
        pod.setDelivery_date(time);
        UpdatePodTask task = new UpdatePodTask(async, pod, podService);
        executors.submit(task);
        requestForAck(pod.getAck_id(), pod.getNote(),image);
    }

    @GET
    public void ackCallback(@QueryParam("podId") int podId, @QueryParam("ackId") String ackId, @Suspended AsyncResponse async) {
        AckPodCallbackTask task = new AckPodCallbackTask(async, podId, ackId, podService);
        executors.submit(task);
    }

    private byte[] toByteArray(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return bytes;
    }

    private void requestForAck(String podId, String note, byte[] image) {
        try {
            URL url = new URL("http://10.10.0.50:8080/epod/upload");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write("teamId=2e6fae47" + "&podId=" + podId
                    + "&callback=" + "http://localhost:8080/week05_ca/pod"
                    + "&note=" + note + "&image=" + Arrays.toString(image));
            writer.flush();
//            String line;
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//            writer.close();
//            reader.close();
        } catch (Exception ex) {
            //adding to log
        }
    }
}
