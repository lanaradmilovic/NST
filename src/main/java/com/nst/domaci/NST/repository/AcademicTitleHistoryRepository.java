package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.AcademicTitleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicTitleHistoryRepository extends JpaRepository<AcademicTitleHistory, Long> {
    List<AcademicTitleHistory> findAllByMemberId(Long memberId);
    @Query("SELECT ath FROM AcademicTitleHistory ath " +
            "WHERE ath.member.id = :memberId " +
            "AND ath.endDate IS NULL")
    AcademicTitleHistory findCurrentAcademicTitleByMemberId(@Param("memberId") Long memberId);


}
