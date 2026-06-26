package com.task.service;

import com.task.dto.AuthResponse;
import com.task.dto.LoginRequest;
import com.task.model.User;

public interface AuthService {

	AuthResponse singup(User user);

	AuthResponse singin(LoginRequest loginRequest);

}
