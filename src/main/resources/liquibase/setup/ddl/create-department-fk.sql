ALTER TABLE department
ADD FOREIGN KEY (current_leader_id) REFERENCES member(id),
ADD FOREIGN KEY (current_secretary_id) REFERENCES member(id);