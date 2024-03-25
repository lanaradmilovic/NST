package com.nst.domaci.NST.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AcademicTitleDto {
@EqualsAndHashCode.Include
    private Long id;
    private String name;
}
