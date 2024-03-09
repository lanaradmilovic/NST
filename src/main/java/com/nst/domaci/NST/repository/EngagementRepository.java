package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    List<Engagement> findAllByMemberId(Long memberId);
    List<Engagement> findAllBySubjectId(Long subjectId);
    List<Engagement> findAllByMemberIdAndSubjectIdOrderByIdDesc(Long memberId, Long subjectId);
    List<Engagement> findAllByMemberIdAndYear(Long memberId, int year);
    List<Engagement> findAllBySubjectIdAndYear(Long subjectId, int year);

}
