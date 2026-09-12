package com.javaacademy.api.user;

import com.javaacademy.api.user.dto.CreateUserRequest;
import com.javaacademy.api.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        User user = userService.createUser(
                request.username(),
                request.email(),
                request.password(),
                request.displayName()
        );

        return UserResponse.from(user);
    }
}