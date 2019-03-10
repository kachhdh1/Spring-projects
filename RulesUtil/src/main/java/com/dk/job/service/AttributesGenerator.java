package com.dk.job.service;

import java.util.ArrayList;
import java.util.List;

import com.dk.job.bean.RuleCsv;
import com.dk.job.exceptions.InvalidInputException;
import com.dk.job.json.EntityAttrBody;

@FunctionalInterface
public interface AttributesGenerator {
	
	public List<EntityAttrBody> getAttributes(RuleCsv ruleCsv) throws InvalidInputException;
	
	//the method is used in case of Incorrect sequence when there are two entities defined
	default List<EntityAttrBody> getAttributesForAnEntity(String sourceSystemCode,String entityName,String attributeName,boolean isPrimamry) 
			throws InvalidInputException {
		List<EntityAttrBody> entityAttrBodyLst = new ArrayList<>();
		EntityAttrBody entityAttrBodyOne=RuleJsonBuilderService.
				getCommonAttributes(sourceSystemCode,entityName,attributeName, isPrimamry);
		entityAttrBodyLst.add(entityAttrBodyOne);
		return entityAttrBodyLst;
	}
	
}
