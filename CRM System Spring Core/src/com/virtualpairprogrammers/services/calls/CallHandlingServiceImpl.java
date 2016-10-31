package com.virtualpairprogrammers.services.calls;

import java.util.Collection;

import com.virtualpairprogrammers.domain.Action;
import com.virtualpairprogrammers.domain.Call;
import com.virtualpairprogrammers.services.customers.CustomerManagementService;
import com.virtualpairprogrammers.services.customers.CustomerNotFoundException;
import com.virtualpairprogrammers.services.diary.DiaryManagementService;

/**
 * this is common interface which is created to achieve complex two 
 * process action which is to recordCall.
 * @author Dharmik
 *
 */
public class CallHandlingServiceImpl implements CallHandlingService {
	
	//private variables
	CustomerManagementService customerService;
	DiaryManagementService diaryService;
	
	//constructor injection
	CallHandlingServiceImpl(CustomerManagementService cms,
			DiaryManagementService ds){
		this.customerService = cms;
		this.diaryService = ds;
	}

	@Override
	public void recordCall(String customerId, Call newCall,
			Collection<Action> actions) throws CustomerNotFoundException {
		
		customerService.recordCall(customerId, newCall);
		
		for(Action action:actions){
			diaryService.recordAction(action);
		}

	}

}
