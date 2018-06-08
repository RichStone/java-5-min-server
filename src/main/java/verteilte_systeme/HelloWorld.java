package verteilte_systeme;

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
        	return render(m, "number_input.vm");
        });
        
        post("/number", (request, response) -> {
        	Map<String, Object> m = new HashMap<String, Object>();
        	int number = Integer.parseInt(request.queryParams("number"));
        	m.put("number", number);
        	m.put("mul", number*number);

        	return render(m,"number_input.vm");
        });
        
        get("/params_calc", (request, response) -> {
        	Map<String, Object> m = new HashMap<String, Object>();
        	if (isInteger(request.queryParams("number_01")) && isInteger(request.queryParams("number_02"))) {
        		int first_number = Integer.parseInt(request.queryParams("number_01"));
        		int second_number = Integer.parseInt(request.queryParams("number_02"));
        		
        		int add = first_number + second_number;
            	int sub = first_number - second_number;
            	int mul = first_number * second_number;
            	int div = first_number / second_number;
            	
            	m.put("01", first_number);
            	m.put("02", second_number);
            	
            	return "(Addition): " + add + "\n"
        		+ "(Substraction): " + sub;
        	}
            else return "You entered a string instead of a number as an URL parameter. Please enter values for number_01 and number_02." + "<br>"
            		+ "example URL could be /params_calc?number_01=20&number_02=12";
        });
    }
    
    public static String render(Map<String, Object> model, String templatePath) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, templatePath));
    }
    
    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try
        {
           Integer.parseInt(s);   
           isValidInteger = true;
        }
        catch (NumberFormatException ex)
        {
           // s is not an integer
        }
        return isValidInteger;
     }
}