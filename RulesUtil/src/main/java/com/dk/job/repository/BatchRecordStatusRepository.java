package com.dk.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dk.job.domain.BatchRecordStatus;

public interface BatchRecordStatusRepository extends JpaRepository<BatchRecordStatus, Long> {
}
