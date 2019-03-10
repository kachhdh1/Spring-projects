package com.dk.utilities.db.impala.domain;

import java.sql.Timestamp;

/**
 * The entity for storing the control information.
 */
public class ControlInfo {

    private String sourceSystemName;

    private String tableName;

    private String targetFilePath;

    private String targetFileName;

    private String etlStatus;

    private int insertRecordCount;

    private String truncateLoad;

    private String delimiter;

    private String etlBatchId;

    private String etlBatchLogId;

    private Timestamp batchStartTs;

    private Timestamp batchEndTs;

    public String getSourceSystemName() {
        return sourceSystemName;
    }

    public void setSourceSystemName(String sourceSystemName) {
        this.sourceSystemName = sourceSystemName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    public String getEtlStatus() {
        return etlStatus;
    }

    public void setEtlStatus(String etlStatus) {
        this.etlStatus = etlStatus;
    }

    public int getInsertRecordCount() {
        return insertRecordCount;
    }

    public void setInsertRecordCount(int insertRecordCount) {
        this.insertRecordCount = insertRecordCount;
    }

    public String getTruncateLoad() {
        return truncateLoad;
    }

    public void setTruncateLoad(String truncateLoad) {
        this.truncateLoad = truncateLoad;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
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

    public Timestamp getBatchStartTs() {
        return batchStartTs;
    }

    public void setBatchStartTs(Timestamp batchStartTs) {
        this.batchStartTs = batchStartTs;
    }

    public Timestamp getBatchEndTs() {
        return batchEndTs;
    }

    public void setBatchEndTs(Timestamp batchEndTs) {
        this.batchEndTs = batchEndTs;
    }

}
