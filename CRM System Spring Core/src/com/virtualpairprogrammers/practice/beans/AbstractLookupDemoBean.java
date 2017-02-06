package com.virtualpairprogrammers.practice.beans;

/**
 * the instantiation of the abstract class is supported only when using Lookup Method Injection, 
 * in which Spring will use CGLIB to generate a subclass of the AbstractLookupDemoBean class 
 * that overrides the method dynamically
 * @author kachhdh1
 */
public abstract class AbstractLookupDemoBean implements DummyBean{

	/*
	 * we can make this function as abstract or concrete one. But in both the cases,
	 * this function will be called by spring to inject the prototype bean (MyHelper)
	 * into the singleton bean (this)
	 * The name attribute of the <lookup-method> tag tells Spring the name of the method 
	 * on the bean that it should override. This method must not accept any arguments, 
	 * and the return type should be that of the bean you want to return from the method.
	 */
	public abstract MyHelper getMyHelper();

	public void someOperation() {
		getMyHelper().doSomethingHelpful();
	}
	
	@Override
	public String toString(){
		return "AbstractLookupDemoBean";
	}

}
