package com.tony.server.response;

import java.util.HashMap;

public abstract class Response {
   private Status status;
   private HashMap<String, String> headers = new HashMap<>();
   private String body;

   public byte[] respond(){
      return ResponseBuilder.buildResponse(getStatus(),
              getHeaders(), getBody()).getBytes();
   }

   public Status getStatus(){
      return status;
   }
   public HashMap<String, String> getHeaders(){
      return headers;
   }
   public String getBody(){
      return body;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   public void setHeaders(HashMap<String, String> headers) {
      this.headers = headers;
   }

   public void setBody(String body) {
      this.body = body;
   }
}
