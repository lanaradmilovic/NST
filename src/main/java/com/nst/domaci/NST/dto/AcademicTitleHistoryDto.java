package com.nst.domaci.NST.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicTitleHistoryDto {
    private Long id;

    private Long memberId;

    private Long academicTitleId;

    private Long scientificFieldId;

    private LocalDate startDate;

    private LocalDate endDate;
}
