DELETE FROM member;

INSERT INTO member (firstname, lastname, academic_title_id, education_title_id, scientific_field_id, department_id)
VALUES
    ('Lana', 'Radmilovic', 1, 2, 4, 3),
    ('Stefan', 'Kovacevic', 4, 1, 3, 3),
    ('Petar', 'Djokovic', 3, 4, 1, 1),
    ('Marta', 'Rajic', 2, 3, 2, 1);
