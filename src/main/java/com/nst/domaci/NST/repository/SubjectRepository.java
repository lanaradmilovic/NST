package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllByDepartmentId(Long departmentId);
}
