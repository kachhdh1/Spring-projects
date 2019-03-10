package com.dk.job.service;

import static com.dk.job.util.RuleConfigConstants.FAILURE;
import static com.dk.job.util.RuleConfigConstants.LIST_SEPERATOR_DB;
import static com.dk.job.util.RuleConfigConstants.SUCCESS;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dk.job.bean.ResponseObj;
import com.dk.job.bean.RuleCsv;
import com.dk.job.domain.BatchRecordStatus;
import com.dk.job.domain.BatchStatus;
import com.dk.job.exceptions.InvalidInputException;
import com.dk.job.json.RuleJson;
import com.dk.job.repository.BatchRecordStatusRepository;
import com.dk.job.repository.BatchStatusRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RuleManagerService {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	BatchRecordStatusRepository batchRecordStatusRepository;

	@Autowired
	BatchStatusRepository batchStatusRepository;

	@Autowired
	UIService service;

	@Autowired
	RuleJsonBuilderService ruleJsonBuilderService;

	@Value("${user.authenticate.url}")
	private String authenticateUserUrl;
	
	private static final Logger logger = LogManager.getLogger(RuleManagerService.class);

	public void executeAndManageRules(List<RuleCsv> listFromCsv,
			String casToken) {
		logger.debug("Inside RuleManagerService:executeAndManageRules method");
		List<BatchRecordStatus> batchRecords = new ArrayList<BatchRecordStatus>();

		int success_count = 0;
		int error_count = 0;
		BatchStatus batch = new BatchStatus();
		for (RuleCsv ruleCsv : listFromCsv) {
			BatchRecordStatus batchRecord = new BatchRecordStatus();
			batchRecord.setBatch(batch);
			
			try {//copy properties
				
				BeanUtils.copyProperties(batchRecord, ruleCsv);
				
				//if attributeList property is empty
				if(StringUtils.isEmpty(ruleCsv.getAttributeList())){
					if(StringUtils.isNotEmpty(ruleCsv.getAttribute2())){
						batchRecord.setAttributeList(ruleCsv.getAttribute()+LIST_SEPERATOR_DB
								+ruleCsv.getAttribute2());
					}else{
						batchRecord.setAttributeList(ruleCsv.getAttribute());
					}
				}
				
				RuleJson buildRuleConfig = ruleJsonBuilderService.buildRuleJson(ruleCsv);
				//set auto generated rule name in the audit trail
				batchRecord.setRuleName(ruleJsonBuilderService.getAutoRuleNameIfEmpty(ruleCsv));
				// post json message
				logger.info("creating rule for "+ruleCsv);
				logger.debug("\n"+getJsonFromModel(buildRuleConfig));
				ResponseObj postJsonResponse = service.postJsonMessage(
						buildRuleConfig, casToken);
				if (SUCCESS
						.equalsIgnoreCase(postJsonResponse.getStatusCode())) {
					batchRecord.setRuleId(postJsonResponse.fetchRuleId());
					success_count++;
					logger.info("Rule created for "+ruleCsv);
				} else {
					// set other parameter to the error records
					batchRecord.setErrorMessage(postJsonResponse
							.fetchErrMessage());
					error_count++;
					logger.error("Failure in rule creation. Reason : "+postJsonResponse
							.fetchErrMessage());
				}
				batchRecord.setStatus(postJsonResponse.getStatusCode());

			} catch (IllegalAccessException | InvocationTargetException |InvalidInputException |JsonProcessingException e) {
				// set other parameter to the error records
				batchRecord.setErrorMessage(e.getMessage());
				batchRecord.setStatus(FAILURE);
				error_count++;
				logger.error("Exception occured:" + e.getMessage());
			} 
			batchRecords.add(batchRecord);
		} // for loop ends

		batch.setCreated_date(new Date());
		batch.setFailed_records(error_count);
		batch.setSuccess_records(success_count);
		batch.setStatus(batch.getFailed_records() > 0 ? FAILURE
				: SUCCESS);
		batch.setTotal_records(success_count + error_count);
		BatchStatus batchStatusPersisted = batchStatusRepository.save(batch);

		// save error records
		for (BatchRecordStatus batchRecord : batchRecords) {
			batchRecordStatusRepository.save(batchRecord);
		}
		logger.info("-------------------------------------------------------");
		logger.info("Batch id :" + batchStatusPersisted.getId()
				+ " ,successful records :" + success_count + ",total records :"
				+ (success_count + error_count));
		logger.info("-------------------------------------------------------");
	}

	/**
	 * creates readable json out of the given model.
	 * Function to be used in case of debug the input
	 * @param model
	 * @return
	 */
	private String getJsonFromModel(RuleJson model) {
		String res = "";
		try {
			res = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
					model);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return res;
	}

}
