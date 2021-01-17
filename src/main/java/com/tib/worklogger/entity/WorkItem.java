package com.tib.worklogger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "work_items")
public class WorkItem {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "duration_amount", nullable = false)
    private int duration;

    @Column(name = "duration_unit", nullable = false)
    private String durationUnit;

    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
