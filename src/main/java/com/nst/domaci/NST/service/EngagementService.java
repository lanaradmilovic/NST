package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.EngagementDto;
import com.nst.domaci.NST.entity.Engagement;

import java.util.List;

public interface EngagementService {
    List<Engagement> findAll();

    List<EngagementDto> findAllBySubjectId(Long subjectId);
    List<EngagementDto> findAllByMemberId(Long memberId);

    Engagement findById(Long id);

    EngagementDto save(EngagementDto engagementDto);

    EngagementDto update(EngagementDto engagementDto);

    void delete(Long id);
}
