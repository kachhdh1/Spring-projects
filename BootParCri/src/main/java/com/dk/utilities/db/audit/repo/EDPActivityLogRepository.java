package com.dk.utilities.db.audit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dk.utilities.db.audit.domain.EDPActivityLog;
import com.dk.utilities.processor.Constants;

public interface EDPActivityLogRepository extends
        JpaRepository<EDPActivityLog, Integer> {

    public static final String FETCH_FROM_SYNC_CONTROL_INFO = "select distinct  '0' EDP_ACTIVITY_LOG_ID, " +
            "source_system_name, AL.ETL_BATCH_ID, " +
            "AL.ETL_BATCH_LOGID, '' JOB_ID,'" + Constants.ACTIVITY_NAME + "' ACTIVITY_NAME, 'NOT STARTED' status, " +
            "null ERROR_DESC, null ACTIVITY_START_DATETIME, null ACTIVITY_END_DATETIME, null COMPACTION_PERIOD_START_DATE, null COMPACTION_PERIOD_END_DATE " +
            "from sync_control_info, " +
            "(select etl_batch_id ETL_BATCH_ID, etl_batch_log_id ETL_BATCH_LOGID, count(*) from sync_control_info " +
            " where ETL_BATCH_ID=?1 group by ETL_BATCH_ID, ETL_BATCH_LOGID) AL;";

    @Query(value = FETCH_FROM_SYNC_CONTROL_INFO, nativeQuery = true)
    List<EDPActivityLog> fetchActivityLogFromSyncControlInfo(final String etlBatchId);

}
