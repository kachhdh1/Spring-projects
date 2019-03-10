package com.dk.job.service;

import java.util.ArrayList;
import java.util.List;

import com.dk.job.bean.RuleCsv;
import com.dk.job.exceptions.InvalidInputException;
import com.dk.job.json.EntityAttrBody;

public class CodeStdnAttributesGenerator implements AttributesGenerator {

	@Override
	public List<EntityAttrBody> getAttributes(RuleCsv ruleCsv)
			throws InvalidInputException {
		List<EntityAttrBody> entityAttrBodyLst = new ArrayList<>();
		EntityAttrBody entityAttrBody=RuleJsonBuilderService.
				getCommonAttributes(ruleCsv.getSourceSystemCode(), ruleCsv.getEntity(),ruleCsv.getAttribute(), true);
		
		entityAttrBodyLst.add(entityAttrBody);
		return entityAttrBodyLst;
	}

}
