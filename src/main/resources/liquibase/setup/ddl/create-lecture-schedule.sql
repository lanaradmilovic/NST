CREATE TABLE lecture_schedule (
    id BIGINT PRIMARY KEY,
    subject_id BIGINT NOT NULL,
    year INT,
    FOREIGN KEY (subject_id) REFERENCES subject(id)
);
