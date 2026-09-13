package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnosticAssessmentRepository
        extends JpaRepository<DiagnosticAssessment, Long> {

    List<DiagnosticAssessment> findByUser(User user);

    List<DiagnosticAssessment> findByStatus(String status);
}