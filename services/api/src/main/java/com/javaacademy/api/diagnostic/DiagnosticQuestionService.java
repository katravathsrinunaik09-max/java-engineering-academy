package com.javaacademy.api.diagnostic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DiagnosticQuestionService {

    private final DiagnosticQuestionRepository diagnosticQuestionRepository;

    public DiagnosticQuestionService(
            DiagnosticQuestionRepository diagnosticQuestionRepository
    ) {
        this.diagnosticQuestionRepository = diagnosticQuestionRepository;
    }

    public List<DiagnosticQuestion> getAllQuestions() {
        return diagnosticQuestionRepository.findAll();
    }

    public DiagnosticQuestion getQuestionById(Long id) {
        return diagnosticQuestionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Diagnostic question not found: " + id
                        )
                );
    }

    public List<DiagnosticQuestion> getQuestionsByDifficulty(
            String difficulty
    ) {
        return diagnosticQuestionRepository.findByDifficulty(difficulty);
    }

    public List<DiagnosticQuestionResponse> getQuestionsBySkillId(
            Long skillId
    ) {
        return diagnosticQuestionRepository.findBySkillId(skillId)
                .stream()
                .map(DiagnosticQuestionResponse::from)
                .toList();
    }
}