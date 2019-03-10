package com.dk.job.service;

import static com.dk.job.util.RuleConfigJobUtil.getMessageIfNotPresent;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dk.job.bean.RuleCsv;
import com.dk.job.exceptions.InvalidInputException;
import com.dk.job.json.EntityAttrBody;
import com.dk.job.json.LookupTableColumn;
import com.dk.job.json.RuleBody;
import com.dk.job.util.RuleConfigConstants;
import com.dk.job.util.RuleConfigConstants.PATTERN_TYPE;

public class AttributesGeneratorClient {

	private AttributesGenerator attributesGenerator;
	
	public AttributesGeneratorClient(String patternType) throws InvalidInputException{
		setGenerator(patternType);
	}

	// logic for getting attributes according to pattern type
	private void setGenerator(String patternType) throws InvalidInputException {
		try{
			PATTERN_TYPE pattern = PATTERN_TYPE.valueOf(patternType);
	
			switch (pattern) {
				case NULL_CHECK:
					setAttributesGenerator(new NullCheckAttributesGenerator());
					break;
				case DATE_FORMAT_STANDARDIZATION:
					setAttributesGenerator(new DateFormatAttributesGenerator());
					break;
				case CODE_STANDARDIZATION:
					setAttributesGenerator(new CodeStdnAttributesGenerator());
					break;
				case INCORRECT_SEQUENCE:
					setAttributesGenerator(new IncorrectSequenceAttributesGenerator());
					break;
				case INVALID_CODE_LOOK_UP:
					setAttributesGenerator(new CodeStdnAttributesGenerator());
					break;
				case INVALID_FORMAT:
					setAttributesGenerator(new InvalidFormatAttributesGenerator());
					break;
				default:
					throw new InvalidInputException(RuleConfigConstants.PATTERN_TYPE_ERR);
			}//end of switch case
		}catch(Exception e){
			throw new InvalidInputException(getMessageIfNotPresent(e,RuleConfigConstants.PATTERN_TYPE_ERR));
		}
		
	}
	
	public List<EntityAttrBody> getAttributesForPattern(RuleCsv ruleCsv) throws InvalidInputException{
		return attributesGenerator.getAttributes(ruleCsv);
	}
	
	public void setLookupParams(RuleCsv ruleCsv, RuleBody ruleBody){
		if(ruleCsv.getPatternType().equalsIgnoreCase(PATTERN_TYPE.INVALID_CODE_LOOK_UP.name())
				|| ruleCsv.getPatternType().equalsIgnoreCase(PATTERN_TYPE.CODE_STANDARDIZATION.name())){
			//check if lookup category is not present then it is for reference table
			if(StringUtils.isEmpty(ruleCsv.getLookupCategory()) && 
					StringUtils.isNotEmpty(ruleCsv.getLookupTable())){
				ruleBody.setLookupTable(ruleCsv.getLookupTable());
				
				ruleBody.setLookupTableColumns(
						new LookupTableColumn(ruleCsv.getBeforeCode(),ruleCsv.getAfterCode()));
			}
			else{
				ruleBody.setLookupCategory(ruleCsv.getLookupCategory());
			}
		}
	}
	

	public AttributesGeneratorClient(AttributesGenerator attributesGenerator) {
		super();
		this.attributesGenerator = attributesGenerator;
	}

	public AttributesGenerator getAttributesGenerator() {
		return attributesGenerator;
	}

	public void setAttributesGenerator(AttributesGenerator attributesGenerator) {
		this.attributesGenerator = attributesGenerator;
	}

}
