package com.javaacademy.api.diagnostic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DiagnosticQuestionServiceTest {

    private DiagnosticQuestionRepository questionRepository;
    private DiagnosticQuestionService questionService;

    @BeforeEach
    void setUp() {
        questionRepository = mock(DiagnosticQuestionRepository.class);

        questionService = new DiagnosticQuestionService(
                questionRepository
        );
    }

    @Test
    void getAllQuestionsReturnsQuestionsFromRepository() {
        DiagnosticQuestion question = mock(DiagnosticQuestion.class);

        when(questionRepository.findAll())
                .thenReturn(List.of(question));

        List<DiagnosticQuestion> questions =
                questionService.getAllQuestions();

        assertEquals(1, questions.size());
        assertSame(question, questions.get(0));
    }

    @Test
    void getQuestionByIdReturnsQuestionFromRepository() {
        DiagnosticQuestion question = mock(DiagnosticQuestion.class);

        when(questionRepository.findById(10L))
                .thenReturn(Optional.of(question));

        DiagnosticQuestion result =
                questionService.getQuestionById(10L);

        assertSame(question, result);
    }

    @Test
    void getQuestionByIdThrowsWhenQuestionDoesNotExist() {
        when(questionRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> questionService.getQuestionById(999L)
        );
    }

    @Test
    void getQuestionsByDifficultyReturnsMatchingQuestions() {
        DiagnosticQuestion question = mock(DiagnosticQuestion.class);

        when(questionRepository.findByDifficulty("BEGINNER"))
                .thenReturn(List.of(question));

        List<DiagnosticQuestion> questions =
                questionService.getQuestionsByDifficulty("BEGINNER");

        assertEquals(1, questions.size());
        assertSame(question, questions.get(0));
    }

    @Test
    void getQuestionsBySkillIdReturnsSafeQuestionResponses() {
        com.javaacademy.api.skill.Skill skill =
                mock(com.javaacademy.api.skill.Skill.class);

        DiagnosticQuestion question =
                mock(DiagnosticQuestion.class);

        when(skill.getId())
                .thenReturn(3L);

        when(question.getId())
                .thenReturn(10L);

        when(question.getSkill())
                .thenReturn(skill);

        when(question.getQuestionText())
                .thenReturn("What is encapsulation?");

        when(question.getQuestionType())
                .thenReturn("MCQ");

        when(question.getDifficulty())
                .thenReturn("BEGINNER");

        when(question.getOptionsJson())
                .thenReturn("[\"A\", \"B\", \"C\"]");

        when(questionRepository.findBySkillId(3L))
                .thenReturn(List.of(question));

        List<DiagnosticQuestionResponse> responses =
                questionService.getQuestionsBySkillId(3L);

        assertEquals(1, responses.size());

        DiagnosticQuestionResponse response =
                responses.get(0);

        assertEquals(10L, response.id());
        assertEquals(3L, response.skillId());

        assertEquals(
                "What is encapsulation?",
                response.questionText()
        );

        assertEquals("MCQ", response.questionType());
        assertEquals("BEGINNER", response.difficulty());

        assertEquals(
                "[\"A\",\"B\",\"C\"]",
                response.options().toString()
        );
    }
}