package com.dk.job.service;

import static com.dk.job.util.RuleConfigConstants.ATTRIBUTE_LIST_SEPERATOR;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dk.job.bean.RuleCsv;
import com.dk.job.json.EntityAttrBody;
import com.dk.job.util.RuleConfigJobUtil;

public class NullCheckAttributesGenerator implements AttributesGenerator {
	
	private static final Logger logger = LogManager.getLogger(NullCheckAttributesGenerator.class);

	@Override
	public List<EntityAttrBody> getAttributes(RuleCsv ruleCsv) {
		List<EntityAttrBody> entityAttrBodyLst = new ArrayList<>();
		String[] attributeArray = ruleCsv.getAttributeList().split(ATTRIBUTE_LIST_SEPERATOR);
		for(String attribute:attributeArray){
			//all the attribute isPrimary should be false
			Optional<String> shortKey = RuleConfigJobUtil.findShortKey(ruleCsv.getSourceSystemCode(),
					ruleCsv.getEntity(), attribute);
			
			if(shortKey.isPresent()){
				entityAttrBodyLst.add(new EntityAttrBody(false,
						attribute, shortKey.get()));
			}else{
				logger.info("Short key not present for : "+attribute);
			}
		}
		return entityAttrBodyLst;
	}

}
