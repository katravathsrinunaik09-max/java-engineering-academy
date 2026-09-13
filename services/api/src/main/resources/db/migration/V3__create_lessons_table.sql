CREATE TABLE lessons (
    id BIGSERIAL PRIMARY KEY,
    skill_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    content TEXT NOT NULL,
    difficulty VARCHAR(30) NOT NULL,
    estimated_minutes INTEGER NOT NULL,
    lesson_order INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_lessons_skill
        FOREIGN KEY (skill_id)
        REFERENCES skills(id),

    CONSTRAINT chk_lessons_difficulty
        CHECK (difficulty IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED')),

    CONSTRAINT chk_lessons_estimated_minutes
        CHECK (estimated_minutes > 0),

    CONSTRAINT chk_lessons_order
        CHECK (lesson_order > 0),

    CONSTRAINT uq_lessons_skill_order
        UNIQUE (skill_id, lesson_order)
);