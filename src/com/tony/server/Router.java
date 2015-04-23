package com.tony.server;

import com.tony.server.response.Response;

import java.util.HashMap;

public class Router {
    private HashMap<Request, Response> routes;

    public Router() {
        routes = new HashMap<>();
    }

    public boolean hasRoute(Request request) {
        return routes.containsKey(request);
    }

    public void addRoute(Request request, Response response) {
        routes.put(request, response);
    }

    public Response route(Request request) {
        if(hasRoute(request)){
            return routes.get(request);
        } else{
            return null;
        }
    }
}
