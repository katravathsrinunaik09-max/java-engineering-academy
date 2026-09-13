package com.javaacademy.api.diagnostic;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnostic/questions")
public class DiagnosticQuestionController {

    private final DiagnosticQuestionService diagnosticQuestionService;

    public DiagnosticQuestionController(
            DiagnosticQuestionService diagnosticQuestionService
    ) {
        this.diagnosticQuestionService = diagnosticQuestionService;
    }

    @GetMapping
    public List<DiagnosticQuestion> getAllQuestions() {
        return diagnosticQuestionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public DiagnosticQuestion getQuestionById(@PathVariable Long id) {
        return diagnosticQuestionService.getQuestionById(id);
    }

    @GetMapping("/difficulty/{difficulty}")
    public List<DiagnosticQuestion> getQuestionsByDifficulty(
            @PathVariable String difficulty
    ) {
        return diagnosticQuestionService.getQuestionsByDifficulty(difficulty);
    }
}