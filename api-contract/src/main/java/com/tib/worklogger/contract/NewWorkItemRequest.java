package com.tib.worklogger.contract;

import java.time.LocalDateTime;

public class NewWorkItemRequest {
    private LocalDateTime eventDate;
    private String description;
    private int duration;
    private String durationUnit;

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

    public static class Builder {
        private final NewWorkItemRequest newWorkItemRequest;

        private Builder() {
            newWorkItemRequest = new NewWorkItemRequest();
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder addEventDate(LocalDateTime eventDate) {
            newWorkItemRequest.setEventDate(eventDate);
            return this;
        }

        public Builder addDescription(String description) {
            newWorkItemRequest.setDescription(description);
            return this;
        }

        public Builder addDuration(int duration, String durationUnit) {
            newWorkItemRequest.setDuration(duration);
            newWorkItemRequest.setDurationUnit(durationUnit);
            return this;
        }

        public NewWorkItemRequest build() {
            return newWorkItemRequest;
        }
    }
}
