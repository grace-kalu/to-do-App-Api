package com.codeWithMerald.ToDoApplication.unitTest;

import com.codeWithMerald.ToDoApplication.models.Task;
import com.codeWithMerald.ToDoApplication.repositories.TaskRepositories;
import com.codeWithMerald.ToDoApplication.services.TaskService;
import com.codeWithMerald.ToDoApplication.services.TaskServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    private static Task task1;
    private static Task task2;
    private static Task task50;

    @Mock
    private TaskRepositories taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {
        task1 = new Task("title1", "desc1");
        task2 = new Task("title2", "desc2");
        task50 = new Task("title50", "desc50");
        task1.setId(1);
        task2.setId(2);
        task50.setId(50);
    }

    @Test
    public void it_should_return_when_a_task_is_created(){
        Mockito.when(taskRepository.save(task1)).thenReturn(task1);
        assertThat(taskService.createTask(task1), is(task1));
        Mockito.verify(taskRepository, Mockito.times(1)).save(task1);

        Mockito.when(taskRepository.save(task2)).thenReturn(task2);
        assertThat(taskService.createTask(task2), is(task2));
        Mockito.verify(taskRepository, Mockito.times(1)).save(task2);

        Mockito.when(taskRepository.save(task50)).thenReturn(task50);
        assertThat(taskService.createTask(task50), is(task50));
        Mockito.verify(taskRepository, Mockito.times(1)).save(task50);
    }

    @Test
    public void a_task_should_return_when_it_is_viewed(){
        Mockito.when(taskRepository.findById(task1.getId())).thenReturn(Optional.of(task1));
        assertThat(taskService.viewTask(task1.getId()), is(task1));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(task1.getId());

//        Mockito.when(taskRepository.findById(task2.getId())).thenReturn(Optional.of(task2));
//        Mockito.when(taskRepository.save(task2)).thenReturn(task2);
//        assertThat(taskService.viewTask(task2.getId()), is(task2));
//        Mockito.verify(taskRepository, Mockito.times(1)).findById(task2.getId());
//        Mockito.verify(taskRepository, Mockito.times(1)).save(task2);
//
//        Mockito.when(taskRepository.findById(task50.getId())).thenReturn(Optional.of(task50));
//        Mockito.when(taskRepository.save(task50)).thenReturn(task50);
//        assertThat(taskService.viewTask(task50.getId()), is(task50));
//        Mockito.verify(taskRepository, Mockito.times(1)).findById(task50.getId());
//        Mockito.verify(taskRepository, Mockito.times(1)).save(task50);
    }

    @Test
    public void all_tasks_should_return_when_all_tasks_are_viewed() {
        Mockito.when(taskRepository.findAll()).thenReturn(Arrays.asList(task1,task2, task50));
        assertThat(taskService.getAllTasks().get(1), is(task2));
        Mockito.verify(taskRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void view_a_task_by_status(){
        Mockito.when(taskRepository.findByStatus("pending")).thenReturn(Arrays.asList(task1,task2,task50));
        assertThat(taskService.ViewByStatus(task50.getStatus()).size(), is(3));
        Mockito.verify(taskRepository, Mockito.times(1)).findByStatus("pending");
    }


    @Test
    public void update_a_task(){
        Task newTask = new Task("This is a new title", "this is a new description");
        Mockito.when(taskRepository.findById(task1.getId())).thenReturn(Optional.of(task1));
        Mockito.when(taskRepository.save(task1)).thenReturn(task1);
        assertThat(taskService.updateTask(newTask, task1.getId()), is(task1));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(task1.getId());
        Mockito.verify(taskRepository, Mockito.times(1)).save(task1);

        Mockito.when(taskRepository.findById(task2.getId())).thenReturn(Optional.of(task2));
        Mockito.when(taskRepository.save(task2)).thenReturn(task2);
        assertThat(taskService.updateTask(newTask, task2.getId()), is(task2));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(task2.getId());
        Mockito.verify(taskRepository, Mockito.times(1)).save(task2);

        Mockito.when(taskRepository.findById(task50.getId())).thenReturn(Optional.of(task50));
        Mockito.when(taskRepository.save(task50)).thenReturn(task50);
        assertThat(taskService.updateTask(newTask, task50.getId()), is(task50));
        Mockito.verify(taskRepository, Mockito.times(1)).findById(task50.getId());
        Mockito.verify(taskRepository, Mockito.times(1)).save(task50);
    }

    @Test
    public void delete_a_task(){
        taskService.deleteTask(task50.getId());
        Mockito.verify(taskRepository, Mockito.times(1)).existsById(task50.getId());
    }


}
