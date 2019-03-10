package com.dk.utilities.db.audit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dk.utilities.db.audit.domain.EDPSourceSystem;

public interface EDPSourceSystemRepository extends JpaRepository<EDPSourceSystem, String> {

	EDPSourceSystem findBySourceSystemName(final String sourceSystemName);

}
