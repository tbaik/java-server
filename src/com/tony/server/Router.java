package com.tony.server;

import com.tony.server.response.Response;

import java.util.HashMap;

public class Router {
    private HashMap<Request, Response> routes;

    public Router() {
        routes = new HashMap<>();
    }

    public Response route(Request request) {
        if(hasRoute(request)){
            return routes.get(request);
        } else{
            return null;
        }
    }

    public boolean hasRoute(Request request) {
        return routes.containsKey(request);
    }

    public void addRoute(Request request, Response response) {
        routes.put(request, response);
    }

    public String allowedMethodsForURI(String uri){
        String allowedMethods = "";
        for (Request request : routes.keySet()) {
            if(request.getURI().equals(uri)){
                allowedMethods += request.getHttpMethod() + ",";
            }
        }
        allowedMethods += "OPTIONS";
        return allowedMethods;
    }
}
