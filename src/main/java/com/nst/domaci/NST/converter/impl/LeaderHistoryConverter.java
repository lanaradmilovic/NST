package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.LeaderHistoryDto;
import com.nst.domaci.NST.entity.LeaderHistory;
import org.springframework.stereotype.Component;

@Component
public class LeaderHistoryConverter implements DtoEntityConverter<LeaderHistoryDto, LeaderHistory> {
    @Override
    public LeaderHistoryDto toDto(LeaderHistory leaderHistory) {
        return LeaderHistoryDto.builder()
                .id(leaderHistory.getId())
                .departmentId(leaderHistory.getDepartment().getId())
                .memberId(leaderHistory.getMember().getId())
                .startDate(leaderHistory.getStartDate())
                .endDate(leaderHistory.getEndDate())
                .build();
    }

    @Override
    public LeaderHistory toEntity(LeaderHistoryDto leaderHistoryDto) {
        return LeaderHistory.builder()
                .startDate(leaderHistoryDto.getStartDate())
                .endDate(leaderHistoryDto.getEndDate())
                .build();
    }
}


