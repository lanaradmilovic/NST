package com.nst.domaci.NST.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDto {
    private Long id;

    @NotNull
    @NotEmpty(message = "Department name is mandatory")
    @Size(min = 2, max = 10, message = "Department name must be between 2 and 10 characters long")
    private String name;
    @NotNull
    @NotEmpty(message = "Department short name is mandatory")
    @Size(min = 2, max = 10, message = "Department short name must be between 1 and 6 characters long")
    private String shortName;

}

