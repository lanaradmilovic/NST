package com.nst.domaci.NST.converter;

import com.nst.domaci.NST.converter.impl.SubjectConverter;
import com.nst.domaci.NST.dto.SubjectDto;
import com.nst.domaci.NST.entity.Department;
import com.nst.domaci.NST.entity.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SubjectConverterTests {
    @Autowired
    private SubjectConverter subjectConverter;

    @Test
    public void testToDto() {
        Subject subject = Subject.builder()
                .id(1L)
                .name("Math")
                .department(Department.builder().id(2L).build())
                .espb(6)
                .build();

        SubjectDto subjectDto = subjectConverter.toDto(subject);

        Assertions.assertEquals(subject.getId(), subjectDto.getId());
        Assertions.assertEquals(subject.getName(), subjectDto.getName());
        Assertions.assertEquals(subject.getDepartment().getId(), subjectDto.getDepartmentId());
        Assertions.assertEquals(subject.getEspb(), subjectDto.getEspb());
    }

    @Test
    public void testToEntity() {
        SubjectDto subjectDto = SubjectDto.builder()
                .name("Physics")
                .departmentId(3L)
                .espb(5)
                .build();

        Subject subject = subjectConverter.toEntity(subjectDto);

        Assertions.assertNull(subject.getId());
        Assertions.assertEquals(subjectDto.getName(), subject.getName());
        // Ensure department is not set as it's not part of SubjectDto
        Assertions.assertNull(subject.getDepartment());
        Assertions.assertEquals(subjectDto.getEspb(), subject.getEspb());
    }
}
