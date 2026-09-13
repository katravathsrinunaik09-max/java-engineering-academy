CREATE TABLE lesson_progress (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    lesson_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'NOT_STARTED',
    started_at TIMESTAMPTZ,
    completed_at TIMESTAMPTZ,
    time_spent_seconds INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_lesson_progress_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_lesson_progress_lesson
        FOREIGN KEY (lesson_id)
        REFERENCES lessons(id),

    CONSTRAINT chk_lesson_progress_status
        CHECK (status IN ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED')),

    CONSTRAINT chk_lesson_progress_time
        CHECK (time_spent_seconds >= 0),

    CONSTRAINT uq_lesson_progress_user_lesson
        UNIQUE (user_id, lesson_id)
);