package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.LectureDto;
import com.nst.domaci.NST.entity.Lecture;

public class LectureConverter implements DtoEntityConverter<LectureDto, Lecture> {

    @Override
    public LectureDto toDto(Lecture lecture) {
        return LectureDto.builder()
                .id(lecture.getId())
                .form(lecture.getForm())
                .engagementId(lecture.getEngagement().getId())
                .dateTime(lecture.getDateTime())
                .description(lecture.getDescription())
                .lectureScheduleId(lecture.getLectureSchedule().getId())
                .build();
    }

    @Override
    public Lecture toEntity(LectureDto lectureDto) {
        Lecture lecture = Lecture.builder()
                .form(lectureDto.getForm())
                .dateTime(lectureDto.getDateTime())
                .description(lectureDto.getDescription())
                .build();

        return lecture;
    }
}

