package com.nst.domaci.NST.converter;

import com.nst.domaci.NST.converter.impl.AcademicTitleConverter;
import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.entity.AcademicTitle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AcademicTitleConverterTests {
    @Autowired
    private AcademicTitleConverter academicTitleConverter;

    @Test
    void testToDto() {
        AcademicTitle academicTitle = AcademicTitle.builder()
                .id(1L)
                .name("Full professor")
                .build();

        AcademicTitleDto academicTitleDto = academicTitleConverter.toDto(academicTitle);

        Assertions.assertEquals(academicTitle.getId(), academicTitleDto.getId());
        Assertions.assertEquals(academicTitle.getName(), academicTitleDto.getName());
    }

    @Test
    void testToEntity() {
        AcademicTitleDto academicTitleDto = AcademicTitleDto.builder()
                .id(1L)
                .name("Full professor")
                .build();

        AcademicTitle academicTitle = academicTitleConverter.toEntity(academicTitleDto);

        Assertions.assertNull(academicTitle.getId()); // Id is not set in dto toEntity conversion
        Assertions.assertEquals(academicTitleDto.getName(), academicTitle.getName());
    }
}
