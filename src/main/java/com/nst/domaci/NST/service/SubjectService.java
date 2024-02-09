package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.SubjectDto;
import com.nst.domaci.NST.entity.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findAll();

    List<SubjectDto> findAllByDepartmentId(Long departmentId);


    Subject findById(Long id);

    SubjectDto save(SubjectDto subjectDto);

    SubjectDto update(SubjectDto subjectDto);

    void delete(Long id);
}
