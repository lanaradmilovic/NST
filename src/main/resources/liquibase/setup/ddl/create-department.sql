CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL CHECK (LENGTH(name) BETWEEN 2 AND 100),
    short_name VARCHAR(10) UNIQUE NOT NULL CHECK (LENGTH(short_name) BETWEEN 1 AND 10),
    current_leader_id BIGINT,
    current_secretary_id BIGINT
);
