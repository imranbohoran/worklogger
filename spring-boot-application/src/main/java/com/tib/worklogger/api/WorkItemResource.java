package com.tib.worklogger.api;

import com.tib.worklogger.CreateWorkItem;
import com.tib.worklogger.contract.NewWorkItemRequest;
import com.tib.worklogger.model.NewWorkItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class WorkItemResource {

    private final CreateWorkItem createWorkItem;
    private final String applicationDomain;

    public WorkItemResource(CreateWorkItem createWorkItem,@Value("${application.domain}") String applicationDomain) {
        this.createWorkItem = createWorkItem;
        this.applicationDomain = applicationDomain;
    }

    @PostMapping(path = "/worklog/work-item", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewWorkItem> createNewWorkItem(@RequestBody NewWorkItemRequest newWorkItemRequest) {
        NewWorkItem newWorkItem = createWorkItem.execute(newWorkItemRequest);
        return ResponseEntity.created(URI.create(applicationDomain + "/worklog/work-item/"+ newWorkItem.getId()))
            .body(newWorkItem);
    }
}
