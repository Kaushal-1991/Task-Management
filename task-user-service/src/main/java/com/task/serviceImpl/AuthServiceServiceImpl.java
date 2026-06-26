package com.task.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.config.JwtPovider;
import com.task.dto.AuthResponse;
import com.task.dto.LoginRequest;
import com.task.model.User;
import com.task.reposistory.UserReposistory;
import com.task.service.AuthService;

@Service
public class AuthServiceServiceImpl implements AuthService {

	@Autowired
	private UserReposistory userReposistory;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUserServiceImpl customUserService;

	@Override
	public AuthResponse singup(User user) {
		String email = user.getEmail();
		String password = user.getPassword();
		String fullname = user.getFullname();
		String username = user.getUsername();
		String role = user.getRole();

		System.out.println(email + "" + password + " " + fullname + " " + username + " " + role);
		User isEmailExist = userReposistory.findByEmail(email);

		if (isEmailExist != null) {
			throw new UsernameNotFoundException("Email already used with another account !!!");
		}

		User createUser = new User();
		createUser.setEmail(email);
		createUser.setFullname(fullname);
		createUser.setPassword(passwordEncoder.encode(password));
		createUser.setRole(role);
		createUser.setUsername(username);

		User savedUser = userReposistory.save(createUser);
		System.out.println(savedUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = JwtPovider.genrateToken(authentication);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwtToken(token);
		authResponse.setMessage("Signup successfully !!!");
		authResponse.setStatus(true);

		return authResponse;
	}

	@Override
	public AuthResponse singin(LoginRequest loginRequest) {
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		Authentication authentication = authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = JwtPovider.genrateToken(authentication);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwtToken(token);
		authResponse.setMessage("Login successfully !!!");
		authResponse.setStatus(true);
		return authResponse;
	}

	private Authentication authenticate(String email, String password) {
		UserDetails userDeatil = customUserService.loadUserByUsername(email);
		if (userDeatil == null) {
			throw new BadCredentialsException("Invalid username and password");
		}

		if (!passwordEncoder.matches(password, userDeatil.getPassword())) {
			throw new BadCredentialsException("Invalid username and password");
		}
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
