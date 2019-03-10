package com.dk.job.service;

import static com.dk.job.util.RuleConfigConstants.PATTERN_REGEX_MAP;
import static com.dk.job.util.RuleConfigJobUtil.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dk.job.bean.RuleCsv;
import com.dk.job.exceptions.InvalidInputException;
import com.dk.job.json.EntityAttrBody;
import com.dk.job.json.RegexFormats;
import com.dk.job.util.RuleConfigConstants;
import com.dk.job.util.RuleConfigJobUtil;
import com.dk.job.util.RuleConfigConstants.PATTERN_NAME;

public class InvalidFormatAttributesGenerator implements AttributesGenerator {

	@Override
	public List<EntityAttrBody> getAttributes(RuleCsv ruleCsv)
			throws InvalidInputException {
		List<EntityAttrBody> entityAttrBodyLst = new ArrayList<>();

		EntityAttrBody entityAttrBody = RuleJsonBuilderService.getCommonAttributes(ruleCsv.getSourceSystemCode(),
				ruleCsv.getEntity(),ruleCsv.getAttribute(), true);

		entityAttrBody.setPatternName(ruleCsv.getPatternName());

		List<RegexFormats> regexFormats = new ArrayList<>();
		try {
			PATTERN_NAME pattern = PATTERN_NAME.valueOf(ruleCsv.getPatternName());
			switch (pattern) {
				case Length:
					regexFormats = getRegexForLenghtPattern(ruleCsv);
					break;
				case Others:
					// check for valid regex , else throw exception
					RuleConfigJobUtil.isValidRegexExpression(ruleCsv.getRegularExpression());
					regexFormats = Arrays.asList(new RegexFormats(ruleCsv
							.getRegularExpression(), RuleConfigConstants.BLANK));
					break;
				default:
					// for all others pattern, get the predefined regex
					regexFormats = PATTERN_REGEX_MAP.get(ruleCsv.getPatternName());
					break;
			}// end of switch case
		} catch (Exception e) {
			throw new InvalidInputException(getMessageIfNotPresent(e,
					RuleConfigConstants.PATTERN_NAME_ERR));
		}
		entityAttrBody.setRegexFormats(regexFormats);
		entityAttrBodyLst.add(entityAttrBody);
		return entityAttrBodyLst;
	}

	/**
	 * function handles the pattern Length under Invalid format
	 * 
	 * @param ruleCsv
	 * @return
	 * @throws InvalidInputException
	 */
	public List<RegexFormats> getRegexForLenghtPattern(RuleCsv ruleCsv)
			throws InvalidInputException {

		String value = ruleCsv.getValue();
		int intValue = 0;
		// only positive integers
		try {
			if (StringUtils.isNotBlank(value)) {
				intValue = Integer.valueOf(value);
				// check if the number is positive or not
				if (intValue < 0)
					throw new InvalidInputException(RuleConfigConstants.INVALID_NUMBER_VALUE);
			}
		} catch (NumberFormatException e) {
			// this exception will be raised when converting String to Int
			throw new InvalidInputException(RuleConfigConstants.INVALID_NUMBER_VALUE);
		}
		String regex = RuleConfigConstants.BLANK;
		String regexPrefix = ".";

		// build regex for different operator and value
		switch (ruleCsv.getOperator()) {
		case "=":
			regex = regexPrefix + "{" + intValue + "}";
			break;
		case ">":
			intValue++;
			regex = regexPrefix + "{" + intValue + ",}";
			break;
		case ">=":
			regex = regexPrefix + "{" + intValue + ",}";
			break;
		case "<":
			intValue--;
			regex = regexPrefix + "{0," + intValue + "}";
			break;
		case "<=":
			regex = regexPrefix + "{0," + intValue + "}";
			break;
		case "BETWEEN":
			regex = regexPrefix + "{" + value + 1 + ",}";
			break;
		default:
			throw new InvalidInputException(
					RuleConfigConstants.INVALID_OPERATOR);
		}// end of switch case

		return Arrays
				.asList(new RegexFormats(regex, RuleConfigConstants.BLANK));
	}

}
