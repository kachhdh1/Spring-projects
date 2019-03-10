package com.dk.job.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dk.job.util.RuleConfigConstants;

@Entity
@Table(name = RuleConfigConstants.RULE_STATUS_DETAILS)
public class BatchRecordStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	private String ruleName;

	private String sourceSystemCode;

	private String patternType;

	private String entity;

	//this will be pipe separated attribute list
	private String attributeList;

	private String ruleId;
	
	@Lob
	private String errorMessage;
	
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "batch_id", nullable = false)
	private BatchStatus batch;
	
	public BatchStatus getBatch() {
		return batch;
	}

	public void setBatch(BatchStatus batch) {
		this.batch = batch;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BatchRecordStatus(){}

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

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(String attributeList) {
		this.attributeList = attributeList;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

}
