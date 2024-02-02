package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.LeaderHistoryDto;

import java.util.List;

public interface LeaderHistoryService {
    List<LeaderHistoryDto> findAll();

    List<LeaderHistoryDto> findAllByDepartmentId(Long departmentId);

    List<LeaderHistoryDto> findAllByMemberId(Long memberId);

    LeaderHistoryDto findById(Long id);
}
