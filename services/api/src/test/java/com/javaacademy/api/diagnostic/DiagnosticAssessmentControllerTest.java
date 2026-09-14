package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import com.javaacademy.api.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        DiagnosticAssessmentController controller =
                new DiagnosticAssessmentController(
                        assessmentService,
                        userService
                );

        DiagnosticAssessmentResponse response =
                controller.startAssessment(authentication);

        assertEquals(1L, response.id());
        verify(userService).getByUsername("srinu");
        verify(assessmentService).startAssessment(user);
    }

    @Test
    void getAssessmentByIdReturnsAssessmentFromService() {
        DiagnosticAssessmentService assessmentService =
                mock(DiagnosticAssessmentService.class);

        UserService userService =
                mock(UserService.class);

        DiagnosticAssessment assessment =
                mock(DiagnosticAssessment.class);

        when(assessmentService.getAssessmentById(1L))
                .thenReturn(assessment);

        DiagnosticAssessmentController controller =
                new DiagnosticAssessmentController(
                        assessmentService,
                        userService
                );

        DiagnosticAssessment response =
                controller.getAssessmentById(1L);

        assertEquals(assessment, response);
    }
}