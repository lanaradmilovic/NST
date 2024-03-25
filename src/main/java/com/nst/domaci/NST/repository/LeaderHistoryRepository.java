package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.LeaderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderHistoryRepository extends JpaRepository<LeaderHistory, Long> {
    List<LeaderHistory> findAllByDepartmentId(Long departmentId);

    List<LeaderHistory> findAllByMemberId(Long memberId);

    @Query("SELECT lh FROM LeaderHistory lh " +
            "WHERE lh.department.id = :departmentId " +
            "AND lh.member.id = :memberId " +
            "AND lh.endDate IS NULL")
    List<LeaderHistory> findAllByDepartmentAndMember(@Param("departmentId") Long departmentId,
                                                     @Param("memberId") Long memberId);

    @Query("SELECT lh FROM LeaderHistory lh " +
            "WHERE lh.member.id = :memberId " +
            "AND lh.endDate IS NULL")
    LeaderHistory findCurrentLeaderByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT lh FROM LeaderHistory lh " +
            "WHERE lh.department.id = :departmentId " +
            "AND lh.endDate IS NULL")
    LeaderHistory findCurrentLeaderByDepartmentId(@Param("departmentId") Long departmentId);

}
