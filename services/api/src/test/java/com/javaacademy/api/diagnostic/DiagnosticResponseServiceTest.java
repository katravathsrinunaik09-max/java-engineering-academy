package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DiagnosticResponseServiceTest {

    private DiagnosticResponseRepository responseRepository;
    private DiagnosticAssessmentService assessmentService;
    private DiagnosticQuestionRepository questionRepository;
    private DiagnosticResponseService responseService;

    private User authenticatedUser;
    private DiagnosticAssessment assessment;
    private DiagnosticQuestion question;

    @BeforeEach
    void setUp() {
        responseRepository = mock(DiagnosticResponseRepository.class);
        assessmentService = mock(DiagnosticAssessmentService.class);
        questionRepository = mock(DiagnosticQuestionRepository.class);

        responseService = new DiagnosticResponseService(
                responseRepository,
                assessmentService,
                questionRepository
        );

        authenticatedUser = mock(User.class);
        assessment = mock(DiagnosticAssessment.class);
        question = mock(DiagnosticQuestion.class);

        when(authenticatedUser.getId())
                .thenReturn(7L);

        when(assessment.getUser())
                .thenReturn(authenticatedUser);

        when(assessmentService.getAssessmentById(1L))
                .thenReturn(assessment);

        when(questionRepository.findById(10L))
                .thenReturn(Optional.of(question));

        when(question.getCorrectAnswer())
                .thenReturn("B");
    }

    @Test
    void submitAnswerMarksCorrectAnswer() {
        DiagnosticResponse savedResponse =
                mock(DiagnosticResponse.class);

        when(responseRepository.save(any(DiagnosticResponse.class)))
                .thenReturn(savedResponse);

        when(savedResponse.getCorrect())
                .thenReturn(true);

        DiagnosticResponse result =
                responseService.submitAnswer(
                        1L,
                        10L,
                        "B",
                        authenticatedUser
                );

        assertEquals(true, result.getCorrect());

        verify(responseRepository)
                .save(any(DiagnosticResponse.class));
    }

    @Test
    void submitAnswerMarksIncorrectAnswer() {
        DiagnosticResponse savedResponse =
                mock(DiagnosticResponse.class);

        when(responseRepository.save(any(DiagnosticResponse.class)))
                .thenReturn(savedResponse);

        when(savedResponse.getCorrect())
                .thenReturn(false);

        DiagnosticResponse result =
                responseService.submitAnswer(
                        1L,
                        10L,
                        "A",
                        authenticatedUser
                );

        assertEquals(false, result.getCorrect());

        verify(responseRepository)
                .save(any(DiagnosticResponse.class));
    }

    @Test
    void submitAnswerThrowsWhenQuestionDoesNotExist() {
        when(questionRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> responseService.submitAnswer(
                        1L,
                        10L,
                        "B",
                        authenticatedUser
                )
        );

        verify(responseRepository, never())
                .save(any(DiagnosticResponse.class));
    }

    @Test
    void submitAnswerThrowsWhenAssessmentBelongsToAnotherUser() {
        User anotherUser = mock(User.class);

        when(anotherUser.getId())
                .thenReturn(99L);

        when(assessment.getUser())
                .thenReturn(anotherUser);

        assertThrows(
                IllegalArgumentException.class,
                () -> responseService.submitAnswer(
                        1L,
                        10L,
                        "B",
                        authenticatedUser
                )
        );

        verify(questionRepository, never())
                .findById(anyLong());

        verify(responseRepository, never())
                .save(any(DiagnosticResponse.class));
    }
}