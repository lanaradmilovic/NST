package com.nst.domaci.NST.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LeaderHistoryDto {
    private Long id;
    private Long departmentId;
    private Long memberId;
    private LocalDate startDate;
    private LocalDate endDate;
}
