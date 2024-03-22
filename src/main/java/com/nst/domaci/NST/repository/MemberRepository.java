package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByDepartmentId(Long departmentId);
    List<Member> findAllByEducationTitleId(Long educationTitleId);
    List<Member> findAllByAcademicTitleId(Long id);
    List<Member> findAllByScientificFieldId(Long id);



}
