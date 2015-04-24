package com.tony.server.response;

import java.util.HashMap;

public abstract class Response {
   private String statusLine;
   private HashMap<String, String> headers;
   private String body;

   public String respond(){
      return ResponseBuilder.buildResponse(getStatusLine(),
              getHeaders(), getBody());
   }

   public String getStatusLine(){
      return statusLine;
   }
   public HashMap<String, String> getHeaders(){
      return headers;
   }
   public String getBody(){
      return body;
   }

   public void setStatusLine(String statusLine) {
      this.statusLine = statusLine;
   }

   public void setHeaders(HashMap<String, String> headers) {
      this.headers = headers;
   }

   public void setBody(String body) {
      this.body = body;
   }
}
