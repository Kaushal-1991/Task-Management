package com.task.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.enums.TaskStatus;
import com.task.model.Task;
import com.task.reposistory.TaskReposistroy;
import com.task.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskReposistroy taskReposistroy;

	@Override
	public Task createTask(Task task, String requestRole) throws Exception{
		if(!requestRole.equalsIgnoreCase("ROLE_ADMIN")) {
			throw new Exception("Task only created by Admin");
		}
		task.setStatus(TaskStatus.PENDING);
		task.setCreatedAt(LocalDateTime.now());
		return taskReposistroy.save(task);
	}

	@Override
	public Task getTaskById(Long id) throws Exception{
		return taskReposistroy.findById(id).orElseThrow(() -> new Exception("Task not found with this id" + id));
	}

	@Override
	public List<Task> getAllTask(TaskStatus status) {
		List<Task> allTask = taskReposistroy.findAll();
		List<Task> findByStatus = allTask.stream()
				                    .filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString()))
				                    .collect(Collectors.toList());
		return findByStatus;
	}

	@Override
	public Task updateTask(Long id, Task updatetask, Long userId) throws Exception{
		Task existingTasks = getTaskById(id);
		
		if(updatetask.getTitle() != null) {
			existingTasks.setTitle(updatetask.getTitle());
		}
		
		if(updatetask.getImage() != null) {
			existingTasks.setImage(updatetask.getImage());
		}
		
		if(updatetask.getDescription() != null) {
			existingTasks.setDescription(updatetask.getDescription());
		}
		
		if(updatetask.getStatus() != null) {
			existingTasks.setStatus(updatetask.getStatus());
		}
		
		if(updatetask.getDeadLine() != null) {
			existingTasks.setDeadLine(updatetask.getDeadLine());
		}
		
		return taskReposistroy.save(existingTasks);
	}

	@Override
	public void deleteTask(Long id)  throws Exception {
		Task existingTasks = getTaskById(id);
		taskReposistroy.delete(existingTasks);
	}

	@Override
	public Task assignedToUser(Long userId,Long id) throws Exception {
		Task existingTasks = getTaskById(id);
		existingTasks.setAssignedUserId(userId);
		existingTasks.setStatus(TaskStatus.DONE);
		return taskReposistroy.save(existingTasks);
	}

	@Override
	public List<Task> assignedUsersTask(Long userId, TaskStatus status) {
		List<Task> task = taskReposistroy.findByAssignedUserId(userId);
		List<Task> assignedTask = task.stream().filter(t -> status == null || t.getStatus().name().equalsIgnoreCase(status.toString())).collect(Collectors.toList());
		return assignedTask;
	}

	@Override
	public Task completeTask(Long taskId)  throws Exception{
		Task existingTasks = getTaskById(taskId);
		existingTasks.setStatus(TaskStatus.DONE);
		return taskReposistroy.save(existingTasks);
	}

	

}
