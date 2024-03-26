package com.nst.domaci.NST.converter;

import com.nst.domaci.NST.converter.impl.EngagementConverter;
import com.nst.domaci.NST.dto.EngagementDto;
import com.nst.domaci.NST.entity.Engagement;
import com.nst.domaci.NST.entity.Member;
import com.nst.domaci.NST.entity.Subject;
import com.nst.domaci.NST.entity.form.TeachingForm;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class EngagementConverterTests {
    @Autowired
    private EngagementConverter engagementConverter;
    private static Set<TeachingForm> teachingForms;

    @BeforeAll
    public static void setUp() {
        teachingForms = new HashSet<>();
        teachingForms.add(TeachingForm.LECTURE);
        teachingForms.add(TeachingForm.LAB_EXERCISE);
    }

    @Test
    public void testToDto() {
        Engagement engagement = Engagement.builder()
                .id(1L)
                .member(Member.builder().id(2L).build()) // Mock member
                .subject(Subject.builder().id(3L).build()) // Mock subject
                .engagementYear(2022L)
                .teachingForm(teachingForms)
                .build();

        EngagementDto engagementDto = engagementConverter.toDto(engagement);

        Assertions.assertEquals(engagement.getId(), engagementDto.getId());
        Assertions.assertEquals(engagement.getMember().getId(), engagementDto.getMemberId());
        Assertions.assertEquals(engagement.getSubject().getId(), engagementDto.getSubjectId());
        Assertions.assertEquals(engagement.getEngagementYear(), engagementDto.getEngagementYear());
        Assertions.assertEquals(engagement.getTeachingForm(), engagementDto.getTeachingForm());
    }

    @Test
    public void testToEntity() {
        EngagementDto engagementDto = EngagementDto.builder()
                .engagementYear(2023L)
                .teachingForm(teachingForms)
                .build();

        Engagement engagement = engagementConverter.toEntity(engagementDto);

        Assertions.assertNull(engagement.getId());
        Assertions.assertEquals(engagementDto.getEngagementYear(), engagement.getEngagementYear());
        Assertions.assertEquals(engagementDto.getTeachingForm(), engagement.getTeachingForm());
    }
}
