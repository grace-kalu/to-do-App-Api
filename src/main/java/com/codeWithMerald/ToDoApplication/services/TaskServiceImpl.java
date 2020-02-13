package com.codeWithMerald.ToDoApplication.services;

import com.codeWithMerald.ToDoApplication.models.Task;
import com.codeWithMerald.ToDoApplication.repositories.TaskRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private TaskRepositories taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepositories taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task){
        Task t = null;
        try{
            t = taskRepository.save(task);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return t;
    }

    public Task viewTask(Integer id){
        Task t = null;
        try{
            t = taskRepository.findById(id).orElse(null);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return t;
    }

    public List<Task> getAllTasks(){
        List<Task> t = null;
        try{
            t = taskRepository.findAll();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return t;
    }

    public Task updateTask(Task taskToUpdate, Integer id){
        Task todo = taskRepository.findById(id).orElse(null);
        if (todo != null){
            todo.setTitle(taskToUpdate.getTitle());
            todo.setDescription(taskToUpdate.getDescription());
            Timestamp time = todo.getUpdatedAt();
            String status = taskToUpdate.getStatus();
//            if (status.equalsIgnoreCase("inProgress")){
//                todo.setStatus("inProgress");
//                todo.setUpdatedAt(time);
//            }
//            else{
//                todo.setStatus(status);
//                todo.setUpdatedAt(null);
//            }
            if (status.equalsIgnoreCase("completed")){
                todo.setStatus(status);
                todo.setCompletedAt(time);
            }
            else{
                todo.setStatus(status);
                todo.setCompletedAt(null);
            }
            return taskRepository.save(todo);
        }
        return null;
    }

    public Integer deleteTask(Integer id){
        if (taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return id;
        }
        return null;
    }
    public List<Task> ViewByStatus(String status){
        return taskRepository.findByStatus(status);
    }
}



