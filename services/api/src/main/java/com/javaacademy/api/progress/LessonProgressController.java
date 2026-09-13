package com.javaacademy.api.progress;

import com.javaacademy.api.lesson.Lesson;
import com.javaacademy.api.lesson.LessonService;
import com.javaacademy.api.user.User;
import com.javaacademy.api.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lessons")
public class LessonProgressController {

    private final LessonService lessonService;
    private final UserRepository userRepository;
    private final LessonProgressService lessonProgressService;

    public LessonProgressController(
            LessonService lessonService,
            UserRepository userRepository,
            LessonProgressService lessonProgressService
    ) {
        this.lessonService = lessonService;
        this.userRepository = userRepository;
        this.lessonProgressService = lessonProgressService;
    }

    @PostMapping("/{lessonId}/start")
    public ResponseEntity<LessonProgress> startLesson(
            @PathVariable Long lessonId
    ) {
        Lesson lesson = lessonService.getById(lessonId);
        User user = getAuthenticatedUser();

        return ResponseEntity.ok(
                lessonProgressService.startLesson(user, lesson)
        );
    }

    @PostMapping("/{lessonId}/complete")
    public ResponseEntity<LessonProgress> completeLesson(
            @PathVariable Long lessonId
    ) {
        Lesson lesson = lessonService.getById(lessonId);
        User user = getAuthenticatedUser();

        return ResponseEntity.ok(
                lessonProgressService.completeLesson(user, lesson)
        );
    }

    @PostMapping("/{lessonId}/time")
    public ResponseEntity<LessonProgress> addTimeSpent(
            @PathVariable Long lessonId,
            @RequestParam int seconds
    ) {
        Lesson lesson = lessonService.getById(lessonId);
        User user = getAuthenticatedUser();

        return ResponseEntity.ok(
                lessonProgressService.addTimeSpent(user, lesson, seconds)
        );
    }

    @GetMapping("/{lessonId}/progress")
    public ResponseEntity<LessonProgress> getProgress(
            @PathVariable Long lessonId
    ) {
        Lesson lesson = lessonService.getById(lessonId);
        User user = getAuthenticatedUser();

        return ResponseEntity.ok(
                lessonProgressService.getOrCreate(user, lesson)
        );
    }

    private User getAuthenticatedUser() {
        String email = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalStateException("Authenticated user not found")
                );
    }
}