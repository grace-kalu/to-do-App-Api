package com.codeWithMerald.ToDoApplication.services;

import com.codeWithMerald.ToDoApplication.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TaskService {

    Task createTask(Task task);
    Task viewTask (Integer id);
    List<Task> getAllTasks();
    Task updateTask(Task taskToUpdate, Integer id);
    Integer deleteTask(Integer id);
    List<Task> ViewByStatus(String status);
}
