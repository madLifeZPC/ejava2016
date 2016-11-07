/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.ca3.log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import static java.lang.Thread.sleep;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author madLife
 */
public class PodLog {
    
    private String path = "";

    public PodLog( String path ) {
        this.path = path;
    }
    
    public String readUnhandledPod(){
        String result = "";
        File file=new File(path); 
        try {  
            if(!file.exists())  
                file.createNewFile();  
                           
            RandomAccessFile in = new RandomAccessFile(file, "rw");  
            FileChannel fcout=in.getChannel();  
            FileLock flout=null;  
            while(true){    
                try {  
                    flout = fcout.tryLock();  
                    break;  
                } catch (Exception e) {  
                    try {    
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PodLog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }    
            }  
          
            in.seek(0);
            result = in.readLine();
              
            flout.release();  
            fcout.close();  
            in.close();  
            in=null;  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
        return result==null?"":result;
    }
    
    public void writeUnhandledPod( String record ){
        
        File file=new File(path); 
        try {  
            if(!file.exists())  
                file.createNewFile();  
                           
            RandomAccessFile out = new RandomAccessFile(file, "rw");  
            FileChannel fcout=out.getChannel();  
            FileLock flout=null;  
            while(true){    
                try {  
                    flout = fcout.tryLock();  
                    break;  
                } catch (Exception e) {  
                    try {    
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PodLog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }    
            }  
          
            out.write(record.getBytes());
              
            flout.release();  
            fcout.close();  
            out.close();  
            out=null;  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
        
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
}
