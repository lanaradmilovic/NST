CREATE TABLE member (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(25) NOT NULL CHECK (LENGTH(firstname) BETWEEN 2 AND 25),
    lastname VARCHAR(25) NOT NULL CHECK (LENGTH(lastname) BETWEEN 2 AND 25),
    academic_title_id BIGINT NOT NULL,
    education_title_id BIGINT NOT NULL,
    scientific_field_id BIGINT NOT NULL,
    department_id BIGINT NOT NULL,
    FOREIGN KEY (academic_title_id) REFERENCES academic_title(id),
    FOREIGN KEY (education_title_id) REFERENCES education_title(id),
    FOREIGN KEY (scientific_field_id) REFERENCES scientific_field(id),
    FOREIGN KEY (department_id) REFERENCES department(id)
);
