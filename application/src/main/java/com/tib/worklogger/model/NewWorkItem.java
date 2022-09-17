package com.tib.worklogger.model;

import com.tib.worklogger.entity.WorkItem;

import java.time.LocalDateTime;
import java.util.UUID;

public class NewWorkItem {
    private LocalDateTime eventDate;
    private String description;
    private int duration;
    private String durationUnit;
    private UUID id;
    private LocalDateTime creationDate;

    public static NewWorkItem fromWorkItem(WorkItem item) {
        NewWorkItem createdWorkLogItem = new NewWorkItem();
        createdWorkLogItem.setId(item.getId());
        createdWorkLogItem.setDescription(item.getDescription());
        createdWorkLogItem.setDuration(item.getDuration());
        createdWorkLogItem.setDurationUnit(item.getDurationUnit());
        createdWorkLogItem.setEventDate(item.getEventDate());
        createdWorkLogItem.setCreationDate(item.getCreatedDate());

        return createdWorkLogItem;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
