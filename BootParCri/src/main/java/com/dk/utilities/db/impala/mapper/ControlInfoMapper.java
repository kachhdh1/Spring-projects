package com.dk.utilities.db.impala.mapper;

import org.springframework.jdbc.core.RowMapper;

import com.dk.utilities.db.impala.domain.ControlInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Map the ControlInfo SQL resultset to the ControlInfo entity object.
 */
public class ControlInfoMapper implements RowMapper<ControlInfo> {

    /**
     * Maps each resultset row to the ControlInfo entity.
     *
     * @param resultSet the ResultSet to map (pre-initialized for the current row)
     * @param rowNum    the number of the current row
     * @return the result object for the current row (may be null)
     * @throws SQLException if a SQLException is encountered getting column values (that is, there's no need to catch
     * SQLException)
     */
    @Override
    public ControlInfo mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
        ControlInfo controlInfo = new ControlInfo();

        controlInfo.setEtlStatus(resultSet.getString("status"));
        controlInfo.setDelimiter(resultSet.getString("delimiter"));
        controlInfo.setEtlBatchId(resultSet.getString("etl_batch_id"));
        controlInfo.setEtlBatchLogId(resultSet.getString("etl_batch_log_id"));
        controlInfo.setSourceSystemName(resultSet.getString("source_system_name"));
        controlInfo.setTableName(resultSet.getString("table_name"));
        controlInfo.setTruncateLoad(resultSet.getString("truncate_load_flag"));
        controlInfo.setBatchStartTs(resultSet.getTimestamp("batch_start_ts"));
        controlInfo.setBatchEndTs(resultSet.getTimestamp("batch_end_ts"));
        controlInfo.setTargetFilePath(resultSet.getString("TARGET_FILE_PATH"));
        controlInfo.setTargetFileName(resultSet.getString("TARGET_FILE_NAME"));
        controlInfo.setInsertRecordCount(resultSet.getInt("insert_record_count"));

        return controlInfo;
    }
}
