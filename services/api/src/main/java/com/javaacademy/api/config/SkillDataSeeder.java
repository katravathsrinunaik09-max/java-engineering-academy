package com.javaacademy.api.config;

import com.javaacademy.api.skill.Skill;
import com.javaacademy.api.skill.SkillRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SkillDataSeeder {

    @Bean
    CommandLineRunner seedSkills(SkillRepository skillRepository) {
        return args -> {
            createIfMissing(
                    skillRepository,
                    "Java",
                    "Java programming fundamentals"
            );

            createIfMissing(
                    skillRepository,
                    "Data Structures and Algorithms",
                    "Problem solving, algorithms, and data structures"
            );

            createIfMissing(
                    skillRepository,
                    "SQL",
                    "Relational databases and SQL querying"
            );

            createIfMissing(
                    skillRepository,
                    "Spring Boot",
                    "Building backend applications with Spring Boot"
            );

            createIfMissing(
                    skillRepository,
                    "System Design",
                    "Designing scalable and reliable software systems"
            );
        };
    }

    private void createIfMissing(
            SkillRepository skillRepository,
            String name,
            String description
    ) {
        if (skillRepository.findByName(name).isEmpty()) {
            skillRepository.save(new Skill(name, description));
        }
    }
}
