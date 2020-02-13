package com.codeWithMerald.ToDoApplication.unitTest;

import com.codeWithMerald.ToDoApplication.models.Task;
import com.codeWithMerald.ToDoApplication.repositories.TaskRepositories;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private TaskRepositories taskRepository;

    @Test
    public void findByStatus() {
        testEntityManager.persist(new Task("title", "description"));
        List<Task> task = taskRepository.findByStatus("pending");
        assertEquals("pending", task.get(0).getStatus());
    }
}

