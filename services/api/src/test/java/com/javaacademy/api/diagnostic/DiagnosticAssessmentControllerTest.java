package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import com.javaacademy.api.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DiagnosticAssessmentControllerTest {

    @Test
    void pingReturnsExpectedMessage() {
        DiagnosticAssessmentService assessmentService =
                mock(DiagnosticAssessmentService.class);

        UserService userService =
                mock(UserService.class);

        DiagnosticAssessmentController controller =
                new DiagnosticAssessmentController(
                        assessmentService,
                        userService
                );

        String response = controller.ping();

        assertEquals(
                "Diagnostic assessment API is running",
                response
        );
    }

    @Test
    void startAssessmentUsesAuthenticatedUser() {
        DiagnosticAssessmentService assessmentService =
                mock(DiagnosticAssessmentService.class);

        UserService userService =
                mock(UserService.class);

        Authentication authentication =
                mock(Authentication.class);

        User user =
                mock(User.class);

        DiagnosticAssessment assessment =
                mock(DiagnosticAssessment.class);

        when(authentication.getName())
                .thenReturn("srinu");

        when(userService.getByUsername("srinu"))
                .thenReturn(user);

        when(assessmentService.startAssessment(user))
                .thenReturn(assessment);

        when(assessment.getId())
                .thenReturn(1L);

        when(assessment.getUser())
                .thenReturn(user);

        when(user.getId())
                .thenReturn(7L);

        when(assessment.getStatus())
                .thenReturn("IN_PROGRESS");

        DiagnosticAssessmentController controller =
                new DiagnosticAssessmentController(
                        assessmentService,
                        userService
                );

        DiagnosticAssessmentResponse response =
                controller.startAssessment(authentication);

        assertEquals(1L, response.id());
        assertEquals(7L, response.userId());
        assertEquals("IN_PROGRESS", response.status());

        verify(userService).getByUsername("srinu");
        verify(assessmentService).startAssessment(user);
    }

    @Test
    void getAssessmentByIdReturnsSafeResponseFromService() {
        DiagnosticAssessmentService assessmentService =
                mock(DiagnosticAssessmentService.class);

        UserService userService =
                mock(UserService.class);

        DiagnosticAssessment assessment =
                mock(DiagnosticAssessment.class);

        User user =
                mock(User.class);

        when(assessmentService.getAssessmentById(1L))
                .thenReturn(assessment);

        when(assessment.getId())
                .thenReturn(1L);

        when(assessment.getUser())
                .thenReturn(user);

        when(user.getId())
                .thenReturn(7L);

        when(assessment.getStatus())
                .thenReturn("IN_PROGRESS");

        DiagnosticAssessmentController controller =
                new DiagnosticAssessmentController(
                        assessmentService,
                        userService
                );

        DiagnosticAssessmentResponse response =
                controller.getAssessmentById(1L);

        assertEquals(1L, response.id());
        assertEquals(7L, response.userId());
        assertEquals("IN_PROGRESS", response.status());

        verify(assessmentService).getAssessmentById(1L);
    }

    @Test
    void submitAnswerPassesRequestToResponseService() {
        DiagnosticAssessmentService assessmentService =
                mock(DiagnosticAssessmentService.class);

        DiagnosticResponseService responseService =
                mock(DiagnosticResponseService.class);

        UserService userService =
                mock(UserService.class);

        DiagnosticAnswerRequest request =
                new DiagnosticAnswerRequest(
                        11L,
                        "B"
                );

        DiagnosticResponse response =
                mock(DiagnosticResponse.class);

        when(responseService.submitAnswer(
                5L,
                11L,
                "B"
        )).thenReturn(response);

        DiagnosticAssessmentController controller =
                new DiagnosticAssessmentController(
                        assessmentService,
                        responseService,
                        userService
                );

        DiagnosticResponse actualResponse =
                controller.submitAnswer(
                        5L,
                        request
                );

        assertEquals(response, actualResponse);

        verify(responseService).submitAnswer(
                5L,
                11L,
                "B"
        );
    }
}