package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.AcademicTitleHistoryDto;

import java.util.List;

public interface AcademicTitleHistoryService {
    List<AcademicTitleHistoryDto> findAll();

    List<AcademicTitleHistoryDto> findAllByMemberId(Long memberId);

    AcademicTitleHistoryDto findById(Long id);

}
