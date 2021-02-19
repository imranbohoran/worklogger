package com.tib.worklogger.repository;

import com.tib.worklogger.entity.WorkItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringWorkItemRepositoryTest {

    @Autowired
    WorkItemRepository workItemRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EntityManager entityManager;

    @Test
    void shouldCreateWorkItem() {
        String description = "test work item - creat work item";
        String assertionSql = "select count(*) from work_items where description = '"+ description + "'";

        WorkItem workItem = new WorkItem();
        workItem.setDescription(description);
        workItem.setEventDate(LocalDateTime.now().minusHours(1));
        workItem.setDuration(1);
        workItem.setDurationUnit(ChronoUnit.HOURS.toString());
        Integer workItemCount = jdbcTemplate.queryForObject(assertionSql, Integer.class);
        int count = (workItemCount == null) ? 0 : workItemCount;

        WorkItem newWorkItem = workItemRepository.save(workItem);
        entityManager.flush();

        Integer workItemCountAfterCreation = jdbcTemplate.queryForObject(assertionSql, Integer.class);
        assertThat(workItemCountAfterCreation).isEqualTo(count + 1);
        assertThat(newWorkItem.getId()).isNotNull();
        assertThat(newWorkItem.getDescription()).isEqualTo(workItem.getDescription());
    }

}