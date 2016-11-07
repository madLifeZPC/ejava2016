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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Liu Zhenchang
 */
@Stateless
@Path("/pod")
public class PodRest {

    @Resource(lookup = "concurrent/ejavaThreadPool")
    private ManagedExecutorService executors;
    @EJB
    private PodService podService;

    @PostConstruct
    public void initiate() {
        System.out.println("initiate end point");
    }

    @GET
    public void ackCallback(@QueryParam("podId") int podId, @QueryParam("ackId") String ackId, @Suspended AsyncResponse async) {
        AckPodCallbackTask task = new AckPodCallbackTask(async, podId, ackId, podService);
        executors.submit(task);
    }

    private byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
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
