package com.dk.utilities.db.audit.repo;

import java.util.List;

import com.dk.utilities.db.audit.domain.SyncControlInfo;
import com.dk.utilities.processor.Constants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SyncControlInfoRepository extends JpaRepository<SyncControlInfo, Integer> {

	public static final String BATCH_ID_STATUS = "select distinct sci.ETL_BATCH_ID EtlBatchId, IF( ISNULL(SS.etl_batch_id), " +
			"'New', 'Existing' ) STATUS, SS.EDP_ACTIVITY_LOG_ID activityLogIds " +
			"FROM sync_control_info sci " +
			"left join " +
			"(select distinct ETL_BATCH_ID, EDP_ACTIVITY_LOG_ID EDP_ACTIVITY_LOG_ID from EDP_ACTIVITY_LOG where " +
			"ACTIVITY_NAME = '"+ Constants.ACTIVITY_NAME +"') SS " +
			"on (SS.ETL_BATCH_ID=sci.ETL_BATCH_ID)";

    @Query(value = BATCH_ID_STATUS, nativeQuery = true)
	List<BatchStatus> fetchBatchByStatus();


}
