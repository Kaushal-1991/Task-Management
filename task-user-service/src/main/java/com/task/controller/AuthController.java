package com.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.task.config.JwtPovider;
import com.task.dto.AuthResponse;
import com.task.dto.LoginRequest;
import com.task.model.User;
import com.task.service.AuthService;


@RestController
@RequestMapping("/auths")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {

		AuthResponse authResponse = authService.singup(user);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {

		AuthResponse authResponse = authService.singin(loginRequest);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

}
