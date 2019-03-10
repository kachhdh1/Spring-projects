package com.dk.utilities.processor;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dk.utilities.Application;
import com.dk.utilities.db.audit.domain.EDPSourceSystem;
import com.dk.utilities.db.audit.repo.EDPSourceSystemRepository;
import com.dk.utilities.processor.sync.SyncBatchIncrementalLoader;
import com.dk.utilities.processor.sync.SyncBatchInitialLoader;
import com.dk.utilities.processor.sync.SyncBatchLoader;

/**
 * This class is responsible for reading the batch information from the Impala's
 * schema specific control_info tables and updates the audit_log and
 * audit_log_details tables.
 * <p>
 * The sync would have different scenarios which are as below 1. The audit log
 * tables are empty, which means it would initial load and all the control_info
 * tables would be fetched which have been successful
 * <p>
 * 2. User might have requested to execute respective batch and in the case only
 * that respective details would be synced from the control_info, if the details
 * already exists then they would not be updated
 */
@Service
public class SyncBatchProcessor {

    @Autowired
    private EDPSourceSystemRepository edpSourceSystemRepository;
    
    @Autowired
    private ApplicationContext context;

    private String batchId = System.getProperty(Application.ETL_BATCH_ID, "");

    private String batchLogId = System.getProperty(Application.ETL_BATCH_LOG_ID, "");

    private String sourceSystemName = System.getProperty(Application.SOURCE_SYSTEM_NAME);


    /**
     *
     */
    public void process() {
        final EDPSourceSystem edpSourceSystem = edpSourceSystemRepository.findBySourceSystemName(sourceSystemName);

        SyncBatchLoader loadProcess = null;

        // This should not be null but if it is then make an audit log entry.
        if (edpSourceSystem == null) {
            //TODO Handle the logging here....
            System.out.println("The EDPSourceSystem Doesn't Exists ");
        } else {
            loadProcess = getLoadProcessInstance(edpSourceSystem);
            loadProcess.process(edpSourceSystem);
        }

       

    }

    public SyncBatchLoader getLoadProcessInstance(final EDPSourceSystem edpSourceSystem) {
        SyncBatchLoader loadProcess = null;

        if (StringUtils.isEmpty(batchId) && StringUtils.isEmpty(batchLogId)) {
            final Timestamp metadataRefreshDate = edpSourceSystem.getMetadataRefreshDate();
            if (metadataRefreshDate == null) {
                loadProcess = context.getBean(SyncBatchInitialLoader.class);
            } else {
                loadProcess = context.getBean(SyncBatchIncrementalLoader.class);
            }
        }
        return loadProcess;
    }


