package com.dk.job.bean;

import org.apache.commons.lang3.StringUtils;

public class RuleCsv {

	private String sourceSystemCode;

	private String patternType;

	private String entity;
	
	//added for incorrect sequence
	private String entity2;

	private String attribute;

	private String attribute2;

	private String ruleName;

	//for null check
	private String attributeList;
	
	//should be error/warning
	private String ruleStatus;
	
	private String sourceFormat;
	
	private String targetFormat;
	
	private String patternName;
	
	private String operator;
	
	private String value;
	
	private String regularExpression;
	
	//for code standardization, invalid code lookup
	private String lookupCategory;
	private String lookupTable;
	private String beforeCode;
	private String afterCode;
	
	//the flag is used to enable/disable rule from csv. Values (Y/N)
	private String process;

	public RuleCsv() {
	}

	public RuleCsv(String sourceSystemCode, String patternType, String entitiy,
			String attribute, String attribute2, String ruleId, String action,
			String ruleName) {
		super();
		this.sourceSystemCode = sourceSystemCode;
		this.patternType = patternType;
		this.entity = entitiy;
		this.attribute = attribute;
		this.attribute2 = attribute2;
		this.ruleName = ruleName;
	}

	@Override
	public String toString() {
		return "RuleCsv [sourceSystemCode=" + sourceSystemCode
				+ ", patternType=" + patternType + ", entitiy=" + entity
				+ ", attributeName=" + attribute + ", ruleName=" + ruleName + "]";
	}
	
	public RuleCsv cleanAndTrimInput(RuleCsv ruleCsv){
		
		ruleCsv.setPatternType(StringUtils.isNoneEmpty(ruleCsv.getPatternType())?ruleCsv.getPatternType().trim():ruleCsv.getPatternType());
		ruleCsv.setSourceSystemCode(StringUtils.isNoneEmpty(ruleCsv.getSourceSystemCode())?ruleCsv.getSourceSystemCode().trim():ruleCsv.getSourceSystemCode());
		ruleCsv.setEntity(StringUtils.isNoneEmpty(ruleCsv.getEntity())?ruleCsv.getEntity().trim().toUpperCase():ruleCsv.getEntity());
		ruleCsv.setAttribute(StringUtils.isNoneEmpty(ruleCsv.getAttribute())?ruleCsv.getAttribute().trim().toUpperCase():ruleCsv.getAttribute());
		ruleCsv.setAttributeList(StringUtils.isNoneEmpty(ruleCsv.getAttributeList())?ruleCsv.getAttributeList().trim().toUpperCase():ruleCsv.getAttributeList());
		ruleCsv.setAttribute2(StringUtils.isNoneEmpty(ruleCsv.getAttribute2())?ruleCsv.getAttribute2().trim().toUpperCase():ruleCsv.getAttribute2());
		ruleCsv.setSourceFormat(StringUtils.isNoneEmpty(ruleCsv.getSourceFormat())?ruleCsv.getSourceFormat().trim():ruleCsv.getSourceFormat());
		ruleCsv.setTargetFormat(StringUtils.isNoneEmpty(ruleCsv.getTargetFormat())?ruleCsv.getTargetFormat().trim():ruleCsv.getTargetFormat());
		ruleCsv.setValue(StringUtils.isNoneEmpty(ruleCsv.getValue())?ruleCsv.getValue().trim():ruleCsv.getValue());
		return ruleCsv;
	}

	public String getSourceSystemCode() {
		return sourceSystemCode;
	}

	public void setSourceSystemCode(String sourceSystemCode) {
		this.sourceSystemCode = sourceSystemCode;
	}

	public String getPatternType() {
		return patternType;
	}

	public void setPatternType(String patternType) {
		this.patternType = patternType;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(String attributeList) {
		this.attributeList = attributeList;
	}

	public String getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
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

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getRegularExpression() {
		return regularExpression;
	}

	public void setRegularExpression(String regularExpression) {
		this.regularExpression = regularExpression;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getBeforeCode() {
		return beforeCode;
	}

	public void setBeforeCode(String beforeCode) {
		this.beforeCode = beforeCode;
	}

	public String getAfterCode() {
		return afterCode;
	}

	public void setAfterCode(String afterCode) {
		this.afterCode = afterCode;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getEntity2() {
		return entity2;
	}

	public void setEntity2(String entity2) {
		this.entity2 = entity2;
	}

}
