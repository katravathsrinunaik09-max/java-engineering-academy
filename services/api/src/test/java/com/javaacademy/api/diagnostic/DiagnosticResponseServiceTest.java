package com.javaacademy.api.diagnostic;

import org.junit.jupiter.api.Test;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class DiagnosticResponseServiceTest {
    @Test
void submitAnswerThrowsWhenQuestionDoesNotExist() {
    DiagnosticResponseRepository responseRepository =
            mock(DiagnosticResponseRepository.class);

    DiagnosticAssessmentService assessmentService =
            mock(DiagnosticAssessmentService.class);

    DiagnosticQuestionRepository questionRepository =
            mock(DiagnosticQuestionRepository.class);

    DiagnosticAssessment assessment =
            mock(DiagnosticAssessment.class);

    when(assessmentService.getAssessmentById(1L))
            .thenReturn(assessment);

    when(questionRepository.findById(999L))
            .thenReturn(java.util.Optional.empty());

    DiagnosticResponseService service =
            new DiagnosticResponseService(
                    responseRepository,
                    assessmentService,
                    questionRepository
            );

    org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> service.submitAnswer(1L, 999L, "A")
    );

    verify(responseRepository, never())
            .save(any(DiagnosticResponse.class));
}

    @Test
void submitAnswerMarksIncorrectAnswer() {
    DiagnosticResponseRepository responseRepository =
            mock(DiagnosticResponseRepository.class);

    DiagnosticAssessmentService assessmentService =
            mock(DiagnosticAssessmentService.class);

    DiagnosticQuestionRepository questionRepository =
            mock(DiagnosticQuestionRepository.class);

    DiagnosticAssessment assessment =
            mock(DiagnosticAssessment.class);

    DiagnosticQuestion question =
            mock(DiagnosticQuestion.class);

    DiagnosticResponse savedResponse =
            mock(DiagnosticResponse.class);

    when(assessmentService.getAssessmentById(1L))
            .thenReturn(assessment);

    when(questionRepository.findById(10L))
            .thenReturn(java.util.Optional.of(question));

    when(question.getCorrectAnswer())
            .thenReturn("A");

    when(responseRepository.save(any(DiagnosticResponse.class)))
            .thenReturn(savedResponse);

    DiagnosticResponseService service =
            new DiagnosticResponseService(
                    responseRepository,
                    assessmentService,
                    questionRepository
            );

    service.submitAnswer(1L, 10L, "B");

    verify(responseRepository).save(argThat(response ->
            Boolean.FALSE.equals(response.getCorrect())
                    && response.getAnswer().equals("B")
                    && response.getAssessment() == assessment
                    && response.getQuestion() == question
                    && response.getAnsweredAt() != null
    ));
}

    @Test
    void submitAnswerMarksCorrectAnswer() {
        DiagnosticResponseRepository responseRepository =
                mock(DiagnosticResponseRepository.class);

        DiagnosticAssessmentService assessmentService =
                mock(DiagnosticAssessmentService.class);

        DiagnosticQuestionRepository questionRepository =
                mock(DiagnosticQuestionRepository.class);

        DiagnosticAssessment assessment =
                mock(DiagnosticAssessment.class);

        DiagnosticQuestion question =
                mock(DiagnosticQuestion.class);

        DiagnosticResponse savedResponse =
                mock(DiagnosticResponse.class);

        when(assessmentService.getAssessmentById(1L))
                .thenReturn(assessment);

        when(questionRepository.findById(10L))
                .thenReturn(java.util.Optional.of(question));

        when(question.getCorrectAnswer())
                .thenReturn("A");

        when(responseRepository.save(any(DiagnosticResponse.class)))
                .thenReturn(savedResponse);

        DiagnosticResponseService service =
                new DiagnosticResponseService(
                        responseRepository,
                        assessmentService,
                        questionRepository
                );

        service.submitAnswer(1L, 10L, "A");

        verify(responseRepository).save(argThat(response ->
                Boolean.TRUE.equals(response.getCorrect())
                        && response.getAnswer().equals("A")
                        && response.getAssessment() == assessment
                        && response.getQuestion() == question
                        && response.getAnsweredAt() != null
        ));
    }
}