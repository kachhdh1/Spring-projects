package com.dk.utilities.db.audit.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The entity for storing the syncing control information.
 */
@Entity
@Table(name = "sync_control_info")
public class SyncControlInfo {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "source_system_name")
    private String sourceSystemName;

    @Column(name = "source_sub_categoryid")
    private int sourceSubCategoryId;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "target_file_path")
    private String targetFilePath;

    @Column(name = "target_file_name")
    private String targetFileName;

    @Column(name = "etl_package_name")
    private String etlPackageName;

    @Column(name = "status")
    private String status;

    @Column(name = "source_record_count")
    private int sourceRecordCount;

    @Column(name = "insert_record_count")
    private int insertRecordCount;

    @Column(name = "truncate_load_flag")
    private String truncateLoadFlag;

    @Column(name = "critical_flag")
    private String criticalFlag;

    @Column(name = "delimiter")
    private String delimiter;

    @Column(name = "etl_batch_id")
    private String etlBatchId;

    @Column(name = "etl_batch_log_id")
    private String etlBatchLogId;

    @Column(name = "batch_start_ts")
    private Timestamp batchStartTs;

    @Column(name = "batch_end_ts")
    private Timestamp batchEndTs;

    @Column(name = "extract_from_ts")
    private Timestamp extractFromTs;

    @Column(name = "extract_to_ts")
    private Timestamp extractToTs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceSystemName() {
        return sourceSystemName;
    }

    public void setSourceSystemName(String sourceSystemName) {
        this.sourceSystemName = sourceSystemName;
    }

    public int getSourceSubCategoryId() {
        return sourceSubCategoryId;
    }

    public void setSourceSubCategoryId(int sourceSubCategoryId) {
        this.sourceSubCategoryId = sourceSubCategoryId;
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

    public String getEtlPackageName() {
        return etlPackageName;
    }

    public void setEtlPackageName(String etlPackageName) {
        this.etlPackageName = etlPackageName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSourceRecordCount() {
        return sourceRecordCount;
    }

    public void setSourceRecordCount(int sourceRecordCount) {
        this.sourceRecordCount = sourceRecordCount;
    }

    public int getInsertRecordCount() {
        return insertRecordCount;
    }

    public void setInsertRecordCount(int insertRecordCount) {
        this.insertRecordCount = insertRecordCount;
    }

    public String getTruncateLoadFlag() {
        return truncateLoadFlag;
    }

    public void setTruncateLoadFlag(String truncateLoadFlag) {
        this.truncateLoadFlag = truncateLoadFlag;
    }

    public String getCriticalFlag() {
        return criticalFlag;
    }

    public void setCriticalFlag(String criticalFlag) {
        this.criticalFlag = criticalFlag;
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

    public Timestamp getExtractFromTs() {
        return extractFromTs;
    }

    public void setExtractFromTs(Timestamp extractFromTs) {
        this.extractFromTs = extractFromTs;
    }

    public Timestamp getExtractToTs() {
        return extractToTs;
    }

    public void setExtractToTs(Timestamp extractToTs) {
        this.extractToTs = extractToTs;
    }
}
