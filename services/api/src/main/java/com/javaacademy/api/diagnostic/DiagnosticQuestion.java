package com.javaacademy.api.diagnostic;

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

import java.time.OffsetDateTime;

@Entity
@Table(name = "diagnostic_questions")
public class DiagnosticQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "question_type", nullable = false, length = 30)
    private String questionType;

    @Column(name = "difficulty", nullable = false, length = 30)
    private String difficulty;

    @Column(name = "options_json", columnDefinition = "jsonb")
    private String optionsJson;

    @Column(name = "correct_answer", nullable = false, columnDefinition = "TEXT")
    private String correctAnswer;

    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected DiagnosticQuestion() {
        // Required by JPA.
    }

    public DiagnosticQuestion(
            Skill skill,
            String questionText,
            String questionType,
            String difficulty,
            String optionsJson,
            String correctAnswer,
            String explanation
    ) {
        this.skill = skill;
        this.questionText = questionText;
        this.questionType = questionType;
        this.difficulty = difficulty;
        this.optionsJson = optionsJson;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    public Long getId() {
        return id;
    }

    public Skill getSkill() {
        return skill;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getOptionsJson() {
        return optionsJson;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
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
}