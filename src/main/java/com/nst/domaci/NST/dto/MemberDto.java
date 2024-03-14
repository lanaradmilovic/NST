package com.nst.domaci.NST.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long id;

    private Long departmentId;

    private Long academicTitleId;

    private Long educationTitleId;

    private Long scientificFieldId;
    private String firstName;

    private String lastName;
}
