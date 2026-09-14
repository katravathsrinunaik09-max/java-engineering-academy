INSERT INTO diagnostic_questions (
    skill_id,
    question_text,
    question_type,
    difficulty,
    options_json,
    correct_answer,
    explanation
)
SELECT
    id,
    'Which keyword is used to inherit from a class in Java?',
    'MCQ',
    'BEGINNER',
    '[{"key":"A","text":"implements"},{"key":"B","text":"extends"},{"key":"C","text":"inherits"},{"key":"D","text":"super"}]'::jsonb,
    'B',
    'The extends keyword is used when one class inherits from another class.'
FROM skills
WHERE name = 'Java';

INSERT INTO diagnostic_questions (
    skill_id,
    question_text,
    question_type,
    difficulty,
    options_json,
    correct_answer,
    explanation
)
SELECT
    id,
    'What is the time complexity of binary search on a sorted array?',
    'MCQ',
    'BEGINNER',
    '[{"key":"A","text":"O(1)"},{"key":"B","text":"O(n)"},{"key":"C","text":"O(log n)"},{"key":"D","text":"O(n log n)"}]'::jsonb,
    'C',
    'Binary search halves the search space after each comparison, giving O(log n) time complexity.'
FROM skills
WHERE name = 'Data Structures and Algorithms';

INSERT INTO diagnostic_questions (
    skill_id,
    question_text,
    question_type,
    difficulty,
    options_json,
    correct_answer,
    explanation
)
SELECT
    id,
    'Which SQL clause is used to filter rows before grouping?',
    'MCQ',
    'BEGINNER',
    '[{"key":"A","text":"HAVING"},{"key":"B","text":"GROUP BY"},{"key":"C","text":"WHERE"},{"key":"D","text":"ORDER BY"}]'::jsonb,
    'C',
    'The WHERE clause filters individual rows before grouping takes place.'
FROM skills
WHERE name = 'SQL';

INSERT INTO diagnostic_questions (
    skill_id,
    question_text,
    question_type,
    difficulty,
    options_json,
    correct_answer,
    explanation
)
SELECT
    id,
    'Which annotation marks a class as a Spring service component?',
    'MCQ',
    'BEGINNER',
    '[{"key":"A","text":"@Controller"},{"key":"B","text":"@Repository"},{"key":"C","text":"@Service"},{"key":"D","text":"@Entity"}]'::jsonb,
    'C',
    'The @Service annotation identifies a class as a service-layer component managed by Spring.'
FROM skills
WHERE name = 'Spring Boot';

INSERT INTO diagnostic_questions (
    skill_id,
    question_text,
    question_type,
    difficulty,
    options_json,
    correct_answer,
    explanation
)
SELECT
    id,
    'Which quality is most important when designing a scalable system?',
    'MCQ',
    'BEGINNER',
    '[{"key":"A","text":"Single point of failure"},{"key":"B","text":"Loose coupling"},{"key":"C","text":"Hard-coded configuration"},{"key":"D","text":"Shared mutable state everywhere"}]'::jsonb,
    'B',
    'Loose coupling makes components easier to scale, change, test, and operate independently.'
FROM skills
WHERE name = 'System Design';