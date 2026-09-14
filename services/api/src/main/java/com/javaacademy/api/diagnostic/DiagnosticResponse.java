package com.javaacademy.api.diagnostic;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "diagnostic_responses")
public class DiagnosticResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assessment_id", nullable = false)
    private DiagnosticAssessment assessment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private DiagnosticQuestion question;

    @Column(name = "answer", nullable = false, columnDefinition = "TEXT")
    private String answer;

    @Column(name = "is_correct")
    private Boolean correct;

    @Column(name = "answered_at", nullable = false)
    private OffsetDateTime answeredAt;

    protected DiagnosticResponse() {
    }

    public DiagnosticResponse(
            DiagnosticAssessment assessment,
            DiagnosticQuestion question,
            String answer,
            Boolean correct,
            OffsetDateTime answeredAt
    ) {
        this.assessment = assessment;
        this.question = question;
        this.answer = answer;
        this.correct = correct;
        this.answeredAt = answeredAt;
    }

    public Long getId() {
        return id;
    }

    public DiagnosticAssessment getAssessment() {
        return assessment;
    }

    public DiagnosticQuestion getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public OffsetDateTime getAnsweredAt() {
        return answeredAt;
    }
}