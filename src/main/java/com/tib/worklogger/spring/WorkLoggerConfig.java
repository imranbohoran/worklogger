package com.tib.worklogger.spring;

import com.tib.worklogger.CreateWorkItem;
import com.tib.worklogger.repository.WorkItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WorkLoggerConfig {

    @Bean
    public CreateWorkItem createWorkItem(WorkItemRepository workItemRepository) {
        return new CreateWorkItem(workItemRepository);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST");
            }
        };
    }
}
