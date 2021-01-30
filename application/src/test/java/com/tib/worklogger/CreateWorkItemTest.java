package com.tib.worklogger;

import com.tib.worklogger.entity.WorkItem;
import com.tib.worklogger.model.NewWorkItem;
import com.tib.worklogger.model.NewWorkItemRequest;
import com.tib.worklogger.repository.WorkItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateWorkItemTest {

    @MockBean
    WorkItemRepository workItemRepository;

    @Captor
    ArgumentCaptor<WorkItem> workItemCaptor;

    private CreateWorkItem createWorkItem;

    @BeforeEach
    void setup() {
        this.createWorkItem = new CreateWorkItem(workItemRepository);
    }

    @Test
    void shouldUseWorkItemRepositoryToCreateWorkItem() {
        NewWorkItemRequest newWorkItem = new NewWorkItemRequest();
        newWorkItem.setDescription("test created item");
        newWorkItem.setDuration(2);
        newWorkItem.setDurationUnit(ChronoUnit.HOURS.toString());
        newWorkItem.setEventDate(LocalDateTime.now());

        WorkItem workItem = savedWorkItem(newWorkItem);
        when(workItemRepository.save(workItemCaptor.capture())).thenReturn(workItem);

        NewWorkItem createdWorkItem = this.createWorkItem.execute(newWorkItem);

        assertThat(workItemCaptor.getValue().getDescription()).isEqualTo(newWorkItem.getDescription());
        assertThat(workItemCaptor.getValue().getDuration()).isEqualTo(newWorkItem.getDuration());
        assertThat(workItemCaptor.getValue().getDurationUnit()).isEqualTo(newWorkItem.getDurationUnit());
        assertThat(workItemCaptor.getValue().getEventDate()).isEqualTo(newWorkItem.getEventDate());
        assertThat(createdWorkItem.getDescription()).isEqualTo(workItem.getDescription());
        assertThat(createdWorkItem.getDuration()).isEqualTo(workItem.getDuration());
        assertThat(createdWorkItem.getDurationUnit()).isEqualTo(workItem.getDurationUnit());
        assertThat(createdWorkItem.getEventDate()).isEqualTo(workItem.getEventDate());
        assertThat(createdWorkItem.getCreationDate()).isEqualTo(workItem.getCreatedDate());
        assertThat(createdWorkItem.getId()).isEqualTo(workItem.getId());
    }

    private WorkItem savedWorkItem(NewWorkItemRequest newWorkItem) {
        WorkItem workItem = new WorkItem();
        workItem.setId(UUID.randomUUID());
        workItem.setDescription(newWorkItem.getDescription());
        workItem.setDurationUnit(newWorkItem.getDurationUnit());
        workItem.setDuration(newWorkItem.getDuration());
        workItem.setEventDate(newWorkItem.getEventDate());
        workItem.setCreatedDate(LocalDateTime.now());

        return workItem;
    }
}