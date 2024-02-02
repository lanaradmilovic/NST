package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByFirstName(String firstName);
    List<Member> findAllByDepartmentId(Long departmentId);

}
