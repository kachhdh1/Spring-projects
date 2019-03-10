package com.dk.job.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class ResponseObj {

	private String statusCode;

	private List<RuleResponse> ruleResponse;

	public ResponseObj() {
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public List<RuleResponse> getRuleResponse() {
		return ruleResponse;
	}

	public void setRuleResponse(List<RuleResponse> ruleResponse) {
		this.ruleResponse = ruleResponse;
	}
	
	public String fetchRuleId(){
		String ruleId="";
		if(null!=ruleResponse && ruleResponse.size()>0)
			return ruleResponse.get(0).getRuleId();
		return ruleId;
	}
	
	public String fetchErrMessage(){
		String msg="";
		if(null!=ruleResponse && ruleResponse.size()>0)
			return ruleResponse.get(0).getReason();
		return msg;
	}

}

class RuleResponse {
	private String ruleId;
	private String message;
	private String reason;

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
