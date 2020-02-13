package com.codeWithMerald.ToDoApplication.repositories;

import com.codeWithMerald.ToDoApplication.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepositories extends JpaRepository<Task, Integer> {
    public List<Task> findByStatus(String status);
}
