package com.javaacademy.api.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void shouldCreateUserWhenUsernameAndEmailAreAvailable() {
        when(userRepository.findByUsername("srinu")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("srinu@example.com")).thenReturn(Optional.empty());

        User savedUser = new User(
                "srinu",
                "srinu@example.com",
                "hashed-password",
                "Srinu"
        );

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(
                "srinu",
                "srinu@example.com",
                "hashed-password",
                "Srinu"
        );

        assertEquals("srinu", result.getUsername());
        assertEquals("srinu@example.com", result.getEmail());
        assertEquals("Srinu", result.getDisplayName());
    }

    @Test
    void shouldRejectDuplicateUsername() {
        User existingUser = new User(
                "srinu",
                "old@example.com",
                "hashed-password",
                "Srinu"
        );

        when(userRepository.findByUsername("srinu"))
                .thenReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(
                        "srinu",
                        "new@example.com",
                        "hashed-password",
                        "Srinu"
                )
        );

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void shouldRejectDuplicateEmail() {
        User existingUser = new User(
                "existing",
                "srinu@example.com",
                "hashed-password",
                "Existing User"
        );

        when(userRepository.findByUsername("srinu")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("srinu@example.com"))
                .thenReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(
                        "srinu",
                        "srinu@example.com",
                        "hashed-password",
                        "Srinu"
                )
        );

        assertEquals("Email already exists", exception.getMessage());
    }
}