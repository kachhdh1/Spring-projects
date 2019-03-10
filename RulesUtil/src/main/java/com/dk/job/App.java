package com.dk.job;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.dk.job.controller.JobController;

@SpringBootApplication
public class App {

	public static void main(String[] args) throws BeansException, Exception {
		ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
		
		context.getBean(JobController.class).run();
		context.close();
	}
} 
