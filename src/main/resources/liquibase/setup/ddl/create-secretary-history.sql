CREATE TABLE secretary_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    department_id BIGINT,
    member_id BIGINT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (member_id) REFERENCES member(id)
);
