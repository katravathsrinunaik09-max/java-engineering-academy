package com.javaacademy.api.diagnostic;

import com.javaacademy.api.skill.Skill;
import jakarta.persistence.*;
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
}