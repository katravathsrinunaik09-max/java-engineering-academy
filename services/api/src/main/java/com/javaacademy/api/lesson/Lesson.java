package com.javaacademy.api.lesson;

import com.javaacademy.api.skill.Skill;
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

import java.time.Instant;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 30)
    private String difficulty;

    @Column(nullable = false)
    private Integer estimatedMinutes;

    @Column(nullable = false)
    private Integer lessonOrder;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected Lesson() {
        // Required by JPA.
    }

    public Lesson(
            Skill skill,
            String title,
            String description,
            String content,
            String difficulty,
            Integer estimatedMinutes,
            Integer lessonOrder
    ) {
        this.skill = skill;
        this.title = title;
        this.description = description;
        this.content = content;
        this.difficulty = difficulty;
        this.estimatedMinutes = estimatedMinutes;
        this.lessonOrder = lessonOrder;
    }

    public Long getId() {
        return id;
    }

    public Skill getSkill() {
        return skill;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Integer getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public Integer getLessonOrder() {
        return lessonOrder;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}