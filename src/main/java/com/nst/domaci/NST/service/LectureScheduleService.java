package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.LectureScheduleDto;
import com.nst.domaci.NST.exception.ResourceNotFoundException;

import java.util.List;

public interface LectureScheduleService {
    List<LectureScheduleDto> findAll();

    LectureScheduleDto findById(Long id) throws ResourceNotFoundException;

    List<LectureScheduleDto> findAllBySubjectIdAndYear(Long subjectId, int year);

    void delete(Long id) throws ResourceNotFoundException;

}
