package com.task.reposistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.Task;

public interface TaskReposistroy extends JpaRepository<Task, Long>{

	List<Task> findByAssignedUserId(Long userId);
}
