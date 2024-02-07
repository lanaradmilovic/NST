package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.LectureDto;
import com.nst.domaci.NST.exception.ResourceNotFoundException;

import java.util.List;

public interface LectureService {

    List<LectureDto> findAll();

    LectureDto findById(Long id) throws ResourceNotFoundException;

    List<LectureDto> findAllByEngagementId(Long engagementId);

    List<LectureDto> findAllByEngagementMemberIdAndEngagementYear(Long memberId, Long year);

    List<LectureDto> findAllByEngagementSubjectIdAndEngagementYear(Long subjectId, Long year);

    void delete(Long id) throws ResourceNotFoundException;

    LectureDto save(LectureDto lectureDto);

    LectureDto update(LectureDto lectureDto) throws ResourceNotFoundException;

}

