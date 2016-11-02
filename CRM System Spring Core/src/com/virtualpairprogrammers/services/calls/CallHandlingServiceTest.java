package com.virtualpairprogrammers.services.calls;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import static org.mockito.Mockito.*;

import com.virtualpairprogrammers.domain.Action;
import com.virtualpairprogrammers.domain.Call;
import com.virtualpairprogrammers.services.customers.CustomerManagementService;
import com.virtualpairprogrammers.services.customers.CustomerNotFoundException;
import com.virtualpairprogrammers.services.diary.DiaryManagementService;

public class CallHandlingServiceTest {

	@Test
	public void recordCallTest() {
		CustomerManagementService customerService = mock(CustomerManagementService.class);
		DiaryManagementService diaryService = mock(DiaryManagementService.class);
		
		CallHandlingService callService = new CallHandlingServiceImpl(customerService,
				diaryService);
		Call newCall = new Call("Dharmik test");
		Action action1 = new Action("Call dharmik", new GregorianCalendar(2016, 0, 0), "dk");
		List<Action> actions = new ArrayList<Action>();
		actions.add(action1);
		try {
			callService.recordCall("CS03939", newCall, actions);
			verify(customerService,times(1)).recordCall("CS03939", newCall);
		} catch (CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
