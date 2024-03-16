package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.FundDto;
import com.nst.domaci.NST.entity.Fund;

import org.springframework.stereotype.Component;

@Component
public class FundConverter implements DtoEntityConverter<FundDto, Fund> {
    @Override
    public FundDto toDto(Fund fund) {
        return FundDto.builder()
                .lecture(fund.getLecture())
                .exercise(fund.getExercise())
                .lab(fund.getLab())
                .build();
    }

    @Override
    public Fund toEntity(FundDto fundDto) {
        return Fund.builder()
                .lecture(fundDto.getLecture())
                .exercise(fundDto.getExercise())
                .lab(fundDto.getLab())
                .build();
    }
}

