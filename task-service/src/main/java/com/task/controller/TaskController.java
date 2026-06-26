package com.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.dto.UserDto;
import com.task.enums.TaskStatus;
import com.task.fienclientservice.UserService;
import com.task.model.Task;
import com.task.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody Task task,@RequestHeader("Authorization") String jwt) throws Exception{
		UserDto userDto = userService.getUserProfile(jwt);
		Task createTask = taskService.createTask(task, userDto.getRole());
		return new ResponseEntity<>(createTask,HttpStatus.CREATED);
	} 
	
	@GetMapping("/{id}")
	public ResponseEntity<Task> getTask(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception{
		UserDto userDto = userService.getUserProfile(jwt);
		Task task = taskService.getTaskById(id);
		return new ResponseEntity<>(task,HttpStatus.OK);
	} 
	
	@GetMapping
	public ResponseEntity<List<Task>> getAllTask(@RequestParam(required = false) TaskStatus status,@RequestHeader("Authorization") String jwt) throws Exception{
		UserDto userDto = userService.getUserProfile(jwt);
		List<Task> tasks = taskService.getAllTask(status);
		return new ResponseEntity<>(tasks,HttpStatus.OK);
	} 
	
	@GetMapping("/user")
	public ResponseEntity<List<Task>> getAssignedUserTask(@RequestParam(required = false) TaskStatus status,@RequestHeader("Authorization") String jwt) throws Exception{
		UserDto userDto = userService.getUserProfile(jwt);
		List<Task> tasks = taskService.assignedUsersTask(userDto.getId(),status);
		return new ResponseEntity<>(tasks,HttpStatus.OK);
	} 
	
}
