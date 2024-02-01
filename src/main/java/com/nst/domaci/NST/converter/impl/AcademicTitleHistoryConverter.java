package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.AcademicTitleHistoryDto;
import com.nst.domaci.NST.entity.AcademicTitleHistory;
import org.springframework.stereotype.Component;

@Component
public class AcademicTitleHistoryConverter implements DtoEntityConverter<AcademicTitleHistoryDto, AcademicTitleHistory> {
    @Override
    public AcademicTitleHistoryDto toDto(AcademicTitleHistory academicTitleHistory) {
        return AcademicTitleHistoryDto.builder()
                .id(academicTitleHistory.getId())
                .memberId(academicTitleHistory.getMember().getId())
                .academicTitleId(academicTitleHistory.getAcademicTitle().getId())
                .scientificFieldId(academicTitleHistory.getScientificField().getId())
                .startDate(academicTitleHistory.getStartDate())
                .endDate(academicTitleHistory.getEndDate())
                .build();
    }

    @Override
    public AcademicTitleHistory toEntity(AcademicTitleHistoryDto academicTitleHistoryDto) {
        return AcademicTitleHistory.builder()
                .startDate(academicTitleHistoryDto.getStartDate())
                .endDate(academicTitleHistoryDto.getEndDate())
                .build();
    }
}

