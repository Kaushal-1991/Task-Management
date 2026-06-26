package com.task.service;

import java.util.List;

import com.task.model.User;

public interface UserService {

	User getUserProfile(String jwt);
	
	List<User> getAllUser();
}
