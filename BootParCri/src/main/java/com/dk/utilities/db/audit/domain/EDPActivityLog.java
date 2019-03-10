package com.dk.utilities.db.audit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * The entity for storing the EDP Activity Log table.
 */
@Entity
@Table(name = "EDP_ACTIVITY_LOG")


public class EDPActivityLog {

	@Id
    @Column(name = "EDP_ACTIVITY_LOG_ID", unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Override
	public String toString() {
		return "EDPActivityLog [id=" + id + ", sourceSystemName=" + sourceSystemName + ", etlBatchId=" + etlBatchId
				+ ", etlBatchLogId=" + etlBatchLogId + ", activityName=" + activityName + ", status=" + status + "]";
	}

	@Column(name = "SOURCE_SYSTEM_NAME")
    private String sourceSystemName;

    @Column(name = "ETL_BATCH_ID")
    private String etlBatchId;

    @Column(name = "ETL_BATCH_LOGID")
    private String etlBatchLogId;

    @Column(name = "JOB_ID")
    private String jobId;

    @Column(name = "ACTIVITY_NAME")
    private String activityName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ERROR_DESC")
    private String errorDesc;

    @Column(name = "ACTIVITY_START_DATETIME")
    private Timestamp activityStartTime;

    @Column(name = "ACTIVITY_END_DATETIME")
    private Timestamp activityEndTime;

    
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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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

    public Timestamp getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Timestamp activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public Timestamp getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Timestamp activityEndTime) {
        this.activityEndTime = activityEndTime;
    }
}
