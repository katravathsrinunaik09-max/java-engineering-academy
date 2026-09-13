package com.javaacademy.api.lesson;

import com.javaacademy.api.skill.Skill;
import com.javaacademy.api.skill.SkillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;
    private final SkillService skillService;

    public LessonService(
            LessonRepository lessonRepository,
            SkillService skillService
    ) {
        this.lessonRepository = lessonRepository;
        this.skillService = skillService;
    }

    @Transactional(readOnly = true)
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Lesson> getLessonsBySkill(Long skillId) {
        return lessonRepository.findBySkillIdOrderByLessonOrderAsc(skillId);
    }

    @Transactional(readOnly = true)
    public Lesson getById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Lesson not found: " + id));
    }

    public Lesson createLesson(
            Long skillId,
            String title,
            String description,
            String content,
            String difficulty,
            Integer estimatedMinutes,
            Integer lessonOrder
    ) {
        Skill skill = skillService.getById(skillId);

        Lesson lesson = new Lesson(
                skill,
                title,
                description,
                content,
                difficulty,
                estimatedMinutes,
                lessonOrder
        );

        return lessonRepository.save(lesson);
    }
}