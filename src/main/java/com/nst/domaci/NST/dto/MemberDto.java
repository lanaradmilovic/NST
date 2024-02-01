package com.nst.domaci.NST.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Member's first name is mandatory.")
    @Size(min = 2, max = 25, message = "Member's name must be between 2 and 25 characters long.")
    private String firstName;

    @NotEmpty(message = "Member's last name name is mandatory.")
    @Size(min = 2, max = 25, message = "Member's last name must be between 2 and 25 characters long.")
    private String lastName;
}
