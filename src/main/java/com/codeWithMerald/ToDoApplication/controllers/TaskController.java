package com.codeWithMerald.ToDoApplication.controllers;



import com.codeWithMerald.ToDoApplication.models.Task;
import com.codeWithMerald.ToDoApplication.services.TaskService;
import com.codeWithMerald.ToDoApplication.services.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TaskController{

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public MyResponse<Task> createTask(@RequestBody Task task){
        Task newTask = taskService.createTask(task);
        HttpStatus status = HttpStatus.CREATED;
        String message = "Todo created successfully";
        if (newTask == null){
            status = HttpStatus.BAD_REQUEST;
            message = "Todo could not be created, please try again";
        }
        return new MyResponse<>(status, message, newTask);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MyResponse<Task> viewOneTask(@PathVariable Integer id){
        Task task = taskService.viewTask(id);
        HttpStatus status = HttpStatus.OK;
        String message = "Tasks has been retrieved successfully";
        if (task == null){
            status = HttpStatus.BAD_REQUEST;
            message = "This task does not exist";
        }
        return new MyResponse<>(status, message, task);
    }

    @GetMapping
    public MyResponse<List<Task>> viewAllTasks(){
        List<Task> todos = taskService.getAllTasks();
        HttpStatus status = HttpStatus.OK;
        String message = "All Todo's retrieved successfully";
        if (todos.isEmpty()){
            status = HttpStatus.BAD_REQUEST;
            message = "No task available";
        }
        return new MyResponse<>(status, message, todos);
    }

    @GetMapping
    @RequestMapping(value = "/status/{status}", method = RequestMethod.GET)
    public MyResponse<List<Task>> viewByStatus(@PathVariable String status){
        List<Task> todos = taskService.ViewByStatus(status);
        HttpStatus statusCode = HttpStatus.OK;
        String message = "All " + status + " Todo's retrieved successfully";
        if (todos.isEmpty()){
            statusCode = HttpStatus.BAD_REQUEST;
            message = status + " Todo's does not exist";
        }
        return new MyResponse<>(statusCode, message, todos);
    }

    @PatchMapping
    @RequestMapping(value = "{id}", method = RequestMethod.PATCH)
    public MyResponse<Task> updateTask(@RequestBody Task taskToUpdate, @PathVariable Integer id){
        Task todo = taskService.updateTask(taskToUpdate, id);
        HttpStatus status = HttpStatus.OK;
        String message = "Todo updated successfully";
        if (todo == null){
            status = HttpStatus.BAD_REQUEST;
            message = "Todo does not exist";
        }
        return new MyResponse<>(status, message, todo);
    }

    @DeleteMapping
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public MyResponse<Integer> deleteTask(@PathVariable Integer id){
        Integer todo = taskService.deleteTask(id);
        HttpStatus status = HttpStatus.OK;
        String message = "Todo deleted successfully";
        if (todo == null){
            status = HttpStatus.BAD_REQUEST;
            message = "Todo does not exist or was deleted";
        }

        return new MyResponse<>(status, message, todo);
    }

}

