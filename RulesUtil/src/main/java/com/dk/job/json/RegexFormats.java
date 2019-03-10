package com.dk.job.json;

public class RegexFormats {
	
	private String regularExpression;
	private String sampleValue;
	private String displayRegexFormat;
	
	public RegexFormats(){}
	
	public RegexFormats(String regularExpression, String displayRegexFormat) {
		super();
		this.regularExpression = regularExpression;
		this.displayRegexFormat=displayRegexFormat;
	}


	public String getRegularExpression() {
		return regularExpression;
	}
	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}

	public String getSampleValue() {
		return sampleValue;
	}

	public void setSampleValue(String sampleValue) {
		this.sampleValue = sampleValue;
	}

	public String getDisplayRegexFormat() {
		return displayRegexFormat;
	}

	public void setDisplayRegexFormat(String displayRegexFormat) {
		this.displayRegexFormat = displayRegexFormat;
	}

}
