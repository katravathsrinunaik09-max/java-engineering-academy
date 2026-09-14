package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.mock;
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

        when(assessment.getStatus())
                .thenReturn("IN_PROGRESS");

        when(assessmentService.getAssessmentById(1L))
                .thenReturn(assessment);

        when(questionRepository.findById(10L))
                .thenReturn(Optional.of(question));

        when(question.getCorrectAnswer())
                .thenReturn("B");
    }

    @Test
    void submitAnswerThrowsWhenQuestionWasAlreadyAnswered() {
        Long assessmentId = 1L;
        Long questionId = 10L;
        User user = mock(User.class);

        when(user.getId()).thenReturn(5L);

        DiagnosticAssessment assessment =
                new DiagnosticAssessment(
                        user,
                        "IN_PROGRESS",
                        OffsetDateTime.now()
                );

        DiagnosticQuestion question =
                mock(DiagnosticQuestion.class);

        DiagnosticResponse existingResponse =
                mock(DiagnosticResponse.class);

        when(assessmentService.getAssessmentById(assessmentId))
                .thenReturn(assessment);

        when(responseRepository.findByAssessmentIdAndQuestionId(
                assessmentId,
                questionId
        )).thenReturn(Optional.of(existingResponse));

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> responseService.submitAnswer(
                                assessmentId,
                                questionId,
                                "B",
                                user
                        )
                );

        assertEquals(
                "You have already answered this question in this assessment",
                exception.getMessage()
        );

        verify(questionRepository, never()).findById(questionId);
        verify(responseRepository, never()).save(any());
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
void submitAnswerThrowsWhenAssessmentIsCompleted() {
    when(assessment.getStatus())
            .thenReturn("COMPLETED");

    IllegalStateException exception =
            assertThrows(
                    IllegalStateException.class,
                    () -> responseService.submitAnswer(
                            1L,
                            10L,
                            "B",
                            authenticatedUser
                    )
            );

    assertEquals(
            "Answers cannot be submitted to a completed or abandoned assessment",
            exception.getMessage()
    );

    verify(questionRepository, never())
            .findById(anyLong());

    verify(responseRepository, never())
            .save(any(DiagnosticResponse.class));
}

@Test
void submitAnswerThrowsWhenAssessmentIsAbandoned() {
    when(assessment.getStatus())
            .thenReturn("ABANDONED");

    IllegalStateException exception =
            assertThrows(
                    IllegalStateException.class,
                    () -> responseService.submitAnswer(
                            1L,
                            10L,
                            "B",
                            authenticatedUser
                    )
            );

    assertEquals(
            "Answers cannot be submitted to a completed or abandoned assessment",
            exception.getMessage()
    );

    verify(questionRepository, never())
            .findById(anyLong());

    verify(responseRepository, never())
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