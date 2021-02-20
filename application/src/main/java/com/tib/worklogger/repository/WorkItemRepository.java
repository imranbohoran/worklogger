package com.tib.worklogger.repository;

import com.tib.worklogger.entity.WorkItem;

public interface WorkItemRepository {

    WorkItem save(WorkItem workItem);
}
