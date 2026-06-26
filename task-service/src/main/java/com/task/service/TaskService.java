package com.task.service;

import java.util.List;

import com.task.dto.TaskDto;
import com.task.enums.TaskStatus;
import com.task.model.Task;

public interface TaskService {
   Task createTask(Task task,String requestRole) throws Exception;
   Task getTaskById(Long id) throws Exception;
   List<Task> getAllTask(TaskStatus status);
   Task updateTask(Long id,Task updatetask,Long userId) throws Exception;
   void deleteTask(Long id)  throws Exception;
   Task assignedToUser(Long userId,Long taskId) throws Exception;
   List<Task> assignedUsersTask(Long userId, TaskStatus status);
   Task completeTask(Long taskId) throws Exception;
}
