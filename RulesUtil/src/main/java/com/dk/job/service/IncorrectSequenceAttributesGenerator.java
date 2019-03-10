package com.dk.job.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dk.job.bean.RuleCsv;
import com.dk.job.json.EntityAttrBody;

public class IncorrectSequenceAttributesGenerator implements
		AttributesGenerator {

	//only two attributes can be selected
	@Override
	public List<EntityAttrBody> getAttributes(RuleCsv ruleCsv) {
		List<EntityAttrBody> entityAttrBodyLst = new ArrayList<>();
		EntityAttrBody entityAttrBody1=RuleJsonBuilderService.
				getCommonAttributes(ruleCsv.getSourceSystemCode(),ruleCsv.getEntity(),ruleCsv.getAttribute(), true);
		entityAttrBodyLst.add(entityAttrBody1);
		
		//if attribute2 is present,repeat the process
		if(StringUtils.isNotEmpty(ruleCsv.getAttribute2())){
			EntityAttrBody entityAttrBody2=RuleJsonBuilderService.
					getCommonAttributes(ruleCsv.getSourceSystemCode(),ruleCsv.getEntity(),ruleCsv.getAttribute2(), false);
			entityAttrBodyLst.add(entityAttrBody2);
		}
		return entityAttrBodyLst;
	}

}
