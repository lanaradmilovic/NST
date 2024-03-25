package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.LectureScheduleDto;
import com.nst.domaci.NST.entity.LectureSchedule;
import com.nst.domaci.NST.exception.ResourceNotFoundException;

import java.util.List;

public interface LectureScheduleService {
    List<LectureSchedule> findAll();

    LectureSchedule findById(Long id) throws ResourceNotFoundException;

    List<LectureSchedule> findAllBySubjectIdAndScheduleYear(Long subjectId, int year);

    void delete(Long id) throws ResourceNotFoundException;
//    void addLectureToSchedule(Long lectureScheduleId, Long lectureId) throws ResourceNotFoundException;
    LectureScheduleDto save(LectureScheduleDto lectureSchedule);
}
