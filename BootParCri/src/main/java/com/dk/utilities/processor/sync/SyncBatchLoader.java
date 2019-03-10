package com.dk.utilities.processor.sync;

import static java.lang.System.out;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dk.utilities.db.audit.domain.EDPActivityLog;
import com.dk.utilities.db.audit.domain.EDPActivityLogDetail;
import com.dk.utilities.db.audit.domain.EDPSourceSystem;
import com.dk.utilities.db.audit.domain.SyncControlInfo;
import com.dk.utilities.db.audit.repo.AuditLogDetailSyncControlInfo;
import com.dk.utilities.db.audit.repo.BatchStatus;
import com.dk.utilities.db.audit.repo.EDPActivityLogDetailRepository;
import com.dk.utilities.db.audit.repo.EDPActivityLogRepository;
import com.dk.utilities.db.audit.repo.EDPSourceSystemRepository;
import com.dk.utilities.db.audit.repo.SyncControlInfoRepository;
import com.dk.utilities.db.impala.domain.ControlInfo;
import com.dk.utilities.db.impala.repo.ControlInfoRepository;

public abstract class SyncBatchLoader {

    @Autowired
    private SyncControlInfoRepository syncControlInfoRepository;

    @Autowired
    private EDPSourceSystemRepository edpSourceSystemRepository;

    @Autowired
    protected ControlInfoRepository controlInfoRepository;

    @Autowired
    protected EDPActivityLogRepository edpActivityLogRepository;

    @Autowired
    protected EDPActivityLogDetailRepository edpActivityLogDetailRepository;

    public void process(final EDPSourceSystem edpSourceSystem) {
        //TODO
        // handle source system name for sync control info and also refresh the control table.

        // Fetch the control information from the Impala source system
        List<ControlInfo> lstControlInfo = fetchControlInfo(edpSourceSystem);
        List<SyncControlInfo> lstSyncControlInfo = populateSyncControlInfo(lstControlInfo);
        syncControlInfoRepository.saveAll(lstSyncControlInfo);

        //
        List<BatchStatus> lstBatchesByStatus = syncControlInfoRepository.fetchBatchByStatus();

        //
        populateAuditInfo(lstBatchesByStatus);

        // Update Metadata Refresh date
        edpSourceSystem.setMetadataRefreshDate(new Timestamp(System.currentTimeMillis()));
        edpSourceSystemRepository.save(edpSourceSystem);
    }

    protected abstract void populateAuditInfo(final List<BatchStatus> lstBatchesByStatus);

    protected abstract List<ControlInfo> fetchControlInfo(final EDPSourceSystem edpSourceSystem);

    // populate sync_control_info table
    private List<SyncControlInfo> populateSyncControlInfo(final List<ControlInfo> lstControlInfo) {
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

    protected void populateExistingBatch(final String etlBatchId, final String activityLogIds) {
        List<EDPActivityLogDetail> lstEDPActivityLogDetails =
                edpActivityLogDetailRepository.fetchNewExistingRecordsFromSyncControlInfo(etlBatchId, activityLogIds);
        edpActivityLogDetailRepository.saveAll(lstEDPActivityLogDetails);
    }

    protected void populateNewBatch(final String etlBatchId) {

        List<EDPActivityLog> lstEDPActivityLogs = edpActivityLogRepository.fetchActivityLogFromSyncControlInfo(etlBatchId);
        edpActivityLogRepository.saveAll(lstEDPActivityLogs);

        List<EDPActivityLogDetail> lstEDPActivityLogDetails = new ArrayList<>();

        for (final EDPActivityLog activityLog : lstEDPActivityLogs) {

            out.println("activityLog.getId() " + activityLog.getId()
                    + " activityLog.getEtlBatchId() " + activityLog.getEtlBatchId()
                    + " activityLog.getEtlBatchLogId() " + activityLog.getEtlBatchLogId());

            List<AuditLogDetailSyncControlInfo> lstAuditLogDetailSyncControlInfo =
                    edpActivityLogDetailRepository.fetchActivityDetailsFromSyncControlInfo(
                            Integer.toString(activityLog.getId()),
                            activityLog.getEtlBatchId(),
                            activityLog.getEtlBatchLogId());

            lstEDPActivityLogDetails.addAll(populateEDPActivityLogDetail(lstAuditLogDetailSyncControlInfo));
        }

        edpActivityLogDetailRepository.saveAll(lstEDPActivityLogDetails);
    }

    protected List<EDPActivityLogDetail> populateEDPActivityLogDetail(final List<AuditLogDetailSyncControlInfo> lstAuditLogDetailSyncControlInfo) {

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
}
