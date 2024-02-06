CREATE TABLE fund (
    subject_id BIGINT PRIMARY KEY,
    lecture INT,
    exercise INT,
    lab INT,
    FOREIGN KEY (subject_id) REFERENCES subject(id)
);
