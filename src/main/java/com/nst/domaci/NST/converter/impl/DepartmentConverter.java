package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.DepartmentDto;
import com.nst.domaci.NST.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter implements DtoEntityConverter<DepartmentDto, Department> {
    @Override
    public DepartmentDto toDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .shortName(department.getShortName())
                .build();
    }

    @Override
    public Department toEntity(DepartmentDto departmentDTO) {
        return Department.builder()
                .name(departmentDTO.getName())
                .shortName(departmentDTO.getShortName())
                .build();
    }
}

