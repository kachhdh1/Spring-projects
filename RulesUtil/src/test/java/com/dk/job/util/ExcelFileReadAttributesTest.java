package com.dk.job.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Test;

import com.dk.job.bean.RuleCsv;

public class ExcelFileReadAttributesTest {
	Workbook workbook;

	@Before
	public void setup() {
		String fileName = "C:\\dharmik\\misc_data\\data_sm\\DQ_RULES_TEST.xlsx";
		try {
			workbook = WorkbookFactory.create(new File(fileName));
			
		} catch (EncryptedDocumentException | InvalidFormatException
				| IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDateFormatSheet() {
		Sheet sheetDateFormat = workbook.getSheetAt(0); //first sheet
		Iterator<Row> rowIterator = sheetDateFormat.rowIterator();
        //skip the first row which is header
        rowIterator.next();
		List<RuleCsv> rulesForDateFormat = RuleConfigJobUtil
				.getRulesForDateFormat(rowIterator);
		assertNotNull(rulesForDateFormat);
		assertEquals(1, rulesForDateFormat.size());
		RuleCsv ruleCsv = rulesForDateFormat.get(0);
		assertEquals("TEST", ruleCsv.getSourceSystemCode());
		assertEquals("DATE_FORMAT_STANDARDIZATION", ruleCsv.getPatternType());
		assertEquals("ENCOUNTER", ruleCsv.getEntity());
		assertEquals("DISCHARGE_DATE", ruleCsv.getAttribute());
		assertEquals("D1", ruleCsv.getRuleName());
		assertEquals("Warning", ruleCsv.getRuleStatus());
		assertEquals("MM/dd/yyyy", ruleCsv.getSourceFormat());
		assertEquals("yyyy-MM-dd", ruleCsv.getTargetFormat());

	}
	
	@Test
	public void testNullCheckSheet() {
		Sheet sheet = workbook.getSheetAt(1); //second sheet
		Iterator<Row> rowIterator = sheet.rowIterator();
        //skip the first row which is header
        rowIterator.next();
		List<RuleCsv> rulesForDateFormat = RuleConfigJobUtil
				.getRulesForNullCheck(rowIterator);
		assertNotNull(rulesForDateFormat);
		assertEquals(2, rulesForDateFormat.size());
		RuleCsv ruleCsv = rulesForDateFormat.get(0);
		assertEquals("TEST", ruleCsv.getSourceSystemCode());
		assertEquals("NULL_CHECK", ruleCsv.getPatternType());
		assertEquals("ENC_DIAG", ruleCsv.getEntity());
		assertEquals("ADDRESS3|ADDRESS4", ruleCsv.getAttributeList());
		assertEquals("N3", ruleCsv.getRuleName());
		assertEquals("Error", ruleCsv.getRuleStatus());
		assertEquals("Y", ruleCsv.getProcess());

	}
	
	@Test
	public void testInvalidFormat() {
		Sheet sheet = workbook.getSheetAt(2); //third sheet
		Iterator<Row> rowIterator = sheet.rowIterator();
        //skip the first row which is header
        rowIterator.next();
		List<RuleCsv> rulesForDateFormat = RuleConfigJobUtil
				.getRulesForInvalidFormat(rowIterator);
		assertNotNull(rulesForDateFormat);
		assertEquals(3, rulesForDateFormat.size());
		RuleCsv ruleCsv = rulesForDateFormat.get(2);
		assertEquals("TEST", ruleCsv.getSourceSystemCode());
		assertEquals("INVALID_FORMAT", ruleCsv.getPatternType());
		assertEquals("PATIENT", ruleCsv.getEntity());
		assertEquals("ZIP_CODE", ruleCsv.getAttribute());
		assertEquals("IF3", ruleCsv.getRuleName());
		assertEquals("Warning", ruleCsv.getRuleStatus());
		assertEquals("Length", ruleCsv.getPatternName());
		assertEquals(">", ruleCsv.getOperator());
		assertEquals("()", ruleCsv.getRegularExpression());
		assertEquals("N", ruleCsv.getProcess());

	}
	
	@Test
	public void testCodeStdAndInvalidCodeLookup() {
		Sheet sheet = workbook.getSheetAt(3); //fourth sheet
		Iterator<Row> rowIterator = sheet.rowIterator();
        //skip the first row which is header
        rowIterator.next();
		List<RuleCsv> rulesForDateFormat = RuleConfigJobUtil
				.getRulesForCodeStdAndInvalidCode(rowIterator);
		assertNotNull(rulesForDateFormat);
		assertEquals(2, rulesForDateFormat.size());
		RuleCsv ruleCsv = rulesForDateFormat.get(1);
		assertEquals("Test", ruleCsv.getSourceSystemCode());
		assertEquals("CODE_STANDARDIZATION", ruleCsv.getPatternType());
		assertEquals("PATIENT", ruleCsv.getEntity());
		assertEquals("GENDER", ruleCsv.getAttribute());
		assertEquals("pat_gender_std", ruleCsv.getRuleName());
		assertEquals("Warning", ruleCsv.getRuleStatus());
		assertEquals("gender_std", ruleCsv.getLookupCategory());
		assertEquals("LT", ruleCsv.getLookupTable());
		assertEquals("BC", ruleCsv.getBeforeCode());
		assertEquals("AC", ruleCsv.getAfterCode());
		assertEquals("N", ruleCsv.getProcess());
	}
	
	@Test
	public void testIncorrectSequence() {
		Sheet sheet = workbook.getSheetAt(4); //fourth sheet
		Iterator<Row> rowIterator = sheet.rowIterator();
        //skip the first row which is header
        rowIterator.next();
		List<RuleCsv> rulesForDateFormat = RuleConfigJobUtil
				.getRulesForIncorrectSequence(rowIterator);
		assertNotNull(rulesForDateFormat);
		assertEquals(1, rulesForDateFormat.size());
		RuleCsv ruleCsv = rulesForDateFormat.get(0);
		assertEquals("TEST", ruleCsv.getSourceSystemCode());
		assertEquals("INCORRECT_SEQUENCE", ruleCsv.getPatternType());
		assertEquals("ENCOUNTER", ruleCsv.getEntity());
		assertEquals("DISCHARGE_DATE", ruleCsv.getAttribute());
		assertEquals("DATEOFENCOUNTER", ruleCsv.getAttribute2());
		assertEquals("IS1", ruleCsv.getRuleName());
		assertEquals("Error", ruleCsv.getRuleStatus());
		assertEquals("Y", ruleCsv.getProcess());
	}

}
