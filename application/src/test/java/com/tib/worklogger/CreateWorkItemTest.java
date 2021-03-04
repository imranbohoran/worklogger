package com.tib.worklogger;

import com.tib.worklogger.contract.NewWorkItemRequest;
import com.tib.worklogger.entity.WorkItem;
import com.tib.worklogger.model.NewWorkItem;
import com.tib.worklogger.repository.WorkItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreateWorkItemTest {

    @Mock
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
        Mockito.when(workItemRepository.save(workItemCaptor.capture())).thenReturn(workItem);

        NewWorkItem createdWorkItem = this.createWorkItem.execute(newWorkItem);

        Assertions.assertThat(workItemCaptor.getValue().getDescription()).isEqualTo(newWorkItem.getDescription());
        Assertions.assertThat(workItemCaptor.getValue().getDuration()).isEqualTo(newWorkItem.getDuration());
        Assertions.assertThat(workItemCaptor.getValue().getDurationUnit()).isEqualTo(newWorkItem.getDurationUnit());
        Assertions.assertThat(workItemCaptor.getValue().getEventDate()).isEqualTo(newWorkItem.getEventDate());
        Assertions.assertThat(createdWorkItem.getDescription()).isEqualTo(workItem.getDescription());
        Assertions.assertThat(createdWorkItem.getDuration()).isEqualTo(workItem.getDuration());
        Assertions.assertThat(createdWorkItem.getDurationUnit()).isEqualTo(workItem.getDurationUnit());
        Assertions.assertThat(createdWorkItem.getEventDate()).isEqualTo(workItem.getEventDate());
        Assertions.assertThat(createdWorkItem.getCreationDate()).isEqualTo(workItem.getCreatedDate());
        Assertions.assertThat(createdWorkItem.getId()).isEqualTo(workItem.getId());
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
