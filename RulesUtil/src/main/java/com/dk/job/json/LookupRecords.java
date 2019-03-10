package com.dk.job.json;

import java.util.List;

public class LookupRecords {

	private String sourceSystemCode;
	private String sourceSystemName;
	private String lookupCategory;
	private List<LookupCodes> lookupCodes;
	
	public LookupRecords(String sourceSystemCode, String sourceSystemName,
			String lookupCategory, List<LookupCodes> lookupCodes) {
		super();
		this.sourceSystemCode = sourceSystemCode;
		this.sourceSystemName = sourceSystemName;
		this.lookupCategory = lookupCategory;
		this.lookupCodes = lookupCodes;
	}

	public String getSourceSystemCode() {
		return sourceSystemCode;
	}

	public void setSourceSystemCode(String sourceSystemCode) {
		this.sourceSystemCode = sourceSystemCode;
	}

	public String getSourceSystemName() {
		return sourceSystemName;
	}

	public void setSourceSystemName(String sourceSystemName) {
		this.sourceSystemName = sourceSystemName;
	}

	public String getLookupCategory() {
		return lookupCategory;
	}

	public void setLookupCategory(String lookupCategory) {
		this.lookupCategory = lookupCategory;
	}

	public List<LookupCodes> getLookupCodes() {
		return lookupCodes;
	}

	public void setLookupCodes(List<LookupCodes> lookupCodes) {
		this.lookupCodes = lookupCodes;
	}
}
