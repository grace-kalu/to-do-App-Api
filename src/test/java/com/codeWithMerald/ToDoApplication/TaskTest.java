package com.codeWithMerald.ToDoApplication;

import com.codeWithMerald.ToDoApplication.controllers.TaskController;
import com.codeWithMerald.ToDoApplication.models.Task;
import com.codeWithMerald.ToDoApplication.repositories.TaskRepositories;
import com.codeWithMerald.ToDoApplication.services.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepositories taskRepository;
    @Autowired
    private TaskController taskController;
    @Autowired
    private TaskService taskService;


    @Test
    public void testController(){
        assertNotNull(taskController);
        assertNotNull(taskRepository);
        assertNotNull(taskService);
    }

    private static String stringAsJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private <T> T stringFromJson(String json, Class<T> entity) {
        try {
            return new ObjectMapper().readValue(json, entity);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }


//    @Test
//    public void createTask() throws Exception {
//        Task t1 = new Task("test1", "test description1");
//        Task t2 = new Task("test2", "test description2");
//        this.mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(stringAsJson(t1))
//        ).andDo(
//                print()
//        ).andExpect(
//                status().isCreated()
//        ).andExpect(
//                content().string(containsString("test1"))
//        );
//    }

    @Test
    public void createTask() throws Exception {
        Task task1 = new Task();
        task1.setId(1);
        task1.setTitle("This is the first title");
        task1.setDescription("This is the second description");

        Task task2 = new Task();
        task2.setId(2);
        task2.setTitle("This is the second title");
        task2.setDescription("This is the second description");
        task2.setStatus("inProgress");

        String jsonString = stringAsJson(task1);
        MvcResult result = mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(200, statusCode);

        String json2String = stringAsJson(task2);
        MvcResult newResult = mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(json2String))
                .andReturn();

        int status2Code = newResult.getResponse().getStatus();
        assertEquals(200, status2Code);
    }


    @Test
    public void editTask() throws Exception {
        Task task = new Task("testTitle", "test description");

        String newJsonString = stringAsJson(task);
        MvcResult mvcResult = mockMvc.perform(patch("/todos").contentType(MediaType.APPLICATION_JSON).
                content(newJsonString)).andReturn();

        int successStatus = mvcResult.getResponse().getStatus();
        assertEquals(200, successStatus);
    }


        @Test
    void viewTaskTest() throws Exception {
        int id = 2;
        Optional<Task> task = Optional.ofNullable(tasks().get(id - 1));

        when(taskRepository.findById(id)).thenReturn(task);
        MvcResult result = mockMvc.perform(get("/todos/" + id).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(200, statusCode);

    }

        @Test
    void viewAllTasksTest() throws Exception {
        when(taskRepository.findAll()).thenReturn(tasks());
        MvcResult result = this.mockMvc.perform(get("/todos").accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        assertEquals(200, statusCode);

        String content = result.getResponse().getContentAsString();
        Task[] tasks = stringFromJson(content, Task[].class);
        assertTrue(tasks.length > 0);

    }

    private List<Task> tasks() {
        Task task1 = new Task();
        task1.setId(1);
        task1.setTitle("This is the first title");
        task1.setDescription("This is the second description");

        Task task2 = new Task();
        task2.setId(2);
        task2.setTitle("This is the second title");
        task2.setDescription("This is the second description");
        task2.setStatus("inProgress");

        Task task3 = new Task();
        task3.setId(3);
        task3.setTitle("This is title 3");
        task3.setDescription("This is the description 3");
        task3.setStatus("completed");

        return Arrays.asList(task1, task2, task3);
    }
}

