package com.javaacademy.api.progress;

import com.javaacademy.api.lesson.Lesson;
import com.javaacademy.api.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "lesson_progress")
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false, length = 30)
    private String status = "NOT_STARTED";

    private OffsetDateTime startedAt;

    private OffsetDateTime completedAt;

    @Column(nullable = false)
    private Integer timeSpentSeconds = 0;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    protected LessonProgress() {
    }

    public LessonProgress(User user, Lesson lesson) {
        this.user = user;
        this.lesson = lesson;
    }

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public String getStatus() {
        return status;
    }

    public OffsetDateTime getStartedAt() {
        return startedAt;
    }

    public OffsetDateTime getCompletedAt() {
        return completedAt;
    }

    public Integer getTimeSpentSeconds() {
        return timeSpentSeconds;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void markStarted() {
        if (startedAt == null) {
            startedAt = OffsetDateTime.now();
        }

        status = "IN_PROGRESS";
    }

    public void markCompleted() {
        status = "COMPLETED";

        if (startedAt == null) {
            startedAt = OffsetDateTime.now();
        }

        completedAt = OffsetDateTime.now();
    }

    public void addTimeSpent(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("Seconds cannot be negative");
        }

        timeSpentSeconds += seconds;
    }
}