package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.SubjectDto;
import com.nst.domaci.NST.entity.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectConverter implements DtoEntityConverter<SubjectDto, Subject> {
    @Override
    public SubjectDto toDto(Subject subject) {
        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .departmentId(subject.getDepartment().getId())
                .espb(subject.getEspb())
                .build();
    }

    @Override
    public Subject toEntity(SubjectDto subjectDto) {
        return Subject.builder()
                .name(subjectDto.getName())
                .espb(subjectDto.getEspb())
                .build();
    }
}