        /**
         * 1. Read from the Impala Control Info and populate the MySQL SyncControlInfo
         * a. if the meta refresh date is NULL ==> Read all the records from Impala ControlInfo
         * b. else if the meta refresh date is NOT NULL ==> Read incremental records from Impala ControlInfo
         * <p>
         * 2. Segregate the data based on New/Existing records
         * <p>
         * 3. Existing Records
         * a. Find out all the entries that have not been started or failed
         * Failed - Make new entry for the same
         * NotStarted - Update existing entries with the details from SyncControlInfo
         * b. No Action for Success entries
         * <p>
         * 4. New Records
         * a. Make new entry into AuditLog and AuditLogDetail tables
         * <p>
         * 5. Update the metadata refresh date and flush the MySQL SyncControlInfo
         */
/*
    public void process_working() {
        //TODO
        // handle source system name for sync control info and also refresh the control table.

        //
        final EDPSourceSystem edpSourceSystem = edpSourceSystemRepository.findBySourceSystemName(sourceSystemName);
        populateSyncControlInfo(edpSourceSystem);

        //
        List<BatchStatus> lstBatchesByStatus = syncControlInfoRepository.fetchBatchByStatus();

        //
        for (BatchStatus bs : lstBatchesByStatus) {
            out.println("Batch-Id " + bs.getEtlBatchId() + " Status " + bs.getStatus());

            if (bs.getStatus().equals("New")) {
                populateNewBatch(bs.getEtlBatchId());
            } else {
                populateExistingBatch(bs.getEtlBatchId(), bs.getActivityLogIds());
            }
        }

        //Flush the SyncControlInfo

        // Update Metadata Refresh date
        edpSourceSystem.setMetadataRefreshDate(new Timestamp(System.currentTimeMillis()));
        edpSourceSystemRepository.save(edpSourceSystem);

    }

    public void populateExistingBatch(final String etlBatchId, final String activityLogIds) {
        List<EDPActivityLogDetail> lstEDPActivityLogDetails =
                edpActivityLogDetailRepository.fetchNewExistingRecordsFromSyncControlInfo(etlBatchId, activityLogIds);
        edpActivityLogDetailRepository.saveAll(lstEDPActivityLogDetails);

    }


    public void populateNewBatch(final String etlBatchId) {

        List<EDPActivityLog> lstEDPActivityLogs = edpActivityLogRepository.fetchActivityLogFromSyncControlInfo(etlBatchId);
        edpActivityLogRepository.saveAll(lstEDPActivityLogs);

        List<EDPActivityLogDetail> lstEDPActivityLogDetails = new ArrayList<>();

        for (final EDPActivityLog activityLog : lstEDPActivityLogs) {

            out.println("activityLog.getId() " + activityLog.getId() + " activityLog.getEtlBatchId() " + activityLog.getEtlBatchId() + " activityLog.getEtlBatchLogId() " + activityLog.getEtlBatchLogId());

            List<AuditLogDetailSyncControlInfo> lstAuditLogDetailSyncControlInfo =
                    edpActivityLogDetailRepository.fetchActivityDetailsFromSyncControlInfo(Integer.toString(activityLog.getId()),
                            activityLog.getEtlBatchId(),
                            activityLog.getEtlBatchLogId());

            lstEDPActivityLogDetails.addAll(populateEDPActivityLogDetail(lstAuditLogDetailSyncControlInfo));
        }

        edpActivityLogDetailRepository.saveAll(lstEDPActivityLogDetails);
    }

    public List<EDPActivityLogDetail> populateEDPActivityLogDetail(final List<AuditLogDetailSyncControlInfo> lstAuditLogDetailSyncControlInfo) {

        List<EDPActivityLogDetail> lstEDPActivityLogDetails = new ArrayList<>();

        for (final AuditLogDetailSyncControlInfo auditLogDetailSyncControlInfo : lstAuditLogDetailSyncControlInfo) {
            EDPActivityLogDetail edpActivityLogDetail = new EDPActivityLogDetail();
            edpActivityLogDetail.setActivityLogId(Integer.parseInt(auditLogDetailSyncControlInfo.getEdpActivityLogId()));
            edpActivityLogDetail.setEntityName(auditLogDetailSyncControlInfo.getEntityName());
            edpActivityLogDetail.setEtlBatchId(auditLogDetailSyncControlInfo.getEtlBatchId());
            edpActivityLogDetail.setEtlBatchLogId(auditLogDetailSyncControlInfo.getEtlBatchLogId());
            edpActivityLogDetail.setStatus(auditLogDetailSyncControlInfo.getStatus());
            edpActivityLogDetail.setTargetFileName(auditLogDetailSyncControlInfo.getTargetFileName());
            edpActivityLogDetail.setTargetFilePath(auditLogDetailSyncControlInfo.getTargetFilePath());
            edpActivityLogDetail.setTruncateLoadFlag(auditLogDetailSyncControlInfo.getTruncateLoad());

            lstEDPActivityLogDetails.add(edpActivityLogDetail);
        }
        return lstEDPActivityLogDetails;
    }

    public void populateSyncControlInfo(final EDPSourceSystem edpSourceSystem) {

        List<ControlInfo> lstControlInfo = null;
        if (StringUtils.isEmpty(batchId) && StringUtils.isEmpty(batchLogId)) {
            // This should not be null but if it is then make an audit log entry.
            if (edpSourceSystem == null) {
                System.out.println("The EDPSourceSystem Doesn't Exists ");
            } else {
                final Timestamp loadRefreshDate = edpSourceSystem.getLastRefreshDate();
                final Timestamp metadataRefreshDate = edpSourceSystem.getMetadataRefreshDate();
                if (metadataRefreshDate == null) {
                    lstControlInfo = controlInfoRepository.fetchAllByStatus("SUCCESS");
                } else {
                    lstControlInfo = controlInfoRepository.fetchAllByStatus("SUCCESS", loadRefreshDate);
                }
                System.out.println("record size (STATUS, loadRefreshDate) " + lstControlInfo.size());
            }
        }

        List<SyncControlInfo> lstSyncControlInfo = populateSyncControlInfo(lstControlInfo);
        syncControlInfoRepository.saveAll(lstSyncControlInfo);
    }

    // populate sync_control_info table
    List<SyncControlInfo> populateSyncControlInfo(final List<ControlInfo> lstControlInfo) {
        List<SyncControlInfo> lstSyncControlInfo = new ArrayList<>();

        for (ControlInfo controlInfo : lstControlInfo) {
            SyncControlInfo syncControlInfo = new SyncControlInfo();

            syncControlInfo.setSourceSystemName(controlInfo.getSourceSystemName());
            syncControlInfo.setTableName(controlInfo.getTableName());
            syncControlInfo.setTargetFilePath(controlInfo.getTargetFilePath());
            syncControlInfo.setTargetFileName(controlInfo.getTargetFileName());
            syncControlInfo.setStatus(controlInfo.getEtlStatus());
            syncControlInfo.setInsertRecordCount(controlInfo.getInsertRecordCount());
            syncControlInfo.setTruncateLoadFlag(controlInfo.getTruncateLoad());
            syncControlInfo.setDelimiter(controlInfo.getDelimiter());
            syncControlInfo.setEtlBatchId(controlInfo.getEtlBatchId());
            syncControlInfo.setEtlBatchLogId(controlInfo.getEtlBatchLogId());
            syncControlInfo.setBatchStartTs(controlInfo.getBatchStartTs());
            syncControlInfo.setBatchEndTs(controlInfo.getBatchEndTs());

            syncControlInfo.setTableName(syncControlInfo.getTableName());
            lstSyncControlInfo.add(syncControlInfo);
        }

        return lstSyncControlInfo;
    }
*/


}
