package com.dk.job.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dk.job.util.RuleConfigConstants;



@Entity
@Table(name = RuleConfigConstants.RULE_STATUS)
public class BatchStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="batch_id")
	Long id;

	private String status;

	private int total_records;

	private int failed_records;

	private int success_records;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_date;
	

	public Long getId() {
		return id;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotal_records() {
		return total_records;
	}

	public void setTotal_records(int total_records) {
		this.total_records = total_records;
	}

	public int getFailed_records() {
		return failed_records;
	}

	public void setFailed_records(int failed_records) {
		this.failed_records = failed_records;
	}

	public int getSuccess_records() {
		return success_records;
	}

	public void setSuccess_records(int success_records) {
		this.success_records = success_records;
	}

	public BatchStatus() {
	}

	
}
