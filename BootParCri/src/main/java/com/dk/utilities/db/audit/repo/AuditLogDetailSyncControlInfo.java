package com.dk.utilities.db.audit.repo;

public interface AuditLogDetailSyncControlInfo {

    String getEdpActivityLogId();

    String getEtlBatchId();

    String getEtlBatchLogId();

    String getEntityName();

    String getTruncateLoad();

    String getStatus();

    String getTargetFilePath();

    String getTargetFileName();
}
