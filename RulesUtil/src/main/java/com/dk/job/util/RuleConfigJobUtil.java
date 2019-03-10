package com.dk.job.util;


import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.crypto.Cipher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.dk.job.bean.DDEntities;
import com.dk.job.bean.DDEntityAttributes;
import com.dk.job.bean.DataDictionary;
import com.dk.job.bean.RuleCsv;
import com.dk.job.exceptions.InvalidInputException;
import com.dk.job.util.RuleConfigConstants.XLSX_SHEET_NAME;


public class RuleConfigJobUtil {
	
	private static final String ALGO = "RSA/ECB/PKCS1Padding";
	//maps to src system with entities (src_system --> entities (table-->attributes))
	public static Map<String,Map<String, Map<String, DDEntityAttributes>>> srcSystemEntitiesDDMap;
	private static final Logger logger = LogManager.getLogger(RuleConfigJobUtil.class);
	
	public static List<RuleCsv> getAllRules(String pathToFolder) throws Exception{
		return getListFromXlx(pathToFolder)
						.stream()
						.filter(rule-> RuleConfigConstants.PROCESS_FLAG.equalsIgnoreCase(rule.getProcess()))
						.map(ruleCsv->ruleCsv.cleanAndTrimInput(ruleCsv))
						.collect(Collectors.toList());
	} 
	
	public static List<RuleCsv> getListFromXlx(String pathToFile) {
		List<RuleCsv> allRules = new ArrayList<>();
		try {
			// Creating a Workbook from an Excel file (.xls or .xlsx)
			Workbook workbook = WorkbookFactory.create(new File(pathToFile));
	        for(Sheet sheet: workbook) {
	            Iterator<Row> rowIterator = sheet.rowIterator();
	            //skip the first row which is header
	            rowIterator.next();
	            try{
	            	XLSX_SHEET_NAME sheetName = XLSX_SHEET_NAME.valueOf(sheet.getSheetName());
	            	switch (sheetName) {
		            	case NULL_CHECK:
		            		allRules.addAll(getRulesForNullCheck(rowIterator));
							break;
						case DATE_FORMAT:
							allRules.addAll(getRulesForDateFormat(rowIterator));
							break;
						case CODE_STD_AND_INVALID_CODE:
							allRules.addAll(getRulesForCodeStdAndInvalidCode(rowIterator));
							break;
						case INCORRECT_SEQ:
							allRules.addAll(getRulesForIncorrectSequence(rowIterator));
							break;
						case INVALID_FORMAT:
							allRules.addAll(getRulesForInvalidFormat(rowIterator));
							break;
						case DataSource:
							break;//ignore and do not parse
						default:
							break;
	            	}
	            }catch(Exception e){
	            	workbook.close();
	            	logger.error("Invalid excel input: Sheet name");
	            }
	        }
	        workbook.close();
		} catch (EncryptedDocumentException | InvalidFormatException
				| IOException e) {
			
			logger.error("Error while reading file : "+pathToFile);
		}
        
		return allRules;
	}
	

