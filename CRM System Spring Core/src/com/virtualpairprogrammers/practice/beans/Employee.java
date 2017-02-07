package com.virtualpairprogrammers.practice.beans;

/**
 * Assume that this class is interface and being implented by two
 * concrete subclasses like manager and director just like factory
 * pattern.
 * @author kachhdh1
 *
 */
public class Employee {
	
    private String firstName;
    private String lastName;
    private String designation;
 
    @Override
    public String toString() {
        return "Employee [firstName=" + firstName
                + ", lastName=" + lastName + ", type=" + designation + "]";
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

}
