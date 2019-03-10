package com.dk.job.service;

import java.util.ArrayList;
import java.util.List;

import com.dk.job.bean.RuleCsv;
import com.dk.job.json.EntityAttrBody;

public class DateFormatAttributesGenerator implements AttributesGenerator{

	@Override
	public List<EntityAttrBody> getAttributes(RuleCsv ruleCsv) {
		List<EntityAttrBody> entityAttrBodyLst = new ArrayList<>();
		EntityAttrBody entityAttrBody=RuleJsonBuilderService.
				getCommonAttributes(ruleCsv.getSourceSystemCode(),ruleCsv.getEntity(),ruleCsv.getAttribute(), true);
		entityAttrBody.setSourceFormat(ruleCsv.getSourceFormat());
		entityAttrBody.setTargetFormat(ruleCsv.getTargetFormat());
		entityAttrBodyLst.add(entityAttrBody);
		return entityAttrBodyLst;
	}

}
