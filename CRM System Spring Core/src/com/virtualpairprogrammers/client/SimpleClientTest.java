package com.virtualpairprogrammers.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.virtualpairprogrammers.domain.Action;
import com.virtualpairprogrammers.domain.Call;
import com.virtualpairprogrammers.domain.Customer;
import com.virtualpairprogrammers.practice.beans.DummyBean;
import com.virtualpairprogrammers.practice.beans.Employee;
import com.virtualpairprogrammers.practice.beans.MyHelper;
import com.virtualpairprogrammers.services.calls.CallHandlingService;
import com.virtualpairprogrammers.services.customers.CustomerManagementService;
import com.virtualpairprogrammers.services.customers.CustomerNotFoundException;
import com.virtualpairprogrammers.services.diary.DiaryManagementService;

public class SimpleClientTest {

	public static void main(String[] args) 
	{
		ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
		
		DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);
		
		System.out.println("Here are the outstanding actions:");
		Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("rac");
		for (Action next: incompleteActions)
		{
			System.out.println(next);
		}
		
		//************* testing spring core concepts****************
		
		//even though we are getting the instance of the factory bean,
		//but we will get the Employee instance in return based on
		//the type defined.
		Employee manager = (Employee) container.getBean("empFac");
		System.out.println(manager);
		
		//************* testing spring core concepts ends **********
	
		container.close();
	}

	private static void displayInfo(DummyBean bean) {
		 MyHelper helper1 = bean.getMyHelper();
	     MyHelper helper2 = bean.getMyHelper();

	     System.out.println("Helper Instances the Same?:"+bean +" : "
	            + (helper1 == helper2));
	}

}
