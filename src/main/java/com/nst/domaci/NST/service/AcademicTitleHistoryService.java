package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.AcademicTitleHistoryDto;
import com.nst.domaci.NST.entity.AcademicTitleHistory;

import java.util.List;

public interface AcademicTitleHistoryService {
    List<AcademicTitleHistory> findAll();

    List<AcademicTitleHistoryDto> findAllByMemberId(Long memberId);

    AcademicTitleHistory findById(Long id);

}
