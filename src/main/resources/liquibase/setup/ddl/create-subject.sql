CREATE TABLE subject (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL CHECK (LENGTH(name) BETWEEN 2 AND 10),
    espb VARCHAR(255),
    department_id BIGINT NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department(id)
);
