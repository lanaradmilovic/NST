CREATE TABLE engagement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    engagement_year BIGINT,
    member_id BIGINT,
    subject_id BIGINT,
    teaching_form VARBINARY(255),
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (subject_id) REFERENCES subject(id)
);
