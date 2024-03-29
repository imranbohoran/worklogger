package com.tib.worklogger;

import com.tib.worklogger.contract.NewWorkItemRequest;
import com.tib.worklogger.entity.WorkItem;
import com.tib.worklogger.model.NewWorkItem;
import com.tib.worklogger.repository.WorkItemRepository;

public class CreateWorkItem {

    private final WorkItemRepository workItemRepository;

    public CreateWorkItem(WorkItemRepository workItemRepository) {
        this.workItemRepository = workItemRepository;
    }

    public NewWorkItem execute(NewWorkItemRequest newWorkItem) {
        WorkItem workItem = new WorkItem();
        workItem.setDescription(newWorkItem.getDescription());
        workItem.setDuration(newWorkItem.getDuration());
        workItem.setDurationUnit(newWorkItem.getDurationUnit());
        workItem.setEventDate(newWorkItem.getEventDate());

        WorkItem savedWorkItem = workItemRepository.save(workItem);

        return NewWorkItem.fromWorkItem(savedWorkItem);
    }
}
