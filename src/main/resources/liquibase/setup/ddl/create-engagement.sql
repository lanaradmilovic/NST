CREATE TABLE engagement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    year BIGINT,
    member_id BIGINT,
    subject_id BIGINT,
    teaching_form VARCHAR(255),
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (subject_id) REFERENCES subject(id)
);
