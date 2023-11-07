package com.tib.worklogger.api;

import com.tib.worklogger.CreateWorkItem;
import com.tib.worklogger.model.NewWorkItem;
import com.tib.worklogger.contract.NewWorkItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(value = WorkItemResource.class)
@ComponentScan(basePackages = "com.tib.worklogger")
class WorkItemResourceTest {

    MockMvc mockMvc;

    @Value("${application.domain}")
    String applicationDomain;

    @BeforeEach
    void setup(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }

    @MockBean
    CreateWorkItem createWorkItem;

    @Captor
    ArgumentCaptor<NewWorkItemRequest> workItemRequestArgumentCaptor;

    @Test
    void createWorkItem() throws Exception {
        NewWorkItemRequest request = new NewWorkItemRequest();
        request.setDescription("sample work item description");
        request.setDuration(1);
        request.setDurationUnit("hours");
        request.setEventDate(LocalDateTime.now());

        String workItemRequestContent = new String(Files.readAllBytes(Paths.get(
            Objects.requireNonNull(WorkItemResource.class.getClassLoader().getResource("test-data/sample-create-workitem.json")).toURI())))
            .replace("<description>", request.getDescription())
            .replace("<duration-unit>", request.getDurationUnit())
            .replace("<event-date>", request.getEventDate().format(DateTimeFormatter.ISO_DATE_TIME));

        NewWorkItem newWorkItem = newWorkItem(request);

        when(createWorkItem.execute(workItemRequestArgumentCaptor.capture())).thenReturn(newWorkItem);

        mockMvc.perform(
            post("/worklog/work-item")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(workItemRequestContent))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", applicationDomain + "/worklog/work-item/"+ newWorkItem.getId().toString()))
            .andExpect(jsonPath("$.id").value(newWorkItem.getId().toString()))
            .andExpect(jsonPath("$.description").value(newWorkItem.getDescription()))
            .andExpect(jsonPath("$.duration").value(newWorkItem.getDuration()))
            .andExpect(jsonPath("$.durationUnit").value(newWorkItem.getDurationUnit()))
            .andExpect(jsonPath("$.eventDate").value(newWorkItem.getEventDate().format(DateTimeFormatter.ISO_DATE_TIME)))
            .andExpect(jsonPath("$.creationDate").value(newWorkItem.getCreationDate().format(DateTimeFormatter.ISO_DATE_TIME)))
            .andDo(document("create-worklog-item",
                responseFields(
                    fieldWithPath("id").description("The id of the new worklog item"),
                    fieldWithPath("description").description("The description of the work item"),
                    fieldWithPath("duration").description("The duration for the work item"),
                    fieldWithPath("durationUnit").description("The duration unit for the work item"),
                    fieldWithPath("eventDate").description("The event date for the work item"),
                    fieldWithPath("creationDate").description("The creation date for the work item"))));
    }

    private NewWorkItem newWorkItem(NewWorkItemRequest request) {
        NewWorkItem newWorkItem = new NewWorkItem();
        newWorkItem.setId(UUID.randomUUID());
        newWorkItem.setDescription(request.getDescription());
        newWorkItem.setEventDate(request.getEventDate());
        newWorkItem.setDuration(request.getDuration());
        newWorkItem.setDurationUnit(request.getDurationUnit());
        newWorkItem.setCreationDate(LocalDateTime.now());

        return newWorkItem;
    }
}
