package com.javaacademy.api.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserWhenUsernameAndEmailAreAvailable() {
        when(userRepository.findByUsername("srinu"))
                .thenReturn(Optional.empty());

        when(userRepository.findByEmail("srinu@example.com"))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("plain-password"))
                .thenReturn("hashed-password");

        User savedUser = new User(
                "srinu",
                "srinu@example.com",
                "hashed-password",
                "Srinu"
        );

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        User result = userService.createUser(
                "srinu",
                "srinu@example.com",
                "plain-password",
                "Srinu"
        );

        assertEquals("srinu", result.getUsername());
        assertEquals("srinu@example.com", result.getEmail());
        assertEquals("Srinu", result.getDisplayName());
        assertEquals("hashed-password", result.getPasswordHash());

        verify(passwordEncoder).encode("plain-password");
        verify(userRepository).save(any(User.class));
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
                        "plain-password",
                        "Srinu"
                )
        );

        assertEquals("Username already exists", exception.getMessage());

        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldRejectDuplicateEmail() {
        User existingUser = new User(
                "existing",
                "srinu@example.com",
                "hashed-password",
                "Existing User"
        );

        when(userRepository.findByUsername("srinu"))
                .thenReturn(Optional.empty());

        when(userRepository.findByEmail("srinu@example.com"))
                .thenReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(
                        "srinu",
                        "srinu@example.com",
                        "plain-password",
                        "Srinu"
                )
        );

        assertEquals("Email already exists", exception.getMessage());

        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any(User.class));
    }
}