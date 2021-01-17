package com.tib.worklogger.spring;

import com.tib.worklogger.CreateWorkItem;
import com.tib.worklogger.repository.WorkItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkLoggerConfig {

    @Bean
    public CreateWorkItem createWorkItem(WorkItemRepository workItemRepository) {
        return new CreateWorkItem(workItemRepository);
    }

}
