package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiagnosticAssessmentServiceTest {

    @Mock
    private DiagnosticAssessmentRepository diagnosticAssessmentRepository;

    @Mock
    private User user;

    @InjectMocks
    private DiagnosticAssessmentService diagnosticAssessmentService;

    @Test
    void startAssessmentCreatesInProgressAssessment() {
        DiagnosticAssessment savedAssessment =
                new DiagnosticAssessment(
                        user,
                        "IN_PROGRESS",
                        OffsetDateTime.now()
                );

        when(diagnosticAssessmentRepository.save(any(DiagnosticAssessment.class)))
                .thenReturn(savedAssessment);

        DiagnosticAssessment result =
                diagnosticAssessmentService.startAssessment(user);

        assertThat(result).isSameAs(savedAssessment);
        assertThat(result.getStatus()).isEqualTo("IN_PROGRESS");

        verify(diagnosticAssessmentRepository)
                .save(any(DiagnosticAssessment.class));
    }

    @Test
    void getAssessmentsForUserReturnsUserAssessments() {
        DiagnosticAssessment firstAssessment =
                new DiagnosticAssessment(
                        user,
                        "IN_PROGRESS",
                        OffsetDateTime.now()
                );

        DiagnosticAssessment secondAssessment =
                new DiagnosticAssessment(
                        user,
                        "COMPLETED",
                        OffsetDateTime.now()
                );

        List<DiagnosticAssessment> assessments =
                List.of(firstAssessment, secondAssessment);

        when(diagnosticAssessmentRepository.findByUser(user))
                .thenReturn(assessments);

        List<DiagnosticAssessment> result =
                diagnosticAssessmentService.getAssessmentsForUser(user);

        assertThat(result)
                .containsExactly(firstAssessment, secondAssessment);

        verify(diagnosticAssessmentRepository)
                .findByUser(user);
    }

    @Test
    void getAssessmentByIdReturnsExistingAssessment() {
        Long assessmentId = 1L;

        DiagnosticAssessment existingAssessment =
                new DiagnosticAssessment(
                        user,
                        "IN_PROGRESS",
                        OffsetDateTime.now()
                );

        when(diagnosticAssessmentRepository.findById(assessmentId))
                .thenReturn(Optional.of(existingAssessment));

        DiagnosticAssessment result =
                diagnosticAssessmentService.getAssessmentById(assessmentId);

        assertThat(result).isSameAs(existingAssessment);

        verify(diagnosticAssessmentRepository)
                .findById(assessmentId);
    }

    @Test
    void completeAssessmentChangesStatusToCompleted() {
        Long assessmentId = 1L;

        DiagnosticAssessment assessment =
                new DiagnosticAssessment(
                        user,
                        "IN_PROGRESS",
                        OffsetDateTime.now()
                );

        when(diagnosticAssessmentRepository.findById(assessmentId))
                .thenReturn(Optional.of(assessment));

        when(diagnosticAssessmentRepository.save(assessment))
                .thenReturn(assessment);

        DiagnosticAssessment result =
                diagnosticAssessmentService.completeAssessment(assessmentId);

        assertThat(result).isSameAs(assessment);
        assertThat(result.getStatus()).isEqualTo("COMPLETED");
        assertThat(result.getCompletedAt()).isNotNull();

        verify(diagnosticAssessmentRepository)
                .findById(assessmentId);

        verify(diagnosticAssessmentRepository)
                .save(assessment);
    }

    @Test
    void abandonAssessmentChangesStatusToAbandoned() {
        Long assessmentId = 1L;

        DiagnosticAssessment assessment =
                new DiagnosticAssessment(
                        user,
                        "IN_PROGRESS",
                        OffsetDateTime.now()
                );

        when(diagnosticAssessmentRepository.findById(assessmentId))
                .thenReturn(Optional.of(assessment));

        when(diagnosticAssessmentRepository.save(assessment))
                .thenReturn(assessment);

        DiagnosticAssessment result =
                diagnosticAssessmentService.abandonAssessment(assessmentId);

        assertThat(result).isSameAs(assessment);
        assertThat(result.getStatus()).isEqualTo("ABANDONED");

        verify(diagnosticAssessmentRepository)
                .findById(assessmentId);

        verify(diagnosticAssessmentRepository)
                .save(assessment);
    }

    @Test
    void completeAssessmentThrowsWhenAssessmentIsAlreadyCompleted() {
        Long assessmentId = 1L;

        DiagnosticAssessment assessment =
                new DiagnosticAssessment(
                        user,
                        "COMPLETED",
                        OffsetDateTime.now()
                );

        when(diagnosticAssessmentRepository.findById(assessmentId))
                .thenReturn(Optional.of(assessment));

        assertThatThrownBy(() ->
                diagnosticAssessmentService.completeAssessment(assessmentId)
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Only an in-progress assessment can be completed");
    }

    @Test
    void abandonAssessmentThrowsWhenAssessmentIsAlreadyAbandoned() {
        Long assessmentId = 1L;

        DiagnosticAssessment assessment =
                new DiagnosticAssessment(
                        user,
                        "ABANDONED",
                        OffsetDateTime.now()
                );

        when(diagnosticAssessmentRepository.findById(assessmentId))
                .thenReturn(Optional.of(assessment));

        assertThatThrownBy(() ->
                diagnosticAssessmentService.abandonAssessment(assessmentId)
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Only an in-progress assessment can be abandoned");
    }

    @Test
    void getAssessmentByIdThrowsExceptionWhenAssessmentNotFound() {
        Long assessmentId = 999L;

        when(diagnosticAssessmentRepository.findById(assessmentId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                diagnosticAssessmentService.getAssessmentById(assessmentId)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Diagnostic assessment not found: " + assessmentId);

        verify(diagnosticAssessmentRepository)
                .findById(assessmentId);
    }
}