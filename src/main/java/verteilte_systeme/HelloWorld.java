package verteilte_systeme;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import spark.ModelAndView;
import spark.Service;
import spark.template.velocity.VelocityTemplateEngine;

public class HelloWorld {
    public static void main(String[] args) {
    	igniteFirstSparkV1();
    	igniteSecondSparkV2();
    }
    
	private static void igniteFirstSparkV1() {
		Service http = Service.ignite();
		
    	staticFiles.location("/public");

    	// get("/", (request, response) -> "Hello World ÜÄÖß%&   FROM PORT 4567 ");
    	get("/", (request, response) -> {        	
        	return "<html><body><h1>Hello World ÜÄÖß$% from Server V2 from port 4567</h1><img src=imgs/sparkjava.png alt=\"Smiley face\"></body></html>";
        });
        get("/hello-user/:name", (request, response) -> {
        	String name = request.params(":name");
        	return "hello-print " + name;
        });
        post("/add/:user-name", (request, response) -> {
        	String name = request.params(":name");
        	return "hello-print " + name;
        });
        get("/number", (request, response) -> {
        	Map<String, Object> m = new HashMap<String, Object>();
        	
        	HttpResponse<String> stringResponse = Unirest.get("http://localhost:8080/get")
        			.queryString("num1", "1")
        			.queryString("num2", "2")
        			.asString();
        	System.out.println(stringResponse.getBody());
        	System.out.println(stringResponse.getHeaders());
        	
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
        
        // TODO: POST an Server V2 mit x=3 y="zwei drei daten" an Server senden, der an eine zweite Server Instanz weiterleitet
        // V3 haben wir schon, das ist die interne Funktion
        // TODO: binary task, V2 soll aus zwei drei Daten ein Java Object bauen und an V4 senden
	}
	
    private static void igniteSecondSparkV2() {
    	Service http = Service.ignite()
                .port(8080)
                .threadPool(20);
    	
    	http.get("/", (q, a) -> "Hello from server V4 from port 8080!");
    	
    	http.get("/get", (request, response) -> {
    		System.out.println(request.queryString());
    		String req = request.queryString();
    		int num1 = Integer.parseInt(request.queryParams("num1"));
    		int num2 = Integer.parseInt(request.queryParams("num2"));
        	return "GET " + req + "\n num1 + num2: " + "\n" + (num1 + num2);
        });
    	
    	http.get("/object", (request, response) -> {
    		response.type("");
        	return "OBJ";
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
    
    class Transformer {
		double x;
		double y;
		
		public Transformer(double x, double y) {
			this.x = Math.pow(x, y);
			this.y = Math.pow(y, x);
		}
	}
    
    
}