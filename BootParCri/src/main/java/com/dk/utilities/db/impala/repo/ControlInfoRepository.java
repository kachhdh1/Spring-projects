package com.dk.utilities.db.impala.repo;

import com.dk.utilities.db.impala.domain.ControlInfo;
import com.dk.utilities.db.impala.mapper.ControlInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class ControlInfoRepository {

	/**
	 * query to fetch all the records.
	 */
	private final String SQL_FIND_ALL = "select * from test_inc.control_info";

	/**
	 * query to fetch all the records based on status.
	 */
	private final String SQL_FIND_ALL_BY_STATUS = "select * from test_inc.control_info where status=? and insert_record_count !=0 order by etl_batch_id, "
			+ "etl_batch_log_id";

	/**
	 * query to fetch all the records based on status.
	 */
	private final String SQL_FIND_ALL_BY_STATUS_AND_BATCHENDTS = "select * from test_inc.control_info where status=? and "
			+ "batch_end_ts > ? order by etl_batch_id, etl_batch_log_id";

	@Autowired
	@Qualifier("impalaJdbcTemplate")
	private JdbcTemplate impalaJdbcTemplate;

	public List<ControlInfo> findAll() {
		return impalaJdbcTemplate.query(SQL_FIND_ALL, new ControlInfoMapper());
	}

	public List<ControlInfo> fetchAllByStatus(final String status) {
		return impalaJdbcTemplate.query(SQL_FIND_ALL_BY_STATUS, new Object[] { status }, new ControlInfoMapper());
	}

	public List<ControlInfo> fetchAllByStatus(final String status, final Timestamp batchEndTS) {
		System.out.println(SQL_FIND_ALL_BY_STATUS_AND_BATCHENDTS);
		System.out.println(status);
		System.out.println(batchEndTS);
		System.out.println(batchEndTS.toString());
		return impalaJdbcTemplate.query(SQL_FIND_ALL_BY_STATUS_AND_BATCHENDTS, new Object[] { status, batchEndTS },
				new ControlInfoMapper());
	}
}
