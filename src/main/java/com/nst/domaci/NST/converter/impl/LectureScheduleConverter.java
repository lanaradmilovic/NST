package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.LectureScheduleDto;
import com.nst.domaci.NST.entity.LectureSchedule;
import org.springframework.stereotype.Component;

@Component
public class LectureScheduleConverter implements DtoEntityConverter<LectureScheduleDto, LectureSchedule> {
        @Override
        public LectureScheduleDto toDto(LectureSchedule lectureSchedule) {
            return LectureScheduleDto.builder()
                    .id(lectureSchedule.getId())
                    .subjectId(lectureSchedule.getSubject().getId())
                    .year(lectureSchedule.getYear())
                    .build();
        }

        @Override
        public LectureSchedule toEntity(LectureScheduleDto lectureScheduleDto) {
            return LectureSchedule.builder()
                    .year(lectureScheduleDto.getYear())
                    .build();
        }
}
