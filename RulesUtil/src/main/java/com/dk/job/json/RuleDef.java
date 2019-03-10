package com.dk.job.json;

public class RuleDef {
	private String organizationCode;
	private RuleBody[] rules;
	

	public RuleDef(String organizationCode, RuleBody[] rules) {
		super();
		this.organizationCode = organizationCode;
		this.rules = rules;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public RuleBody[] getRules() {
		return rules;
	}

	public void setRules(RuleBody[] rules) {
		this.rules = rules;
	}

	

}
