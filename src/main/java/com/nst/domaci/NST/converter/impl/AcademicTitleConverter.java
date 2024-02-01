package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.entity.AcademicTitle;
import org.springframework.stereotype.Component;

@Component
public class AcademicTitleConverter implements DtoEntityConverter<AcademicTitleDto, AcademicTitle> {
    @Override
    public AcademicTitleDto toDto(AcademicTitle academicTitle) {
        return AcademicTitleDto.builder()
                .id(academicTitle.getId())
                .name(academicTitle.getName())
                .build();
    }

    @Override
    public AcademicTitle toEntity(AcademicTitleDto academicTitleDto) {
        return AcademicTitle.builder()
                .name(academicTitleDto.getName())
                .build();
    }
}
