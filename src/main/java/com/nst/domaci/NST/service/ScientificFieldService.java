package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.ScientificFieldDto;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;

import java.util.List;

public interface ScientificFieldService {
    List<ScientificFieldDto> findAll();
    ScientificFieldDto findById(Long id) throws ResourceNotFoundException;
    void delete(Long id) throws ResourceNotFoundException;
    ScientificFieldDto save(ScientificFieldDto academicTitleDto) throws EntityAlreadyExistsException;
    ScientificFieldDto update(ScientificFieldDto academicTitleDto) throws ResourceNotFoundException;
}
