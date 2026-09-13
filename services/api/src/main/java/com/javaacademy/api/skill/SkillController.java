package com.javaacademy.api.skill;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Skill createSkill(
            @Valid @RequestBody CreateSkillRequest request
    ) {
        return skillService.createSkill(
                request.name(),
                request.description()
        );
    }

    public record CreateSkillRequest(

            @NotBlank(message = "Skill name is required")
            @Size(max = 100, message = "Skill name must be at most 100 characters")
            String name,

            @Size(max = 500, message = "Description must be at most 500 characters")
            String description
    ) {
    }
}
