package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.SecretaryHistoryDto;
import com.nst.domaci.NST.entity.SecretaryHistory;
import org.springframework.stereotype.Component;

@Component
public class SecretaryHistoryConverter implements DtoEntityConverter<SecretaryHistoryDto, SecretaryHistory> {
    @Override
    public SecretaryHistoryDto toDto(SecretaryHistory secretaryHistory) {
        return SecretaryHistoryDto.builder()
                .id(secretaryHistory.getId())
                .memberId(secretaryHistory.getMember().getId())
                .startDate(secretaryHistory.getStartDate())
                .endDate(secretaryHistory.getEndDate())
                .build();
    }

    @Override
    public SecretaryHistory toEntity(SecretaryHistoryDto secretaryHistoryDto) {
        return SecretaryHistory.builder()
                .startDate(secretaryHistoryDto.getStartDate())
                .endDate(secretaryHistoryDto.getEndDate())
                .build();
    }
}

