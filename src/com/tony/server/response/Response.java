package com.tony.server.response;

import java.util.HashMap;

public abstract class Response {
   public String respond(){
      return ResponseBuilder.buildResponse(getStatusLine(),
              getHeaders(), getBody());
   }
   public abstract String getStatusLine();
   public abstract HashMap<String, String> getHeaders();
   public abstract String getBody();

}
