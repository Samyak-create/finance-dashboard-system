package com.finance.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.backend.entities.User;
import com.finance.backend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	@PreAuthorize("hasRole('AMDIN')")
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUser();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public User updateUser(@PathVariable Integer id,@RequestBody User user) {
		return userService.updateUser(id, user);
	}
}
