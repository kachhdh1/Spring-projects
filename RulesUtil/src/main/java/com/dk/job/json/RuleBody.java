package com.dk.job.json;

import java.util.Date;

public class RuleBody {
	private String ruleName;
	private String ruleId;
	private String sourceSystemCode;
	private String ruleType;
	private String patternType;
	private String createdBy;
	private String ruleStatus;
	private Date createdDatetime;
	private EntityBody[] entities;
	private String modelType;
	private String lookupCategory;
	private String lookupTable;
	private LookupTableColumn lookupTableColumns;

	public RuleBody(String sourceSystemCode, String ruleType, String patternType, String createdBy, String ruleStatus,
			Date createdDatetime, EntityBody[] entities,String modelType,String ruleName,String ruleId) {
		super();
		this.sourceSystemCode = sourceSystemCode;
		this.ruleType = ruleType;
		this.patternType = patternType;
		this.createdBy = createdBy;
		this.ruleStatus = ruleStatus;
		this.createdDatetime = createdDatetime;
		this.entities = entities;
		this.modelType=modelType;
		this.ruleName = ruleName;
		this.ruleId = ruleId;
	}

	public String getSourceSystemCode() {
		return sourceSystemCode;
	}

	public void setSourceSystemCode(String sourceSystemCode) {
		this.sourceSystemCode = sourceSystemCode;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getPatternType() {
		return patternType;
	}

	public void setPatternType(String patternType) {
		this.patternType = patternType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public Date getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public EntityBody[] getEntities() {
		return entities;
	}

	public void setEntities(EntityBody[] entities) {
		this.entities = entities;
	}
	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getLookupCategory() {
		return lookupCategory;
	}

	public void setLookupCategory(String lookupCategory) {
		this.lookupCategory = lookupCategory;
	}

	public String getLookupTable() {
		return lookupTable;
	}

	public void setLookupTable(String lookupTable) {
		this.lookupTable = lookupTable;
	}

	public LookupTableColumn getLookupTableColumns() {
		return lookupTableColumns;
	}

	public void setLookupTableColumns(LookupTableColumn lookupTableColumns) {
		this.lookupTableColumns = lookupTableColumns;
	}

}
