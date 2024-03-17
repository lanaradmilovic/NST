package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.LectureDto;
import com.nst.domaci.NST.entity.Lecture;
import com.nst.domaci.NST.exception.ResourceNotFoundException;

import java.util.List;

public interface LectureService {

    List<Lecture> findAll();

    Lecture findById(Long id) throws ResourceNotFoundException;

    List<Lecture> findAllByEngagementId(Long engagementId);

    List<Lecture> findAllByEngagementMemberIdAndEngagementYear(Long memberId, Long year);

    List<Lecture> findAllByEngagementSubjectIdAndEngagementYear(Long subjectId, Long year);

    void delete(Long id) throws ResourceNotFoundException;

    LectureDto save(LectureDto lectureDto);

    LectureDto update(LectureDto lectureDto) throws ResourceNotFoundException;

}

