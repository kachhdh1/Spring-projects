package com.dk.utilities.processor.sync;

import com.dk.utilities.db.audit.domain.EDPSourceSystem;
import com.dk.utilities.db.audit.repo.BatchStatus;
import com.dk.utilities.db.impala.domain.ControlInfo;

import java.util.List;

import org.springframework.stereotype.Component;

import static java.lang.System.out;

@Component
public class SyncBatchInitialLoader extends SyncBatchLoader {

    @Override
    protected List<ControlInfo> fetchControlInfo(final EDPSourceSystem edpSourceSystem) {
        return controlInfoRepository.fetchAllByStatus("SUCCESS");
    }

    @Override
    protected void populateAuditInfo(final List<BatchStatus> lstBatchesByStatus) {
        //
        for (final BatchStatus bs : lstBatchesByStatus) {
            out.println("Batch-Id " + bs.getEtlBatchId() + " Status " + bs.getStatus());

            if (bs.getStatus().equals("New")) {
                populateNewBatch(bs.getEtlBatchId());
            }
        }
    }

}
