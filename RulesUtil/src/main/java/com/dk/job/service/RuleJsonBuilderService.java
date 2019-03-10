package com.dk.job.service;

import static com.dk.job.util.RuleConfigConstants.CREATEDBY;
import static com.dk.job.util.RuleConfigConstants.ERROR_RS;
import static com.dk.job.util.RuleConfigConstants.INVALID_RS;
import static com.dk.job.util.RuleConfigConstants.NOT_AVAILABLE;
import static com.dk.job.util.RuleConfigConstants.RULE_ID;
import static com.dk.job.util.RuleConfigConstants.RULE_TYPE_QUALITY;
import static com.dk.job.util.RuleConfigConstants.STANDARDIZATION;
import static com.dk.job.util.RuleConfigConstants.WARNING_RS;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dk.job.bean.RuleCsv;
import com.dk.job.exceptions.InvalidInputException;
import com.dk.job.json.EntityAttrBody;
import com.dk.job.json.EntityBody;
import com.dk.job.json.RuleBody;
import com.dk.job.json.RuleDef;
import com.dk.job.json.RuleJson;
import com.dk.job.util.RuleConfigJobUtil;

@Component
public class RuleJsonBuilderService {
	
	@Value("${organiation.code}")
	private String orgCode;
	@Value("${model.type}")
	private String modelType;
	
	public RuleJson buildRuleJson(RuleCsv ruleConfigCsv) throws InvalidInputException {
		RuleJson ruleJsonModel = new RuleJson();
		EntityBody[] entities = null;
		
		//set pattern type for attributes generator client
		AttributesGeneratorClient client = new AttributesGeneratorClient(ruleConfigCsv.getPatternType());
		
		//-----------------------------------------------------------
		//if pattern type is incorrect sequence
		/*if(PATTERN_TYPE.INCORRECT_SEQUENCE.name().equals(ruleConfigCsv.getPatternType())){
			entities = client.getEntityDetailsForIncorrectSeq(ruleConfigCsv);
		}else{
			List<EntityAttrBody> entityAttrBodyLst =client.getAttributesForPattern(ruleConfigCsv);
			
			EntityBody entityBody = new EntityBody(true,
					ruleConfigCsv.getEntity(), entityAttrBodyLst);
			entities = new EntityBody[] { entityBody };
		}*/
		//-----------------------------------------------------------
		List<EntityAttrBody> entityAttrBodyLst =client.getAttributesForPattern(ruleConfigCsv);
		
		EntityBody entityBody = new EntityBody(true,
				ruleConfigCsv.getEntity(), entityAttrBodyLst);
		entities = new EntityBody[] { entityBody };
		
		RuleBody ruleBody = new RuleBody(
				ruleConfigCsv.getSourceSystemCode(), 
				getRuleTypeForPattern(ruleConfigCsv.getPatternType()), 
				ruleConfigCsv.getPatternType(), 
				CREATEDBY, 
				getRuleStatus(ruleConfigCsv.getRuleStatus()), 
				new Date(),
				//new EntityBody[] { entityBody }, 
				entities,
				modelType,
				getAutoRuleNameIfEmpty(ruleConfigCsv), 
				RULE_ID); 
		
		//set extra parameters for code standardization and invalid code lookup
		client.setLookupParams(ruleConfigCsv,ruleBody);

		RuleDef ruleDef = new RuleDef(orgCode, new RuleBody[] { ruleBody });
		ruleJsonModel.setRuleDef(ruleDef);
		return ruleJsonModel;
	}

	public static EntityAttrBody getCommonAttributes(String sourceSystemCode,String entityName, String attributeName, boolean isPrimary){
		return new EntityAttrBody(isPrimary,
				attributeName, RuleConfigJobUtil.findShortKey(sourceSystemCode,entityName, attributeName).orElse(NOT_AVAILABLE));
	}
	
	public String getAutoRuleNameIfEmpty(RuleCsv ruleCsv) {
		if(StringUtils.isAllEmpty(ruleCsv.getRuleName())){
			//generate a new rule name based on input
			return "rule_NA";
		}
		return ruleCsv.getRuleName();
	}
	
	private String getRuleStatus(String ruleStatus) {
		if(StringUtils.isNoneEmpty(ruleStatus)){
			return ruleStatus.equalsIgnoreCase(ERROR_RS)?INVALID_RS:WARNING_RS;
		}
		return WARNING_RS;
	}
	
	/**
	 * returns rule type value based on the pattern type
	 * @param patternType
	 * @return
	 */
	private String getRuleTypeForPattern(String patternType) {
		if(StringUtils.isNoneEmpty(patternType)){
			if(patternType.toUpperCase().contains(STANDARDIZATION)){
				return STANDARDIZATION;
			}
		}
		return RULE_TYPE_QUALITY;
	}
}
