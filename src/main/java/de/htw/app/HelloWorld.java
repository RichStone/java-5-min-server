package de.htw.app;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class HelloWorld {
    public static void main(String[] args) {
    	staticFiles.location("/public");
    	
        get("/", (request, response) -> "Hello World ÜÄÖ");
        get("/hello-user/:name", (request, response) -> {
        	String name = request.params(":name");
        	return "hello-print " + name;
        });
        post("/add/:user-name", (request, response) -> {
        	String name = request.params(":name");
        	return "hello-print " + name;
        });
        get("/picture", (request, response) -> {        	
//        	response.type("text/html,charset=utf-8");
        	return "<html><body><h1>Hello World ÜÄÖ</h1><img src=imgs/sparkjava.png alt=\"Smiley face\"></body></html>";
        });
        get("/number", (request, response) -> {
        	Map<String, Object> m = new HashMap<String, Object>();
        	return render(m, "fibonacci.vm");
        });
        post("/number", (request, response) -> {
        	Map<String, Object> m = new HashMap<String, Object>();
        	String number = request.attribute("number");
        	System.out.println(request.params());
        	System.out.println(request.attributes());
        	System.out.println(request.queryParams("number"));
        	System.out.println(request.splat());
        	return render(m, "fibonacci.vm");
        });
    }
    
    public static String render(Map<String, Object> model, String templatePath) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, templatePath));
    }
    
}