package com.javaacademy.api.progress;

import com.javaacademy.api.lesson.Lesson;
import com.javaacademy.api.skill.Skill;
import com.javaacademy.api.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonProgressServiceTest {

    @Mock
    private LessonProgressRepository lessonProgressRepository;

    @Mock
    private User user;

    @Mock
    private Skill skill;

    @Mock
    private Lesson lesson;

    @InjectMocks
    private LessonProgressService lessonProgressService;

    @Test
    void getOrCreateReturnsExistingProgress() {
        LessonProgress existingProgress = new LessonProgress(user, lesson);

        when(lessonProgressRepository.findByUserAndLesson(user, lesson))
                .thenReturn(Optional.of(existingProgress));

        LessonProgress result =
                lessonProgressService.getOrCreate(user, lesson);

        assertThat(result).isSameAs(existingProgress);

        verify(lessonProgressRepository, never()).save(any());
    }

    @Test
    void getOrCreateCreatesProgressWhenMissing() {
        LessonProgress createdProgress = new LessonProgress(user, lesson);

        when(lessonProgressRepository.findByUserAndLesson(user, lesson))
                .thenReturn(Optional.empty());

        when(lessonProgressRepository.save(any(LessonProgress.class)))
                .thenReturn(createdProgress);

        LessonProgress result =
                lessonProgressService.getOrCreate(user, lesson);

        assertThat(result).isSameAs(createdProgress);

        verify(lessonProgressRepository).save(any(LessonProgress.class));
    }

    @Test
    void startLessonMarksProgressAsInProgress() {
        LessonProgress progress = new LessonProgress(user, lesson);

        when(lessonProgressRepository.findByUserAndLesson(user, lesson))
                .thenReturn(Optional.of(progress));

        when(lessonProgressRepository.save(progress))
                .thenReturn(progress);

        LessonProgress result =
                lessonProgressService.startLesson(user, lesson);

        assertThat(result.getStatus()).isEqualTo("IN_PROGRESS");

        verify(lessonProgressRepository).save(progress);
    }

    @Test
    void completeLessonMarksProgressAsCompleted() {
        LessonProgress progress = new LessonProgress(user, lesson);

        when(lessonProgressRepository.findByUserAndLesson(user, lesson))
                .thenReturn(Optional.of(progress));

        when(lessonProgressRepository.save(progress))
                .thenReturn(progress);

        LessonProgress result =
                lessonProgressService.completeLesson(user, lesson);

        assertThat(result.getStatus()).isEqualTo("COMPLETED");

        verify(lessonProgressRepository).save(progress);
    }

    @Test
    void addTimeSpentIncreasesTrackedTime() {
        LessonProgress progress = new LessonProgress(user, lesson);

        when(lessonProgressRepository.findByUserAndLesson(user, lesson))
                .thenReturn(Optional.of(progress));

        when(lessonProgressRepository.save(progress))
                .thenReturn(progress);

        LessonProgress result =
                lessonProgressService.addTimeSpent(user, lesson, 120);

        assertThat(result.getTimeSpentSeconds()).isEqualTo(120);

        verify(lessonProgressRepository).save(progress);
    }
}