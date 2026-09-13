package com.javaacademy.api.progress;

import com.javaacademy.api.lesson.Lesson;
import com.javaacademy.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    Optional<LessonProgress> findByUserAndLesson(User user, Lesson lesson);
}