	public static String encryptPassword(String plainPass,String key) throws Exception{
		X509EncodedKeySpec keySpec2 = new X509EncodedKeySpec(Base64.getDecoder().decode(key.getBytes()));

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec2);
		
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] encVal = c.doFinal(plainPass.getBytes());
		return new String(Base64.getEncoder().encode(encVal));
	}
	
	public static String encocdePassword(String plainPass){
		return new String(Base64.getEncoder().encode(plainPass.getBytes()));
	}
	
	public static Map<String, Map<String, DDEntityAttributes>> resolveDDAsMap(
			DataDictionary dataDictionary) {
		Map<String, Map<String, DDEntityAttributes>> entityShortKeyMap=new HashMap<>();
		List<DDEntities> ddEntities = dataDictionary.getDdEntities();
		if(ddEntities != null && !ddEntities.isEmpty()){
			for(DDEntities ddEntity:dataDictionary.getDdEntities()){
				entityShortKeyMap.put(ddEntity.getEntityName(), ddEntity.getAttributeKeyMap());
			}
		}
		return entityShortKeyMap;
	}
	
	public static void cacheDD(Map<String, DataDictionary> map){
		srcSystemEntitiesDDMap = new HashMap<>();
		for (Entry<String, DataDictionary> entry : map.entrySet()) {
			srcSystemEntitiesDDMap.put(entry.getKey(), resolveDDAsMap(entry.getValue()));
		}
	}
	
	/**
	 * Returns short key for the entity attribute, if present
	 * if not, returns empty Optional object
	 * @param entityName
	 * @param attributeName
	 * @return
	 */
	public static Optional<String> findShortKey(String sourceSystem,String entityName, String attributeName){
		logger.debug("Getting short name for Entity: "+entityName+" and Attribute: "+attributeName);
		Optional<String> shortkey = Optional.empty();
		Optional<Map<String, Map<String, DDEntityAttributes>>> ssMapOpt = 
				Optional.ofNullable(srcSystemEntitiesDDMap.get(sourceSystem));
		if(ssMapOpt.isPresent()){
			Optional<Map<String, DDEntityAttributes>> parent = Optional.ofNullable(ssMapOpt.get().get(entityName));
			if(parent.isPresent()){
				Optional<DDEntityAttributes> attribute = Optional.ofNullable(parent.get().get(attributeName));
				if(attribute.isPresent()){
					logger.debug(attribute.get());
					shortkey=Optional.ofNullable(attribute.get().getShortName());
				}else{
					logger.debug("Attribute is not present: "+attributeName);
				}
			}
		}
		return shortkey;	
	}
	
	
	/**
	 * takes message for displaying exception, if already a 
	 * message is present then displays
	 * @param e
	 * @param actualMessage
	 * @return
	 */
	public static String getMessageIfNotPresent(Exception e, String actualMessage){
		return e.getMessage()
				.contains(RuleConfigConstants.INVALID_INPUT)? e.getMessage():actualMessage;
	}
	
	/**
	 * function checks for the valid regex
	 * @param regex
	 * @throws InvalidInputException 
	 */
	public static void isValidRegexExpression(String regex) throws InvalidInputException{
		try{
			Pattern.compile(regex);
		}catch (PatternSyntaxException e) {
			throw new InvalidInputException(getMessageIfNotPresent(e,RuleConfigConstants.REGEX_ERR));
		}
	}
	
	/**
	 * function to get all attributes from date format standardization sheet
	 * @param rowIterator2
	 * @return List<RuleCsv>
	 */
	public static List<RuleCsv> getRulesForDateFormat(Iterator<Row> rowIterator){
        List<RuleCsv> ruleCsvList = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        while (rowIterator.hasNext()) {
        	Row row = rowIterator.next();
        	RuleCsv csv = new RuleCsv();
            csv.setSourceSystemCode(dataFormatter.formatCellValue(row.getCell(0)));
            csv.setPatternType(dataFormatter.formatCellValue(row.getCell(1)));
            csv.setEntity(dataFormatter.formatCellValue(row.getCell(2)));
            csv.setAttribute(dataFormatter.formatCellValue(row.getCell(3)));
            csv.setRuleName(dataFormatter.formatCellValue(row.getCell(4)));
            csv.setRuleStatus(dataFormatter.formatCellValue(row.getCell(5)));
            csv.setSourceFormat(dataFormatter.formatCellValue(row.getCell(6)));
            csv.setTargetFormat(dataFormatter.formatCellValue(row.getCell(7)));
            csv.setProcess(dataFormatter.formatCellValue(row.getCell(8)));
            ruleCsvList.add(csv);
        }
        return ruleCsvList;
	}
	
	public static List<RuleCsv> getRulesForNullCheck(Iterator<Row> rowIterator){
        List<RuleCsv> ruleCsvList = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        while (rowIterator.hasNext()) {
        	Row row = rowIterator.next();
        	RuleCsv csv = new RuleCsv();
            csv.setSourceSystemCode(dataFormatter.formatCellValue(row.getCell(0)));
            csv.setPatternType(dataFormatter.formatCellValue(row.getCell(1)));
            csv.setEntity(dataFormatter.formatCellValue(row.getCell(2)));
            csv.setAttributeList(dataFormatter.formatCellValue(row.getCell(3)));
            csv.setRuleName(dataFormatter.formatCellValue(row.getCell(4)));
            csv.setRuleStatus(dataFormatter.formatCellValue(row.getCell(5)));
            csv.setProcess(dataFormatter.formatCellValue(row.getCell(6)));
            ruleCsvList.add(csv);
        }
        return ruleCsvList;
	}
	
	public static List<RuleCsv> getRulesForInvalidFormat(Iterator<Row> rowIterator){
        List<RuleCsv> ruleCsvList = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        while (rowIterator.hasNext()) {
        	Row row = rowIterator.next();
        	RuleCsv csv = new RuleCsv();
            csv.setSourceSystemCode(dataFormatter.formatCellValue(row.getCell(0)));
            csv.setPatternType(dataFormatter.formatCellValue(row.getCell(1)));
            csv.setEntity(dataFormatter.formatCellValue(row.getCell(2)));
            csv.setAttribute(dataFormatter.formatCellValue(row.getCell(3)));
            csv.setRuleName(dataFormatter.formatCellValue(row.getCell(4)));
            csv.setRuleStatus(dataFormatter.formatCellValue(row.getCell(5)));
            csv.setPatternName(dataFormatter.formatCellValue(row.getCell(6)));
            csv.setOperator(dataFormatter.formatCellValue(row.getCell(7)));
            csv.setValue(dataFormatter.formatCellValue(row.getCell(8)));
            csv.setRegularExpression(dataFormatter.formatCellValue(row.getCell(9)));
            csv.setProcess(row.getCell(10).toString());
            ruleCsvList.add(csv);
        }
        return ruleCsvList;
	}
	
	public static List<RuleCsv> getRulesForCodeStdAndInvalidCode(Iterator<Row> rowIterator){
        List<RuleCsv> ruleCsvList = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        while (rowIterator.hasNext()) {
        	Row row = rowIterator.next();
        	RuleCsv csv = new RuleCsv();
            csv.setSourceSystemCode(dataFormatter.formatCellValue(row.getCell(0)));
            csv.setPatternType(dataFormatter.formatCellValue(row.getCell(1)));
            csv.setEntity(dataFormatter.formatCellValue(row.getCell(2)));
            csv.setAttribute(dataFormatter.formatCellValue(row.getCell(3)));
            csv.setRuleName(dataFormatter.formatCellValue(row.getCell(4)));
            csv.setRuleStatus(dataFormatter.formatCellValue(row.getCell(5)));
            csv.setLookupCategory(dataFormatter.formatCellValue(row.getCell(6)));
            csv.setLookupTable(dataFormatter.formatCellValue(row.getCell(7)));
            csv.setBeforeCode(dataFormatter.formatCellValue(row.getCell(8)));
            csv.setAfterCode(dataFormatter.formatCellValue(row.getCell(9)));
            csv.setProcess(row.getCell(10).toString());
            ruleCsvList.add(csv);
        }
        return ruleCsvList;
	}
	
	public static List<RuleCsv> getRulesForIncorrectSequence(Iterator<Row> rowIterator){
        List<RuleCsv> ruleCsvList = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        while (rowIterator.hasNext()) {
        	Row row = rowIterator.next();
        	RuleCsv csv = new RuleCsv();
            csv.setSourceSystemCode(dataFormatter.formatCellValue(row.getCell(0)));
            csv.setPatternType(dataFormatter.formatCellValue(row.getCell(1)));
            csv.setEntity(dataFormatter.formatCellValue(row.getCell(2)));
            /*csv.setEntity2(dataFormatter.formatCellValue(row.getCell(3)));
            csv.setAttribute(dataFormatter.formatCellValue(row.getCell(4)));
            csv.setAttribute2(dataFormatter.formatCellValue(row.getCell(5)));
            csv.setRuleName(dataFormatter.formatCellValue(row.getCell(6)));
            csv.setRuleStatus(dataFormatter.formatCellValue(row.getCell(7)));
            csv.setProcess(dataFormatter.formatCellValue(row.getCell(8)));*/
            
            csv.setAttribute(dataFormatter.formatCellValue(row.getCell(3)));
            csv.setAttribute2(dataFormatter.formatCellValue(row.getCell(4)));
            csv.setRuleName(dataFormatter.formatCellValue(row.getCell(5)));
            csv.setRuleStatus(dataFormatter.formatCellValue(row.getCell(6)));
            csv.setProcess(dataFormatter.formatCellValue(row.getCell(7)));
            ruleCsvList.add(csv);
        }
        return ruleCsvList;
	}

}
