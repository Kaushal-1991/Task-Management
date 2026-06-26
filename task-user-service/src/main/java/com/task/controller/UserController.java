package com.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.model.User;
import com.task.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> profile(@RequestHeader("Authorization") String jwt){
		User userProfile = userService.getUserProfile(jwt);
		return new ResponseEntity<>(userProfile,HttpStatus.OK);
	}
	
	@GetMapping("/all-user")
	public ResponseEntity<List<User>> allUser(){
		List<User> allUser = userService.getAllUser();
		return new ResponseEntity<>(allUser,HttpStatus.OK);
	}
}
