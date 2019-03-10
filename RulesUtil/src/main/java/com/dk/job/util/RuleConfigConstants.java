package com.dk.job.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dk.job.json.RegexFormats;

public class RuleConfigConstants {

	// json response attributes
	public static final String RULE_ID = "";
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";

	// json payload attributes
	public static final String CREATEDBY = "ctadmin";
	public static final String RULE_NAME = "System";

	// authentication
	public static final String AUTHENTICATE_BODY_TOKEN = "authToken";

	// header values
	public static final String AUTHORIATION = "Authorization";

	// misc
	public static final String ATTRIBUTE_LIST_SEPERATOR = "\\|";
	public static final String LIST_SEPERATOR_DB = "|";
	public static final String BLANK = "";
	public static final String NOT_AVAILABLE = "NA";
	public static final String STANDARDIZATION = "STANDARDIZATION";

	//--------------------ENUMS---------------------
	public static enum PATTERN_TYPE {
		NULL_CHECK, 
		DATE_FORMAT_STANDARDIZATION,
		INCORRECT_SEQUENCE,
		INVALID_FORMAT,
		INVALID_CODE_LOOK_UP,
		CODE_STANDARDIZATION
	}
	
	public static enum PATTERN_NAME{
		Others,Emailid,Phone,SSN,Length
	}
	
	public static enum XLSX_SHEET_NAME {
		NULL_CHECK, 
		DATE_FORMAT,
		INCORRECT_SEQ,
		INVALID_FORMAT,
		CODE_STD_AND_INVALID_CODE,
		DataSource //ignore this sheet
	}

	//--------------------ENUMS---------------------
	
	//csv constants
	public static final String RULE_TYPE_QUALITY="QUALITY";
	public static final String ERROR_RS="Error";
	public static final String INVALID_RS="Invalid";
	public static final String WARNING_RS="Warning";
	public static final String PROCESS_FLAG="Y";
	
	@SuppressWarnings("serial")
	public static final Map<String, List<RegexFormats>> PATTERN_REGEX_MAP = new HashMap<String, List<RegexFormats>>() {{
		put("Emailid", new ArrayList<RegexFormats>(){{
			add(new RegexFormats("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$","ctadmi@ct.com"));
		}});
		put("Phone", new ArrayList<RegexFormats>(){{
			add(new RegexFormats("\\((\\d{3})\\) (\\d{3})-(\\d{4})","(XXX) XXX-XXXX"));
			add(new RegexFormats("\\+(\\d{1})-(\\d{3})-(\\d{3})-(\\d{4})","+X-XXX-XXX-XXXX"));
		}});
		put("SSN", new ArrayList<RegexFormats>(){{
			add(new RegexFormats("(\\d{3})-(\\d{2})-(\\d{4})","XXX-XX-XXXX"));
			add(new RegexFormats("(\\d{4})-(\\d{3})-(\\d{2})","XXXX-XXX-XX"));
		}});
	}};
	
	//error message for custom exception classes
	public static final String INVALID_INPUT="Invalid input";
	public static final String PATTERN_TYPE_ERR="Invalid input : Pattern Type";
	public static final String PATTERN_NAME_ERR="Invalid input : Pattern Name";
	public static final String REGEX_ERR="Invalid input : Regex";
	public static final String INVALID_NUMBER_VALUE="Invalid input : Value, only interger value accepted";
	public static final String INVALID_OPERATOR = "Invalid input : Operator";
	
	//hibernate related
	public static final String RULE_STATUS="RULE_UTILITY_BATCH";
	public static final String RULE_STATUS_DETAILS="RULE_UTILITY_BATCH_DETAILS";
}
