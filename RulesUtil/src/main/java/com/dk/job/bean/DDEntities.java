package com.dk.job.bean;

import java.util.Map;

public class DDEntities {
	private String entityName;
	private String shortName;
	private Map<String, DDEntityAttributes> attributeKeyMap;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Map<String, DDEntityAttributes> getAttributeKeyMap() {
		return attributeKeyMap;
	}

	public void setAttributeKeyMap(Map<String, DDEntityAttributes> attributeKeyMap) {
		this.attributeKeyMap = attributeKeyMap;
	}

	@Override
	public String toString() {
		return "DDEntities [entityName=" + entityName + ", attributeKeyMap="
				+ attributeKeyMap + "]";
	}

}
