package com.dk.utilities.db.audit.repo;

public interface BatchStatus {

    String getEtlBatchId();

    String getStatus();

    String getActivityLogIds();
}
