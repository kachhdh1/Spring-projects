package com.dk.utilities.db.audit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * The entity for storing the EDP Source system information.
 */
@Entity
@Table(name = "EDP_SOURCE_SYSTEM")
public class EDPSourceSystem {

    @Id
    @Column(name = "SOURCE_SYSTEM_NAME", unique = true)
    private String sourceSystemName;

    @Column(name = "TRIGGER_CHK_FLAG")
    private String triggerCheckFlag;

    @Column(name = "TRIGGER_CHK_TIMEOUT")
    private int triggerCheckTimeout;

    @Column(name = "TRIGGER_CHK_POOLING_INTERVAL")
    private int triggerCheckPollingInterval;

    @Column(name = "LAST_REFRESH_DATE")
    private Timestamp lastRefreshDate;
    
    @Column(name = "METADATA_REFRESH_DATE")
    private Timestamp metadataRefreshDate;

    @Column(name = "RAW_DBNAME")
    private String rawDBName;

    @Column(name = "RAW_INC_DBNAME")
    private String rawIncDBName;

    @Column(name = "MASTER_DBNAME")
    private String masterDBName;

    @Column(name = "MASTER_INC_DBNAME")
    private String masterIncDBName;

    @Column(name = "MASTER_BASE_DBNAME")
    private String masterBaseDBName;

    @Column(name = "RAW_BASE_DBNAME")
    private String rawBaseDBName;

    /**
     * default constructor
     */
    public EDPSourceSystem() {

    }


    /**
     * @return The sourcesystem name.
     */
    public String getSourceSystemName() {
        return sourceSystemName;
    }

    /**
     * Sets the source system name.
     *
     * @param sourceSystemName source system name.
     */
    public void setSourceSystemName(String sourceSystemName) {
        this.sourceSystemName = sourceSystemName;
    }

    public String getTriggerCheckFlag() {
        return triggerCheckFlag;
    }

    public void setTriggerCheckFlag(String triggerCheckFlag) {
        this.triggerCheckFlag = triggerCheckFlag;
    }

    public int getTriggerCheckTimeout() {
        return triggerCheckTimeout;
    }

    public void setTriggerCheckTimeout(int triggerCheckTimeout) {
        this.triggerCheckTimeout = triggerCheckTimeout;
    }

    public int getTriggerCheckPollingInterval() {
        return triggerCheckPollingInterval;
    }

    public void setTriggerCheckPollingInterval(int triggerCheckPollingInterval) {
        this.triggerCheckPollingInterval = triggerCheckPollingInterval;
    }

    public Timestamp getLastRefreshDate() {
        return lastRefreshDate;
    }

    public void setLastRefreshDate(Timestamp lastRefreshDate) {
        this.lastRefreshDate = lastRefreshDate;
    }
    
    public Timestamp getMetadataRefreshDate() {
		return metadataRefreshDate;
	}


	public void setMetadataRefreshDate(Timestamp metadataRefreshDate) {
		this.metadataRefreshDate = metadataRefreshDate;
	}


    public String getRawDBName() {
        return rawDBName;
    }

    public void setRawDBName(String rawDBName) {
        this.rawDBName = rawDBName;
    }


	public String getRawIncDBName() {
        return rawIncDBName;
    }

    public void setRawIncDBName(String rawIncDBName) {
        this.rawIncDBName = rawIncDBName;
    }

    public String getMasterDBName() {
        return masterDBName;
    }

    public void setMasterDBName(String masterDBName) {
        this.masterDBName = masterDBName;
    }

    public String getMasterIncDBName() {
        return masterIncDBName;
    }

    public void setMasterIncDBName(String masterIncDBName) {
        this.masterIncDBName = masterIncDBName;
    }

    public String getMasterBaseDBName() {
        return masterBaseDBName;
    }

    public void setMasterBaseDBName(String masterBaseDBName) {
        this.masterBaseDBName = masterBaseDBName;
    }

    public String getRawBaseDBName() {
        return rawBaseDBName;
    }

    public void setRawBaseDBName(String rawBaseDBName) {
        this.rawBaseDBName = rawBaseDBName;
    }

}
