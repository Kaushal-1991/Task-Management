package com.task.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.config.JwtPovider;
import com.task.model.User;
import com.task.reposistory.UserReposistory;
import com.task.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserReposistory userReposistory;

	@Override
	public User getUserProfile(String jwt) {
		String email = JwtPovider.getEmailFromJwtToken(jwt);
		User user = userReposistory.findByEmail(email);
		return user;
	}

	@Override
	public List<User> getAllUser() {
		return userReposistory.findAll();
	}

}
