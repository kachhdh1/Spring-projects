package com.dk.job.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityBody {
	@JsonProperty("isPrimary")
	private boolean isPrimary;
	private String name;
	private List<EntityAttrBody> entityAttributes;

	public EntityBody(boolean isPrimary, String name, List<EntityAttrBody> entityAttributes) {
		super();
		this.isPrimary = isPrimary;
		this.name = name;
		this.setEntityAttributes(entityAttributes);
	}

	public boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EntityAttrBody> getEntityAttributes() {
		return entityAttributes;
	}

	public void setEntityAttributes(List<EntityAttrBody> entityAttributes) {
		this.entityAttributes = entityAttributes;
	}

}
