package com.nst.domaci.NST.dto;

import com.nst.domaci.NST.entity.form.TeachingForm;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@Builder
public class EngagementDto {
    private Long id;
    private Long engagementYear;
    private Long memberId;
    private Long subjectId;
    private Set<TeachingForm> teachingForm;
}
