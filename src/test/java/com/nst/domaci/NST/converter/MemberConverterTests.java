package com.nst.domaci.NST.converter;

import com.nst.domaci.NST.converter.impl.MemberConverter;
import com.nst.domaci.NST.dto.MemberDto;
import com.nst.domaci.NST.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class MemberConverterTests {
    @Autowired
    private MemberConverter memberConverter;

    @Test
    public void testToDto() {
        Member member = Member.builder()
                .id(1L)
                .department(Department.builder().id(2L).build())
                .educationTitle(EducationTitle.builder().id(3L).build())
                .academicTitle(AcademicTitle.builder().id(4L).build())
                .scientificField(ScientificField.builder().id(5L).build())
                .firstName("John")
                .lastName("Doe")
                .build();

        MemberDto memberDto = memberConverter.toDto(member);

        Assertions.assertEquals(member.getId(), memberDto.getId());
        Assertions.assertEquals(member.getDepartment().getId(), memberDto.getDepartmentId());
        Assertions.assertEquals(member.getEducationTitle().getId(), memberDto.getEducationTitleId());
        Assertions.assertEquals(member.getAcademicTitle().getId(), memberDto.getAcademicTitleId());
        Assertions.assertEquals(member.getScientificField().getId(), memberDto.getScientificFieldId());
        Assertions.assertEquals(member.getFirstName(), memberDto.getFirstName());
        Assertions.assertEquals(member.getLastName(), memberDto.getLastName());
    }

    @Test
    public void testToEntity() {
        MemberDto memberDto = MemberDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .build();

        Member member = memberConverter.toEntity(memberDto);

        Assertions.assertNull(member.getId());
        Assertions.assertEquals(memberDto.getFirstName(), member.getFirstName());
        Assertions.assertEquals(memberDto.getLastName(), member.getLastName());
    }
}
