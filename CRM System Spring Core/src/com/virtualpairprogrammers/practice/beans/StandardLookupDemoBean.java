package com.virtualpairprogrammers.practice.beans;

public class StandardLookupDemoBean implements DummyBean{
	
	MyHelper myHelper;

	public MyHelper getMyHelper() {
		return myHelper;
	}

	public void someOperation() {
		myHelper.doSomethingHelpful();
	}

	public void setMyHelper(MyHelper myHelper) {
		this.myHelper = myHelper;
	}
	
	@Override
	public String toString(){
		return "StandardLookupDemoBean";
	}

}
