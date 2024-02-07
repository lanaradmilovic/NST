package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.LectureSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureScheduleRepository extends JpaRepository<LectureSchedule, Long> {
    List<LectureSchedule> findAllBySubjectIdAndYear(Long subjectId, int year);
}
