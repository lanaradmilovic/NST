package com.nst.domaci.NST.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectDto {
    private Long id;
    @NotEmpty(message = "Subject name is mandatory")
    @Size(min = 2, max = 10, message = "Subject name is between 2 and 10 characters long")
    private String name;
    private String espb;
    private Long departmentId;
}
