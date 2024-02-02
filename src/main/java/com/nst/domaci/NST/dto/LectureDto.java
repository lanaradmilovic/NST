package com.nst.domaci.NST.dto;

import com.nst.domaci.NST.entity.form.TeachingForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureDto {

    private Long id;
    private TeachingForm form;
    private Long engagementId;
    private LocalDateTime dateTime;
    private String description;
    private Long lectureScheduleId;

}

