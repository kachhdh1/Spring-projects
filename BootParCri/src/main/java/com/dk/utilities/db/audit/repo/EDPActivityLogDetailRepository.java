package com.dk.utilities.db.audit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dk.utilities.db.audit.domain.EDPActivityLogDetail;

import java.util.List;

public interface EDPActivityLogDetailRepository extends JpaRepository<EDPActivityLogDetail, Integer> {

    public static final String EXISTING_RECORD_QUERY = "select distinct IF( eald.status='FAILED', '0', eald" +
            ".EDP_ACTIVITY_LOG_DETAILID ) EDP_ACTIVITY_LOG_DETAILID, " +
            "eald.EDP_ACTIVITY_LOG_ID, eald.ETL_BATCH_ID, eald.ETL_BATCH_LOGID, eald.JOB_ID, eald.ENTITY_NAME, sci" +
            ".truncate_load_flag TRUNCATE_LOAD, IF( eald.status='FAILED', 'NOT STARTED', eald.status ) status, eald" +
            ".ERROR_DESC, sci.target_file_path, sci.target_file_name from " +
            "sync_control_info sci join (select * from EDP_ACTIVITY_LOG_DETAILS where ETL_BATCH_ID = ?1 and status " +
            "in ('NOT STARTED', 'FAILED') " +
            "and ENTITY_NAME not in (select ENTITY_NAME from EDP_ACTIVITY_LOG_DETAILS where status = 'SUCCESS' and " +
            "ETL_BATCH_ID = ?1 and EDP_ACTIVITY_LOG_ID in (?2))) eald on (sci.ETL_BATCH_ID=eald.ETL_BATCH_ID and " +
            "sci.table_name=eald.entity_name)";

    public static final String FETCH_ACT_LOG_FROM_SYNC_CONTROL_INFO = "select distinct" +
            " ?1  EdpActivityLogId, etl_batch_id EtlBatchId," +
            " etl_batch_log_id EtlBatchLogId, table_name EntityName, truncate_load_flag TruncateLoad, 'NOT " +
            "STARTED' status, target_file_path TargetFilePath, target_file_name TargetFileName from sync_control_info" +
            "  where etl_batch_id=?2 and etl_batch_log_id=?3";

    @Query(value = EXISTING_RECORD_QUERY, nativeQuery = true)
    List<EDPActivityLogDetail> fetchNewExistingRecordsFromSyncControlInfo(final String etlBatchId, final String activityLogId);

    @Query(value = FETCH_ACT_LOG_FROM_SYNC_CONTROL_INFO, nativeQuery = true)
    List<AuditLogDetailSyncControlInfo> fetchActivityDetailsFromSyncControlInfo(final String activityLogId, final String etlBatchId,
                                                                                final String etlBatchLogId);

}
