package com.example.expensestracker.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    public void testGenerateToken() {
        // Arrange
        UserDetails userDetails = User.builder()
                .username("john_doe")
                .password("password123")
                .roles("USER")
                .build();

        // Act
        String token = jwtUtil.generateToken(userDetails);

        // Assert
        assertNotNull(token);
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    public void testExtractUsername() {
        // Arrange
        UserDetails userDetails = User.builder()
                .username("john_doe")
                .password("password123")
                .roles("USER")
                .build();
        String token = jwtUtil.generateToken(userDetails);

        // Act
        String username = jwtUtil.extractUsername(token);

        // Assert
        assertEquals("john_doe", username);
    }
}