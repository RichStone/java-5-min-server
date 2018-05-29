package de.htw.app;

import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) {
    	staticFiles.location("/public");
    	
        get("/", (request, response) -> "Hello World");
        get("/hello-user/:name", (request, response) -> {
        	String name = request.params(":name");
        	return "hello-print " + name;
        });
        get("/picture", (request, response) -> {
        	return "<html><body><img src=imgs/sparkjava.png alt=\"Smiley face\"></body></html>";
        });
        // post("/user/new/:name", (...))
        // request.params()
    }
}