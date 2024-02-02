package com.nst.domaci.NST.service;


import com.nst.domaci.NST.dto.SecretaryHistoryDto;

import java.util.List;

public interface SecretaryHistoryService {
    List<SecretaryHistoryDto> findAll();

    List<SecretaryHistoryDto> findAllByDepartmentId(Long departmentId);

    List<SecretaryHistoryDto> findAllByMemberId(Long memberId);

    SecretaryHistoryDto findById(Long id);
}
