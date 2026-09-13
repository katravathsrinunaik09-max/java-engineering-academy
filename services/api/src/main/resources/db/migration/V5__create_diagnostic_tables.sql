CREATE TABLE diagnostic_assessments (

    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    status VARCHAR(30) NOT NULL DEFAULT 'IN_PROGRESS',

    started_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    completed_at TIMESTAMPTZ,

    CONSTRAINT fk_diagnostic_assessments_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT chk_diagnostic_assessments_status
        CHECK (status IN ('IN_PROGRESS', 'COMPLETED', 'ABANDONED')),

    CONSTRAINT chk_diagnostic_assessments_completion
        CHECK (
            (status = 'COMPLETED' AND completed_at IS NOT NULL)
            OR
            (status <> 'COMPLETED')
        )
);

CREATE TABLE diagnostic_questions (

    id BIGSERIAL PRIMARY KEY,

    skill_id BIGINT NOT NULL,

    question_text TEXT NOT NULL,

    question_type VARCHAR(30) NOT NULL,

    difficulty VARCHAR(30) NOT NULL,

    options_json JSONB,

    correct_answer TEXT NOT NULL,

    explanation TEXT,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_diagnostic_questions_skill
        FOREIGN KEY (skill_id)
        REFERENCES skills(id),

    CONSTRAINT chk_diagnostic_questions_type
        CHECK (question_type IN ('MCQ', 'TRUE_FALSE', 'CODE_OUTPUT')),

    CONSTRAINT chk_diagnostic_questions_difficulty
        CHECK (difficulty IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED'))
);

CREATE TABLE diagnostic_responses (

    id BIGSERIAL PRIMARY KEY,

    assessment_id BIGINT NOT NULL,

    question_id BIGINT NOT NULL,

    answer TEXT NOT NULL,

    is_correct BOOLEAN NOT NULL,

    answered_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_diagnostic_responses_assessment
        FOREIGN KEY (assessment_id)
        REFERENCES diagnostic_assessments(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_diagnostic_responses_question
        FOREIGN KEY (question_id)
        REFERENCES diagnostic_questions(id),

    CONSTRAINT uq_diagnostic_responses_assessment_question
        UNIQUE (assessment_id, question_id)
);

CREATE INDEX idx_diagnostic_assessments_user_id
    ON diagnostic_assessments(user_id);

CREATE INDEX idx_diagnostic_questions_skill_id
    ON diagnostic_questions(skill_id);

CREATE INDEX idx_diagnostic_responses_assessment_id
    ON diagnostic_responses(assessment_id);