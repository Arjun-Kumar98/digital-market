package com.digitalmarket.test;



import com.digitalmarket.jwt.JwtUtil;
import com.digitalmarket.model.UserEntity;
import com.digitalmarket.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.digitalmarket.service.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordService passwordService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    UserServiceTest() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testSaveUserDetails() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(1);
        userEntity.setUsername("TestUser");
        userEntity.setPassword("raw_password");

        when(passwordService.hashPassword("raw_password")).thenReturn("hashed_password");

        // Act
        String response = userService.saveUserDetails(userEntity);

        // Assert
        assertEquals("user has been created successfully", response);
        verify(passwordService, times(1)).hashPassword("raw_password");
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testUpdateUserDetails() {
        // Arrange
        UserEntity existingUser = new UserEntity();
        existingUser.setUserId(1);
        existingUser.setUsername("OldName");

        UserEntity updatedUser = new UserEntity();
        updatedUser.setUserId(1);
        updatedUser.setUsername("NewName");
        updatedUser.setEmailId("new@example.com");
        updatedUser.setAddress("New Address");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        // Act
        String response = userService.updateUserDetails(updatedUser);

        // Assert
        assertEquals("User details have been udpated successfully", response);
        verify(userRepository, times(1)).save(existingUser);
        assertEquals("NewName", existingUser.getUsername());
        assertEquals("new@example.com", existingUser.getEmailId());
        assertEquals("New Address", existingUser.getAddress());
    }

    @Test
    void testUpdateUserDetailsThrowsException() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(99);

        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                userService.updateUserDetails(userEntity));
        assertEquals("usernot found", exception.getMessage());
    }

    @Test
    void testUserLoginSuccess() {
        // Arrange
        String email = "user@example.com";
        String rawPassword = "password";
        String hashedPassword = "hashed_password";
        String token = "jwt_token";

        UserEntity userEntity = new UserEntity();
        userEntity.setEmailId(email);
        userEntity.setPassword(hashedPassword);

        when(userRepository.findByEmailId(email)).thenReturn(Optional.of(userEntity));
        when(passwordService.verifyPassword(rawPassword, hashedPassword)).thenReturn(true);
        when(jwtUtil.generateToken(email)).thenReturn(token);

        // Act
        String response = userService.userLogin(email, rawPassword);

        // Assert
        assertEquals("Login successful. JWT Token: jwt_token", response);
    }

    @Test
    void testUserLoginFailure() {
        // Arrange
        String email = "user@example.com";
        String rawPassword = "password";

        when(userRepository.findByEmailId(email)).thenReturn(Optional.empty());

        // Act
        String response = userService.userLogin(email, rawPassword);

        // Assert
        assertEquals("incorrect username or password", response);
    }
}
