package com.digitalmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.digitalmarket.model.*;
import com.digitalmarket.service.*;
import com.digitalmarket.service.PasswordService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import com.digitalmarket.jwt.*;
import org.slf4j.LoggerFactory;
import javax.validation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")

public class UserController {
	
	@Autowired
	private PasswordService passwordService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private UserService userService;

	@Validated
	@PostMapping("/login")
	public ResponseEntity<String> userLogin(
			@RequestParam("emailid") @NotBlank(message = "email id cannot be blank") String username,
			@RequestParam("password") @NotBlank(message = "password cannot be blank") String password) {
		String response = userService.userLogin(username, passwordService.hashPassword(password));
		return ResponseEntity.ok(response);
	}

    @Validated
	@PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(@RequestBody UserEntity userEntity) {
		String response = userService.saveUserDetails(userEntity);
		return ResponseEntity.ok(response);
	}
	@Validated
	@PutMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody UserEntity userEntity) {
		String response = userService.updateUserDetails(userEntity);
		return ResponseEntity.ok(response);
	}
	

		

}
