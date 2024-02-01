package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.ScientificFieldDto;
import com.nst.domaci.NST.entity.ScientificField;
import org.springframework.stereotype.Component;

@Component
public class ScientificFieldConverter implements DtoEntityConverter<ScientificFieldDto, ScientificField> {

    @Override
    public ScientificFieldDto toDto(ScientificField scientificField) {
        return ScientificFieldDto.builder()
                .id(scientificField.getId())
                .name(scientificField.getName())
                .build();
    }

    @Override
    public ScientificField toEntity(ScientificFieldDto scientificFieldDto) {
        return ScientificField.builder()
                .name(scientificFieldDto.getName())
                .build();
    }
}

