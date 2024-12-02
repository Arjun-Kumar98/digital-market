package com.digitalmarket.service;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
// For dependency injection
import org.springframework.stereotype.Service; 
import com.digitalmarket.repository.*;
import com.digitalmarket.model.*;
import com.digitalmarket.jwt.*;
import com.digitalmarket.service.PasswordService;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
    private JwtUtil jwtTokenProvider;
	
	private <T> T fetchEntityById(Optional<T> optionalEntity, String entityName) {
		return optionalEntity.orElseThrow(() -> new EntityNotFoundException(entityName + "not found"));
	}
	public String saveUserDetails(UserEntity userEntity) {
	
	userEntity.setPassword(passwordService.hashPassword(userEntity.getPassword()));
		userRepository.save(userEntity);
		return "user has been created successfully";
	}


public String updateUserDetails(UserEntity userEntity) {
	UserEntity updateUser = fetchEntityById(userRepository.findById(userEntity.getUserId()),"user");
	updateUser.setUsername(userEntity.getUsername());
	updateUser.setEmailId(userEntity.getEmailId());
	updateUser.setAddress(userEntity.getAddress());
	userRepository.save(updateUser);
	
	return "User details have been udpated successfully";
}

public String userLogin(String emailId,String password) {
	Optional<UserEntity> optuser = userRepository.findByEmailId(emailId);
		if(optuser.isPresent() && passwordService.verifyPassword(password,optuser.get().getPassword())) {
		    String token = jwtTokenProvider.generateToken(optuser.get().getEmailId());

            // Return a message with the token
            return "Login successful. JWT Token: " + token;
		}else {
			return "incorrect username or password";
		}
		
	}
}
