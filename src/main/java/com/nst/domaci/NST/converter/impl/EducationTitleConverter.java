package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.EducationTitleDto;
import com.nst.domaci.NST.entity.EducationTitle;
import org.springframework.stereotype.Component;

@Component
public class EducationTitleConverter implements DtoEntityConverter<EducationTitleDto, EducationTitle> {

    @Override
    public EducationTitleDto toDto(EducationTitle educationTitle) {
        return EducationTitleDto.builder()
                .id(educationTitle.getId())
                .name(educationTitle.getName())
                .build();
    }

    @Override
    public EducationTitle toEntity(EducationTitleDto educationTitleDto) {
        return EducationTitle.builder()
                .name(educationTitleDto.getName())
                .build();
    }
}

