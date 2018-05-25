package de.htw.app;

import static spark.Spark.*;

public class HelloWorld {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
        get("/hello-print/*", (req, res) -> {
        	return "hello-print";
        });
    }
}