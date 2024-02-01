package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.EducationTitleDto;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;

import java.util.List;

public interface EducationTitleService {
    List<EducationTitleDto> findAll();
    EducationTitleDto findById(Long id) throws ResourceNotFoundException;
    void delete(Long id) throws ResourceNotFoundException;
    EducationTitleDto save(EducationTitleDto educationTitleDto) throws EntityAlreadyExistsException;
    EducationTitleDto update(EducationTitleDto educationTitleDto) throws ResourceNotFoundException;
}
