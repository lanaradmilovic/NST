package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.SecretaryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecretaryHistoryRepository extends JpaRepository<SecretaryHistory, Long> {
    List<SecretaryHistory> findAllByDepartmentId(Long departmentId);

    List<SecretaryHistory> findAllByMemberId(Long memberId);

    @Query("SELECT sh FROM SecretaryHistory sh " +
            "WHERE sh.department.id = :departmentId " +
            "AND sh.member.id = :memberId " +
            "AND sh.endDate IS NULL")
    SecretaryHistory findAllByDepartmentAndMember(@Param("departmentId") Long departmentId,
                                                  @Param("memberId") Long memberId);
    @Query("SELECT sh FROM SecretaryHistory sh " +
            "WHERE sh.member.id = :memberId " +
            "AND sh.endDate IS NULL")
    SecretaryHistory findCurrentByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT sh FROM SecretaryHistory sh " +
            "WHERE sh.department.id = :departmentId " +
            "AND sh.endDate IS NULL")
    SecretaryHistory findCurrentSecretaryByDepartmentId(@Param("departmentId") Long departmentId);
}
