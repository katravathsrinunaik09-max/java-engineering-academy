package com.javaacademy.api.diagnostic;

import com.javaacademy.api.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class DiagnosticResponseService {

    private final DiagnosticResponseRepository responseRepository;
    private final DiagnosticAssessmentService assessmentService;
    private final DiagnosticQuestionRepository questionRepository;

    public DiagnosticResponseService(
            DiagnosticResponseRepository responseRepository,
            DiagnosticAssessmentService assessmentService,
            DiagnosticQuestionRepository questionRepository
    ) {
        this.responseRepository = responseRepository;
        this.assessmentService = assessmentService;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public DiagnosticResponse submitAnswer(
            Long assessmentId,
            Long questionId,
            String answer,
            User authenticatedUser
    ) {
        DiagnosticAssessment assessment =
                assessmentService.getAssessmentById(assessmentId);

        if (!assessment.getUser().getId().equals(authenticatedUser.getId())) {
            throw new IllegalArgumentException(
                    "You are not allowed to submit answers to this assessment"
            );
        }

        if (!"IN_PROGRESS".equals(assessment.getStatus())) {
            throw new IllegalStateException(
                    "Answers cannot be submitted to a completed or abandoned assessment"
            );
        }

        responseRepository.findByAssessmentIdAndQuestionId(
                        assessmentId,
                        questionId
                )
                .ifPresent(existingResponse -> {
                    throw new IllegalArgumentException(
                            "You have already answered this question in this assessment"
                    );
                });

        DiagnosticQuestion question =
                questionRepository.findById(questionId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Diagnostic question not found: " + questionId
                                ));

        boolean correct = question.getCorrectAnswer().equals(answer);

        DiagnosticResponse response = new DiagnosticResponse(
                assessment,
                question,
                answer,
                correct,
                OffsetDateTime.now()
        );

        return responseRepository.save(response);
    }
}