package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findAllByEngagementId(Long engagementId);
    List<Lecture> findAllByEngagementMemberIdAndEngagementEngagementYear(Long memberId, Long year);
    List<Lecture> findAllByEngagementSubjectIdAndEngagementEngagementYear(Long subjectId, Long year);

}
