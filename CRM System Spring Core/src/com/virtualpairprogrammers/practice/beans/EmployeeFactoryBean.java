package com.virtualpairprogrammers.practice.beans;

public class EmployeeFactoryBean {
	
	public static Employee createEmployeeOfType(String type) 
    {
        if ("manager".equals(type) ) 
        {
        	Employee manager = new Employee();
        	manager.setFirstName("manager");
        
            //Set designation here, just like factory bean,
            //create a manager bean here else create 
            //a director bean.
        	manager.setDesignation(type);
             
            return manager;//of type manager
        }
        else if("director".equals(type)){
        	Employee dir = new Employee();
            dir.setFirstName("manager");
            dir.setDesignation(type); //of type director
            return dir;
        }
        else
        {
            throw new IllegalArgumentException("Unknown product");
        }
		
    }

}
