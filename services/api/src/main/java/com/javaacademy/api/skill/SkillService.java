package com.javaacademy.api.skill;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Transactional(readOnly = true)
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Skill getById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Skill not found: " + id));
    }

    public Skill createSkill(String name, String description) {
        if (skillRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException(
                    "Skill already exists: " + name
            );
        }

        Skill skill = new Skill(name, description);
        return skillRepository.save(skill);
    }
}