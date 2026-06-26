package com.task.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.model.User;

public interface UserReposistory extends JpaRepository<User, Long>{

	public User findByEmail(String email);
}
