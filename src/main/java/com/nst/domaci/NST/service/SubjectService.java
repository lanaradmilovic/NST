package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    List<SubjectDto> findAll();

    List<SubjectDto> findAllByDepartmentId(Long departmentId);


    SubjectDto findById(Long id);

    SubjectDto save(SubjectDto subjectDto);

    SubjectDto update(SubjectDto subjectDto);

    void delete(Long id);
}
