/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.rest;

import edu.nus.iss.ca3.entity.Pod;
import edu.nus.iss.ca3.service.PodService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Optional;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

/**
 *
 * @author Liu Zhenchang
 */
@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet{
        
    @EJB 
    private PodService podService;
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{
               
        int podId = Integer.parseInt(req.getParameter("podId"));
        String note = req.getParameter("note");
       // System.out.println(">>>>>note:"+note);
        Part imagePart = req.getPart("image");
        byte[] image = new byte[(int)imagePart.getSize()];
        InputStream is = imagePart.getInputStream();
        is.read(image);
        
        Long time = Long.parseLong(req.getParameter("time"));
        Optional<Pod> pod = podService.findById(podId);
        if( pod.isPresent() ){
            Pod instance = pod.get();
            instance.setNote(note);
             System.out.println(">>>>>note:"+instance.getNote());
            instance.setImage(image);
            
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time);
            instance.setDelivery_date(new java.sql.Timestamp(cal.getTimeInMillis())); 
            writeFile(image,req.getParameter("podId"));
            podService.updatePod(instance);
        }

        
        Client client = ClientBuilder.newBuilder()
                .register(MultiPartFeature.class)
                .build();

        MultiPart part = new MultiPart();

        FileDataBodyPart imgPart = new FileDataBodyPart("image",
                new File(req.getParameter("podId")),
                MediaType.APPLICATION_OCTET_STREAM_TYPE);
        imgPart.setContentDisposition(
                FormDataContentDisposition.name("image")
                .fileName("ca3.jpg").build());

        MultiPart formData = new FormDataMultiPart()
                .field("teamId", "2e6fae47", MediaType.TEXT_PLAIN_TYPE)
                .field("podId", podId, MediaType.TEXT_PLAIN_TYPE)
                .field("note", note, MediaType.TEXT_PLAIN_TYPE)
                .field("callback", "http://172.23.133.161:8080/week05_ca/api/pod", MediaType.TEXT_PLAIN_TYPE)
                .field("time", Long.toString(System.currentTimeMillis()), MediaType.TEXT_PLAIN_TYPE)
                .bodyPart(imgPart);
        formData.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

        //part.bodyPart(new FileDataBodyPart("image", 
        //new File("/home/cmlee/Pictures/ca3.png")));
        WebTarget target = client.target("http://10.10.0.50:8080/epod/upload");
        Invocation.Builder inv = target.request();

        System.out.println(">>> part: " + formData);

        Response callResp = inv.post(Entity.entity(formData, formData.getMediaType()));

    }
    
    public void writeFile(byte[] img, String fileName) throws IOException{
        try(FileOutputStream fs = new FileOutputStream(fileName)){
            fs.write(img);
        }
    }
    
}
