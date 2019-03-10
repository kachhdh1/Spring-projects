package com.dk.job.controller;

import java.util.List;
//import java.util.stream.Stream;




import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dk.job.bean.RuleCsv;
import com.dk.job.service.UIService;
import com.dk.job.service.RuleManagerService;
import com.dk.job.util.RuleConfigJobUtil;


@Component
public class JobController {

	@Autowired
	UIService uIService;

	@Value("${input.file.path}")
	private String inputFilePath;

	@Value("${user.authenticate.url}")
	private String authenticateUserUrl;

	@Autowired
	RuleManagerService ruleManagerService;

	private static final Logger logger = LogManager.getLogger(JobController.class);
	public void run() {
		logger.info("\n\n\n\nStarting application RuleConfigUtil..");
		try {
			long t1 = System.currentTimeMillis();
			List<RuleCsv> listFromCsv = RuleConfigJobUtil
					.getAllRules(inputFilePath);
			// authenticate the user first to generate the cas token
			String casToken = uIService
					.authenticateUser(authenticateUserUrl);

			if(StringUtils.isNotEmpty(casToken)){
				
				//for multiple source system changes
				RuleConfigJobUtil.cacheDD(uIService.
						getDDForSourceSystems(casToken, listFromCsv));
				ruleManagerService.executeAndManageRules(listFromCsv, casToken);
				long t2 = System.currentTimeMillis();
				long jobDiff = t2-t1;
				logger.info("-------------------------------------------------------");
				logger.info("             Job completed in :"+jobDiff/1000+" secs");
				logger.info("-------------------------------------------------------");
			}else{
				logger.error("Error in authentication.");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
	
	public void runExcelDemo(){
		//Stream<Path> list = Files.list(Paths.get(inputFolder));
		RuleConfigJobUtil.getListFromXlx(inputFilePath);
	}
	
}
