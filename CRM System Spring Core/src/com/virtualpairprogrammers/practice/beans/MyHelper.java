package com.virtualpairprogrammers.practice.beans;

import javax.annotation.PreDestroy;

public class MyHelper {
	
	public void doSomethingHelpful(){
		System.out.println("helpful method from MyHelper class");
	}

	@PreDestroy
	public void destroy(){
		System.out.println("Destroying bean : - cleaning up resources before destroy");
	}
}
