package com.nst.domaci.NST.converter;

import com.nst.domaci.NST.converter.impl.LectureConverter;
import com.nst.domaci.NST.dto.LectureDto;
import com.nst.domaci.NST.entity.Engagement;
import com.nst.domaci.NST.entity.Lecture;
import com.nst.domaci.NST.entity.LectureSchedule;
import com.nst.domaci.NST.entity.form.TeachingForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class LectureConverterTests {
    @Autowired
    private LectureConverter lectureConverter;

    private Lecture lecture;

    @BeforeEach
    public void setUp() {
        lecture = Lecture.builder()
                .id(1L)
                .form(TeachingForm.LAB_EXERCISE)
                .dateTime(LocalDateTime.of(2024, 3, 25, 10, 0))
                .description("Introduction to Spring Boot")
                .lectureSchedule(LectureSchedule.builder().id(2L).build())
                .engagement(Engagement.builder().id(3L).build())
                .build();
    }

    @Test
    public void testToDto() {
        LectureDto lectureDto = lectureConverter.toDto(lecture);

        Assertions.assertEquals(lecture.getId(), lectureDto.getId());
        Assertions.assertEquals(lecture.getForm(), lectureDto.getForm());
        Assertions.assertEquals(lecture.getEngagement().getId(), lectureDto.getEngagementId());
        Assertions.assertEquals(lecture.getDateTime(), lectureDto.getDateTime());
        Assertions.assertEquals(lecture.getDescription(), lectureDto.getDescription());
        Assertions.assertEquals(lecture.getLectureSchedule().getId(), lectureDto.getLectureScheduleId());
    }

    @Test
    public void testToEntity() {
        LectureDto lectureDto = LectureDto.builder()
                .form(TeachingForm.LAB_EXERCISE)
                .dateTime(LocalDateTime.of(2024, 3, 26, 14, 0))
                .description("Advanced Spring Boot")
                .build();

        Lecture convertedLecture = lectureConverter.toEntity(lectureDto);

        Assertions.assertNull(convertedLecture.getId());
        Assertions.assertEquals(lectureDto.getForm(), convertedLecture.getForm());
        Assertions.assertEquals(lectureDto.getDateTime(), convertedLecture.getDateTime());
        Assertions.assertEquals(lectureDto.getDescription(), convertedLecture.getDescription());
    }
}
