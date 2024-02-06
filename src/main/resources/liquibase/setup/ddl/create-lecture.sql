CREATE TABLE lecture (
    id BIGINT PRIMARY KEY,
    form VARCHAR(255),
    engagement_id BIGINT,
    date_time TIMESTAMP,
    description VARCHAR(255),
    lecture_schedule_id BIGINT,
    FOREIGN KEY (engagement_id) REFERENCES engagement(id),
    FOREIGN KEY (lecture_schedule_id) REFERENCES lecture_schedule(id)
);
