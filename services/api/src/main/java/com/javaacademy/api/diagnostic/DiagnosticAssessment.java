package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "diagnostic_assessments")
public class DiagnosticAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "status", nullable = false, length = 30)
    private String status;

    @Column(name = "started_at", nullable = false)
    private OffsetDateTime startedAt;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    protected DiagnosticAssessment() {
    }

    public DiagnosticAssessment(
            User user,
            String status,
            OffsetDateTime startedAt
    ) {
        this.user = user;
        this.status = status;
        this.startedAt = startedAt;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
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

    public void complete(OffsetDateTime completedAt) {
        if (!"IN_PROGRESS".equals(this.status)) {
            throw new IllegalStateException(
                    "Only an in-progress assessment can be completed"
            );
        }

        this.status = "COMPLETED";
        this.completedAt = completedAt;
    }

    public void abandon() {
        if (!"IN_PROGRESS".equals(this.status)) {
            throw new IllegalStateException(
                    "Only an in-progress assessment can be abandoned"
            );
        }

        this.status = "ABANDONED";
    }
}