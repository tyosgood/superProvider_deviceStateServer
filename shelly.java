package com.cisco.jtapi.superProvider_deviceStateServer;


import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.net.URI;
import java.net.http.*;


public class shelly {

    public static void lightAction(String action, String URL){
        
        HttpClient client = HttpClient.newHttpClient();

        URI myUri = URI.create(URL+action);

        HttpRequest request = HttpRequest.newBuilder(myUri).build();

        //try {
            CompletableFuture response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.body());
        //} catch (IOException e) {
            // TODO Auto-generated catch block
       //     e.printStackTrace();
       // } catch (InterruptedException e) {
            // TODO Auto-generated catch block
      //      e.printStackTrace();
      //  }
    }
    
}
