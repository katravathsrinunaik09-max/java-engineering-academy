package com.javaacademy.api.skill;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService skillService;

    @Test
    void getAllSkillsReturnsAllSkills() {
        Skill javaSkill = new Skill("Java", "Java programming");
        Skill sqlSkill = new Skill("SQL", "Database querying");

        when(skillRepository.findAll())
                .thenReturn(List.of(javaSkill, sqlSkill));

        List<Skill> result = skillService.getAllSkills();

        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getName());
        assertEquals("SQL", result.get(1).getName());

        verify(skillRepository).findAll();
    }

    @Test
    void getByIdReturnsSkillWhenFound() {
        Skill skill = new Skill("Java", "Java programming");

        when(skillRepository.findById(1L))
                .thenReturn(Optional.of(skill));

        Skill result = skillService.getById(1L);

        assertEquals("Java", result.getName());
        verify(skillRepository).findById(1L);
    }

    @Test
    void getByIdThrowsExceptionWhenSkillIsMissing() {
        when(skillRepository.findById(99L))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> skillService.getById(99L)
        );

        assertEquals("Skill not found: 99", exception.getMessage());
        verify(skillRepository).findById(99L);
    }

    @Test
    void createSkillSavesNewSkill() {
        when(skillRepository.findByName("Java"))
                .thenReturn(Optional.empty());

        Skill savedSkill = new Skill("Java", "Java programming");

        when(skillRepository.save(any(Skill.class)))
                .thenReturn(savedSkill);

        Skill result = skillService.createSkill(
                "Java",
                "Java programming"
        );

        assertEquals("Java", result.getName());
        assertEquals("Java programming", result.getDescription());

        verify(skillRepository).findByName("Java");
        verify(skillRepository).save(any(Skill.class));
    }

    @Test
    void createSkillThrowsExceptionWhenNameAlreadyExists() {
        Skill existingSkill = new Skill("Java", "Existing skill");

        when(skillRepository.findByName("Java"))
                .thenReturn(Optional.of(existingSkill));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> skillService.createSkill(
                        "Java",
                        "Java programming"
                )
        );

        assertEquals("Skill already exists: Java", exception.getMessage());
        verify(skillRepository).findByName("Java");
    }
}