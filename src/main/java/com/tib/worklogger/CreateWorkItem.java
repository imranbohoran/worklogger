package com.tib.worklogger;

import com.tib.worklogger.entity.WorkItem;
import com.tib.worklogger.model.NewWorkItem;
import com.tib.worklogger.repository.WorkItemRepository;

public class CreateWorkItem {

    private final WorkItemRepository workItemRepository;

    public CreateWorkItem(WorkItemRepository workItemRepository) {
        this.workItemRepository = workItemRepository;
    }

    public NewWorkItem execute(NewWorkItem newWorkItem) {
        WorkItem workItem = new WorkItem();
        workItem.setDescription(newWorkItem.getDescription());
        workItem.setDuration(newWorkItem.getDuration());
        workItem.setDurationUnit(newWorkItem.getDurationUnit());
        workItem.setEventDate(newWorkItem.getEventDate());

        WorkItem savedWorkItem = workItemRepository.save(workItem);

        NewWorkItem createdWorkLogItem = new NewWorkItem();
        createdWorkLogItem.setId(savedWorkItem.getId());
        createdWorkLogItem.setDescription(savedWorkItem.getDescription());
        createdWorkLogItem.setDuration(savedWorkItem.getDuration());
        createdWorkLogItem.setDurationUnit(savedWorkItem.getDurationUnit());
        createdWorkLogItem.setEventDate(savedWorkItem.getEventDate());
        createdWorkLogItem.setCreationDate(savedWorkItem.getCreatedDate());

        return createdWorkLogItem;
    }
}
