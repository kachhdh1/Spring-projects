package com.dk.utilities.db.audit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The entity for storing the EDP Activity Log table.
 */
@Entity
@Table(name = "EDP_ACTIVITY_LOG_DETAILS")
public class EDPActivityLogDetail {
    @Id
    @Column(name = "EDP_ACTIVITY_LOG_DETAILID", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "EDP_ACTIVITY_LOG_ID")
    private int activityLogId;

    @Column(name = "ETL_BATCH_ID")
    private String etlBatchId;

    @Column(name = "ETL_BATCH_LOGID")
    private String etlBatchLogId;

    @Column(name = "JOB_ID")
    private String jobId;

    @Column(name = "ENTITY_NAME")
    private String entityName;

    @Column(name = "TRUNCATE_LOAD")
    private String truncateLoadFlag;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ERROR_DESC")
    private String errorDesc;

    @Column(name = "TARGET_FILE_PATH")
    private String targetFilePath;

    @Column(name = "TARGET_FILE_NAME")
    private String targetFileName;

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityLogId() {
        return activityLogId;
    }

    public void setActivityLogId(int activityLogId) {
        this.activityLogId = activityLogId;
    }

    public String getEtlBatchId() {
        return etlBatchId;
    }

    public void setEtlBatchId(String etlBatchId) {
        this.etlBatchId = etlBatchId;
    }

    public String getEtlBatchLogId() {
        return etlBatchLogId;
    }

    public void setEtlBatchLogId(String etlBatchLogId) {
        this.etlBatchLogId = etlBatchLogId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTruncateLoadFlag() {
        return truncateLoadFlag;
    }

    public void setTruncateLoadFlag(String truncateLoadFlag) {
        this.truncateLoadFlag = truncateLoadFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }

    public void setTargetFilePath(String targetFilePath) {
        this.targetFilePath = targetFilePath;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }
}
