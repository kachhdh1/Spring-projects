package com.dk.utilities;

import static java.lang.System.setProperty;

import java.sql.Timestamp;

//import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.dk.utilities.db.audit.domain.EDPSourceSystem;
import com.dk.utilities.db.audit.repo.EDPSourceSystemRepository;
import com.dk.utilities.processor.SyncBatchProcessor;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Application {

	public static final String SOURCE_SYSTEM_NAME = "source.system.name";
	public static final String ETL_BATCH_ID = "etl.batch.id";
	public static final String ETL_BATCH_LOG_ID = "etl.batch.log.id";
	public static final String ACTIVITY_NAME = "RAW LAYER PARQUET CONVERSION";

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		args = new String[]{"Source1","hadoop/key/path","jayspt_password","",""};
		if (args.length >= 3) {
			LOGGER.info("args length = " + args.length);

			// set the source system name as property
			setProperty(SOURCE_SYSTEM_NAME, args[0]);

			/*Configuration conf = new Configuration();
			conf.set("hadoop.security.credential.provider.path", args[1]);*/

			System.out.println("JCEKS File Path " + args[1]);
			try {
				//setProperty("jasypt.encryptor.password", new String(conf.getPassword(args[2])));
				setProperty("jasypt.encryptor.password", new String(args[2]));
			} catch (final Exception ioe) {
				LOGGER.error("Error while extracting the secret key from JECKS file", ioe);
			}
		} else {
			throw new RuntimeException("Insufficient argument specified");
		}

		if (args.length >= 4) {
			LOGGER.info("ETL_BATCH_ID = " + args[3]);
			setProperty(ETL_BATCH_ID, args[3]);
		}

		if (args.length == 5) {
			LOGGER.info("ETL_BATCH_LOG_ID = " + args[4]);
			setProperty(ETL_BATCH_LOG_ID, args[4]);
		}

		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
		SyncBatchProcessor info = ctx.getBean(SyncBatchProcessor.class);
		
		//TODO delete *********************************************************
		DataGenerator dataGenerator = ctx.getBean(DataGenerator.class);
		dataGenerator.insertEdpSourceSystemData();
		//TODO delete *********************************************************
		
		info.process();

	}
}

@Component
class DataGenerator{
	
	@Autowired
	private EDPSourceSystemRepository edpSourceSystemRepository;
	
	public void insertEdpSourceSystemData(){
		EDPSourceSystem edpSourceSystem = new EDPSourceSystem();
		edpSourceSystem.setSourceSystemName("Source1");
		edpSourceSystem.setTriggerCheckFlag("Y");
		edpSourceSystem.setTriggerCheckTimeout(200);
		edpSourceSystem.setTriggerCheckPollingInterval(5);
		edpSourceSystem.setLastRefreshDate(new Timestamp(System.currentTimeMillis()));
		edpSourceSystem.setMetadataRefreshDate(new Timestamp(System.currentTimeMillis()));
		edpSourceSystem.setRawBaseDBName("Test_Raw_DB");
		edpSourceSystem.setRawIncDBName("Test_Raw_DB_Inc");
		edpSourceSystem.setRawDBName("Test_Raw_DB_Inc_Base");
		edpSourceSystem.setMasterBaseDBName("Test_Master_DB");
		edpSourceSystem.setMasterIncDBName("Test_Master_DB_Inc");
		edpSourceSystem.setMasterDBName("Test_Master_DB_Inc_Base");
		edpSourceSystemRepository.save(edpSourceSystem);
	}
}
