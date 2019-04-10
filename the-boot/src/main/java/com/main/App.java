package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * keep the main class in the top of project and above 
 * all spring component to be able to scan the nested 
 * spring components
 * App.java is under com.main and all other components
 * are under com.main
 * 
 * H2 login = http://localhost:8081/h2
 * Application login = http://localhost:8081/index.htm
 * 
 * @author dharmikk
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
