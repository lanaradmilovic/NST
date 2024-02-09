package com.nst.domaci.NST.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectDto {
    private Long id;
    private String name;
    private int espb;
    private Long departmentId;
}
