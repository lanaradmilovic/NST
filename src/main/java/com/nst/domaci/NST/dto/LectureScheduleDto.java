package com.nst.domaci.NST.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LectureScheduleDto {
    private Long id;
    private Long subjectId;
    private int year;
}
