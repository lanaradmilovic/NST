package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.EngagementDto;
import com.nst.domaci.NST.entity.Engagement;
import org.springframework.stereotype.Component;

@Component
public class EngagementConverter implements DtoEntityConverter<EngagementDto, Engagement> {
    @Override
    public EngagementDto toDto(Engagement engagement) {
        return EngagementDto.builder()
                .id(engagement.getId())
                .memberId(engagement.getMember().getId())
                .subjectId(engagement.getSubject().getId())
                .engagementYear(engagement.getEngagementYear())
                .teachingForm(engagement.getTeachingForm())
                .build();
    }

    @Override
    public Engagement toEntity(EngagementDto engagementDto) {
        return Engagement.builder()
                .engagementYear(engagementDto.getEngagementYear())
                .teachingForm(engagementDto.getTeachingForm())
                .build();
    }
}
