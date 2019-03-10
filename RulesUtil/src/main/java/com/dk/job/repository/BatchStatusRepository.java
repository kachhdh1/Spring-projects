package com.dk.job.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dk.job.domain.BatchStatus;

public interface BatchStatusRepository extends JpaRepository<BatchStatus, Long> {
}
