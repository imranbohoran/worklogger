package com.tib.worklogger.repository;

import com.tib.worklogger.entity.WorkItem;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface WorkItemRepository extends PagingAndSortingRepository<WorkItem, UUID> {

}
