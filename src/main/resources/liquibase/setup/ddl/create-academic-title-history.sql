CREATE TABLE academic_title_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id BIGINT,
    start_date DATE NOT NULL,
    end_date DATE,
    academic_title_id BIGINT NOT NULL,
    scientific_field_id BIGINT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (academic_title_id) REFERENCES academic_title(id),
    FOREIGN KEY (scientific_field_id) REFERENCES scientific_field(id)
);
