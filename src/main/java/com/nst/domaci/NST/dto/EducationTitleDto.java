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
public class EducationTitleDto {
    private Long id;
    @NotEmpty(message = "Education title name is mandatory")
    @Size(min = 2, max = 35, message = "Education title name is between 2 and 35 characters long")
    private String name;
}
