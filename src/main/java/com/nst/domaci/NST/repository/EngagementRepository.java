package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngagementRepository extends JpaRepository<Engagement, Long> {
    List<Engagement> findAllByMemberId(Long memberId);
    List<Engagement> findAllBySubjectId(Long subjectId);
    List<Engagement> findAllByMemberIdAndSubjectId(Long memberId, Long subjectId);

}
