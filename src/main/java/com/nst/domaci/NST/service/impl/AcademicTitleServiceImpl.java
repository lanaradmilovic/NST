package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.AcademicTitleConverter;
import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.entity.AcademicTitle;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.AcademicTitleRepository;
import com.nst.domaci.NST.service.AcademicTitleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AcademicTitleServiceImpl implements AcademicTitleService {
    private final AcademicTitleRepository academicTitleRepository;
    private final AcademicTitleConverter academicTitleConverter;

    public AcademicTitleServiceImpl(AcademicTitleRepository academicTitleRepository, AcademicTitleConverter academicTitleConverter) {
        this.academicTitleRepository = academicTitleRepository;
        this.academicTitleConverter = academicTitleConverter;
    }

    @Override
    public List<AcademicTitleDto> findAll() {
        return academicTitleRepository
                .findAll()
                .stream().map(entity -> academicTitleConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public AcademicTitleDto findById(Long id) throws ResourceNotFoundException {
        Optional<AcademicTitle> result = academicTitleRepository.findById(id);
        if (result.isPresent()) {
            AcademicTitle academicTitle = result.get();
            return academicTitleConverter.toDto(academicTitle);
        } else {
            throw new ResourceNotFoundException("Academic title with ID = " + id + " does not exist.");
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        findById(id);
        academicTitleRepository.deleteById(id);
    }

    @Override
    public AcademicTitleDto save(AcademicTitleDto academicTitleDto) throws EntityAlreadyExistsException {
        Optional<AcademicTitle> result = academicTitleRepository.findByName(academicTitleDto.getName());
        if (result.isPresent()){
            throw new EntityAlreadyExistsException("Academic title with name = " + academicTitleDto.getName() + " already exists.");
        }
        else {
            AcademicTitle academicTitle = academicTitleConverter.toEntity(academicTitleDto);
            academicTitle = academicTitleRepository.save(academicTitle);
            return academicTitleConverter.toDto(academicTitle);
        }
    }

    @Override
    public AcademicTitleDto update(AcademicTitleDto academicTitleDto) throws ResourceNotFoundException {
        Optional<AcademicTitle> result = academicTitleRepository.findById(academicTitleDto.getId());
        if (result.isPresent()){
            AcademicTitle academicTitle = result.get();
            academicTitle.setName(academicTitleDto.getName());
            academicTitle=  academicTitleRepository.save(academicTitle);
            return academicTitleConverter.toDto(academicTitle);
        }
        throw new ResourceNotFoundException("Education title with ID = " + academicTitleDto.getId() + " does not exist.");

    }
}
