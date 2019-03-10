package com.dk.job.json;

import java.util.List;

public class LookupDef {
	private String organizationCode;
	
	private List<LookupRecords> lookupRecords;
	
	LookupDef(){}
	
	public LookupDef(String organizationCode, List<LookupRecords> lookupRecords) {
		super();
		this.organizationCode = organizationCode;
		this.lookupRecords = lookupRecords;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public List<LookupRecords> getLookupRecords() {
		return lookupRecords;
	}

	public void setLookupRecords(List<LookupRecords> lookupRecords) {
		this.lookupRecords = lookupRecords;
	}

}
