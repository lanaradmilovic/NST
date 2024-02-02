package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.EngagementDto;

import java.util.List;

public interface EngagementService {
    List<EngagementDto> findAll();

    List<EngagementDto> findAllBySubjectId(Long subjectId);
    List<EngagementDto> findAllByMemberId(Long memberId);

    EngagementDto findById(Long id);

    EngagementDto save(EngagementDto engagementDto);

    EngagementDto update(EngagementDto engagementDto);

    void delete(Long id);
}
