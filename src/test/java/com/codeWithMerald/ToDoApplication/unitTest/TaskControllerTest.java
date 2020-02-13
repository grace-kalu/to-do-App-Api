package com.codeWithMerald.ToDoApplication.unitTest;

import com.codeWithMerald.ToDoApplication.controllers.MyResponse;
import com.codeWithMerald.ToDoApplication.controllers.TaskController;
import com.codeWithMerald.ToDoApplication.models.Task;
import com.codeWithMerald.ToDoApplication.services.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;



@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    private static Task firstTask;
    private static Task secondTask;
    private static Task thirdTask;

    @Mock

    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void init() {

        firstTask = new Task("firstTitle", "firstDesc");
        secondTask = new Task("secondTitle", "secondDesc");
        thirdTask = new Task("thirdTitle", "thirdDesc");
        firstTask.setId(1);
        secondTask.setId(2);
        thirdTask.setId(3);
    }

    private static String jsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Test
    public void createATask() throws Exception{
        MyResponse<Task> newTask = taskController.createTask(firstTask);
        Mockito.verify(taskService, Mockito.times(1)).createTask(firstTask);
    }

    @Test
    public void viewATask() throws Exception {
        Mockito.when(taskService.viewTask(firstTask.getId())).thenReturn(firstTask);
        MyResponse<Task> taskResponse = taskController.viewOneTask(firstTask.getId());
        assertThat(taskResponse.getStatus(), is(HttpStatus.OK));
        assertThat(taskResponse.getData(), is(firstTask));
    }

    @Test
    public void viewAllTask() throws Exception {
    Mockito.when(taskService.getAllTasks()).thenReturn(Arrays.asList(firstTask, secondTask, thirdTask));
    assertThat(taskController.viewAllTasks().getData().size(), is(3));
    assertThat(taskController.viewAllTasks().getStatus(), is(HttpStatus.OK));
    }


    @Test
    public void viewTaskByStatus() throws Exception {
    Mockito.when(taskService.ViewByStatus("pending")).thenReturn(Arrays.asList(firstTask, secondTask, thirdTask));
    assertThat(taskController.viewByStatus("pending").getData().size(), is(3));
    assertThat(taskController.viewByStatus("pending").getStatus(),is(HttpStatus.OK));
    }

    @Test
    public void updateATask() throws Exception {
        Task task = new Task("title", "description");
        Mockito.when(taskService.updateTask(task, secondTask.getId())).thenReturn(task);
        assertThat(taskController.updateTask(task, secondTask.getId()).getData(), is(task));
        assertThat(taskController.updateTask(task, secondTask.getId()).getStatus(), is(HttpStatus.OK));
    }

    @Test
    public void deleteATask() throws Exception {
        taskController.deleteTask(thirdTask.getId());
        Mockito.verify(taskService, Mockito.times(1)).deleteTask(thirdTask.getId());
    }
}

