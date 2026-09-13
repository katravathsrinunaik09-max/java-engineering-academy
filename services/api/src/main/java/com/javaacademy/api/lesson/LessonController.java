package com.javaacademy.api.lesson;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/skill/{skillId}")
    public List<Lesson> getLessonsBySkill(
            @PathVariable Long skillId
    ) {
        return lessonService.getLessonsBySkill(skillId);
    }

    @GetMapping("/{id}")
    public Lesson getLessonById(
            @PathVariable Long id
    ) {
        return lessonService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lesson createLesson(
            @Valid @RequestBody CreateLessonRequest request
    ) {
        return lessonService.createLesson(
                request.skillId(),
                request.title(),
                request.description(),
                request.content(),
                request.difficulty(),
                request.estimatedMinutes(),
                request.lessonOrder()
        );
    }

    public record CreateLessonRequest(

            @NotNull(message = "Skill ID is required")
            Long skillId,

            @NotBlank(message = "Lesson title is required")
            @Size(max = 200, message = "Lesson title must be at most 200 characters")
            String title,

            @Size(max = 1000, message = "Description must be at most 1000 characters")
            String description,

            @NotBlank(message = "Lesson content is required")
            String content,

            @NotBlank(message = "Difficulty is required")
            String difficulty,

            @NotNull(message = "Estimated minutes is required")
            @Min(value = 1, message = "Estimated minutes must be at least 1")
            Integer estimatedMinutes,

            @NotNull(message = "Lesson order is required")
            @Min(value = 1, message = "Lesson order must be at least 1")
            Integer lessonOrder
    ) {
    }
}