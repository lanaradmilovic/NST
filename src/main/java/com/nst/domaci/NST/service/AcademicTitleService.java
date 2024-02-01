package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;

import java.util.List;

public interface AcademicTitleService {
    List<AcademicTitleDto> findAll();
    AcademicTitleDto findById(Long id) throws ResourceNotFoundException;
    void delete(Long id) throws ResourceNotFoundException;
    AcademicTitleDto save(AcademicTitleDto academicTitleDto) throws EntityAlreadyExistsException;
    AcademicTitleDto update(AcademicTitleDto academicTitleDto) throws ResourceNotFoundException;
}
