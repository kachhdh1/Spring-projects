package com.dk.job.bean;

public class DataDictionaryBody {
	
	DataDictionaryBody(){}
	
	public DataDictionaryBody(String modelType, String sourceSystemCode) {
		super();
		this.modelType = modelType;
		this.sourceSystemCode = sourceSystemCode;
	}

	private String modelType;
	private String sourceSystemCode;
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public String getSourceSystemCode() {
		return sourceSystemCode;
	}
	public void setSourceSystemCode(String sourceSystemCode) {
		this.sourceSystemCode = sourceSystemCode;
	}
	
	

}
