package com.dk.job.json;

public class LookupCodes {
	
	private String beforeCode;
    private String afterCode;
    
	public LookupCodes(String beforeCode, String afterCode) {
		super();
		this.beforeCode = beforeCode;
		this.afterCode = afterCode;
	}
	public String getBeforeCode() {
		return beforeCode;
	}
	public void setBeforeCode(String beforeCode) {
		this.beforeCode = beforeCode;
	}
	public String getAfterCode() {
		return afterCode;
	}
	public void setAfterCode(String afterCode) {
		this.afterCode = afterCode;
	}
    

}
