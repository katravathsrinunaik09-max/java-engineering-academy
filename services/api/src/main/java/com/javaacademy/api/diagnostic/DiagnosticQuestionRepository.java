package com.javaacademy.api.diagnostic;

import com.javaacademy.api.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnosticQuestionRepository
        extends JpaRepository<DiagnosticQuestion, Long> {

    List<DiagnosticQuestion> findBySkill(Skill skill);

    List<DiagnosticQuestion> findBySkillId(Long skillId);

    List<DiagnosticQuestion> findByDifficulty(String difficulty);
}