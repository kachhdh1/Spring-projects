package com.dk.job.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DDEntityAttributes {
	private String attributeName;
	private String dataType;
	@JsonIgnore
	private String isKeyIdentifier;
	private String shortName;

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getIsKeyIdentifier() {
		return isKeyIdentifier;
	}

	public void setIsKeyIdentifier(String isKeyIdentifier) {
		this.isKeyIdentifier = isKeyIdentifier;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public String toString() {
		return "DDEntityAttributes [attributeName=" + attributeName
				+ ", dataType=" + dataType + ", shortName=" + shortName + "]";
	}

}
