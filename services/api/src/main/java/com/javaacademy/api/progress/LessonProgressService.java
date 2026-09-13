package com.javaacademy.api.progress;

import com.javaacademy.api.lesson.Lesson;
import com.javaacademy.api.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LessonProgressService {

    private final LessonProgressRepository lessonProgressRepository;

    public LessonProgressService(LessonProgressRepository lessonProgressRepository) {
        this.lessonProgressRepository = lessonProgressRepository;
    }

    @Transactional(readOnly = true)
    public LessonProgress getOrCreate(User user, Lesson lesson) {
        return lessonProgressRepository
                .findByUserAndLesson(user, lesson)
                .orElseGet(() -> lessonProgressRepository.save(
                        new LessonProgress(user, lesson)
                ));
    }

    public LessonProgress startLesson(User user, Lesson lesson) {
        LessonProgress progress = getOrCreate(user, lesson);
        progress.markStarted();
        return lessonProgressRepository.save(progress);
    }

    public LessonProgress completeLesson(User user, Lesson lesson) {
        LessonProgress progress = getOrCreate(user, lesson);
        progress.markCompleted();
        return lessonProgressRepository.save(progress);
    }

    public LessonProgress addTimeSpent(
            User user,
            Lesson lesson,
            int seconds
    ) {
        LessonProgress progress = getOrCreate(user, lesson);
        progress.addTimeSpent(seconds);
        return lessonProgressRepository.save(progress);
    }
}