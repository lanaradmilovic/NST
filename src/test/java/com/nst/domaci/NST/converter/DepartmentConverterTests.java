package com.nst.domaci.NST.converter;

import com.nst.domaci.NST.converter.impl.DepartmentConverter;
import com.nst.domaci.NST.dto.DepartmentDto;
import com.nst.domaci.NST.entity.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentConverterTests {
    @Autowired
    private DepartmentConverter departmentConverter;

    @Test
    public void testToDto() {
        Department department = Department.builder()
                .id(1L)
                .name("Engineering")
                .shortName("ENG")
                .build();

        DepartmentDto departmentDto = departmentConverter.toDto(department);

        Assertions.assertEquals(department.getId(), departmentDto.getId());
        Assertions.assertEquals(department.getName(), departmentDto.getName());
        Assertions.assertEquals(department.getShortName(), departmentDto.getShortName());
    }

    @Test
    public void testToEntity() {
        DepartmentDto departmentDto = DepartmentDto.builder()
                .name("Engineering")
                .shortName("ENG")
                .build();

        Department department = departmentConverter.toEntity(departmentDto);

        Assertions.assertNull(department.getId());
        Assertions.assertEquals(departmentDto.getName(), department.getName());
        Assertions.assertEquals(departmentDto.getShortName(), department.getShortName());
    }
}
