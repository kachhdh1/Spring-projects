package com.dk.job.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityAttrBody {
	@JsonProperty("isPrimary")
	private boolean isPrimary;
	private String attributeName;
	private String shortName;
	
	//for date format standardization
	private String sourceFormat;
	private String targetFormat;
	
	//for Invalid format
	private String patternName;
	private List<RegexFormats> regexFormats;

	public EntityAttrBody(boolean isPrimary, String attributeName, String shortName) {
		super();
		this.setIsPrimary(isPrimary);
		this.attributeName = attributeName;
		this.shortName = shortName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getSourceFormat() {
		return sourceFormat;
	}

	public void setSourceFormat(String sourceFormat) {
		this.sourceFormat = sourceFormat;
	}

	public String getTargetFormat() {
		return targetFormat;
	}

	public void setTargetFormat(String targetFormat) {
		this.targetFormat = targetFormat;
	}

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

	public List<RegexFormats> getRegexFormats() {
		return regexFormats;
	}

	public void setRegexFormats(List<RegexFormats> regexFormats) {
		this.regexFormats = regexFormats;
	}

}
