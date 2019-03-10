package com.dk.job.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class DataDictionary {
	private List<DDEntities> ddEntities;

	public List<DDEntities> getDdEntities() {
		return ddEntities;
	}

	public void setDdEntities(List<DDEntities> ddEntities) {
		this.ddEntities = ddEntities;
	}
	
	public DDEntities findDDEntity(String entityName){
		DDEntities ddEntityRes=null;
		for(DDEntities ddEntity:ddEntities){
			if(entityName.equalsIgnoreCase(ddEntity.getEntityName())){
				ddEntityRes= ddEntity;
				break;
			}
		}
		return ddEntityRes;
	}
}

