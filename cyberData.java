package com.cisco.jtapi.superProvider_deviceStateServer;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.net.URI;
import java.net.http.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.io.*;
import javax.net.ssl.*;




public class cyberData {

    public static void lightAction(String action, String URL){

        //using this to disable certificate validation since the CyberData lights have self-signed certs
        //should either give them signed certs or add them to trusted list in prod

       final Properties props = System.getProperties();
       props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

       SSLContext sslContext = null;
        
       final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
             }
        };
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
                 
                   
        //remove the .sslContext line for prod when we are using signed certs
        HttpClient client = HttpClient.newBuilder()
            .sslContext(sslContext)
            .build();

        switch (action){
            case "idle":
                action = "stop_strobe";
               // System.out.println("idle in cyberdata");
                break;
            case "active":
                action = "start_strobe&red=255&green=255&blue=255&pattern=slow_fade&brightness=128";
                break;
            case "alerting":
                action = "start_strobe&red=255&green=255&blue=255&pattern=fast_blink&brightness=128";
                break;
            case "held":
                action = "start_strobe&red=255&green=255&blue=255&pattern=slow_blink&brightness=128";
                break;
            
        }
        URI myUri = URI.create(URL);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(myUri)
            .POST(HttpRequest.BodyPublishers.ofString("request="+action))
            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("admin:admin").getBytes()))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build();


            CompletableFuture response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
   
        /* try {
            System.out.println(request.bodyPublisher());
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException e) {
             //TODO Auto-generated catch block
           e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
    }
}
