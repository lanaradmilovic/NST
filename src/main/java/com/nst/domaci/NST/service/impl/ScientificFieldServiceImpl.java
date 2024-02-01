package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.ScientificFieldConverter;
import com.nst.domaci.NST.dto.ScientificFieldDto;
import com.nst.domaci.NST.entity.ScientificField;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.ScientificFieldRepository;
import com.nst.domaci.NST.service.ScientificFieldService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScientificFieldServiceImpl implements ScientificFieldService {
    private final ScientificFieldRepository scientificFieldRepository;
    private final ScientificFieldConverter scientificFieldConverter;

    public ScientificFieldServiceImpl(ScientificFieldRepository scientificFieldRepository, ScientificFieldConverter scientificFieldConverter) {
        this.scientificFieldRepository = scientificFieldRepository;
        this.scientificFieldConverter = scientificFieldConverter;
    }

    @Override
    public List<ScientificFieldDto> findAll() {
        return scientificFieldRepository
                .findAll()
                .stream().map(entity -> scientificFieldConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public ScientificFieldDto findById(Long id) throws ResourceNotFoundException {
        Optional<ScientificField> result = scientificFieldRepository.findById(id);
        if (result.isPresent()) {
            ScientificField ScientificField = result.get();
            return scientificFieldConverter.toDto(ScientificField);
        } else {
            throw new ResourceNotFoundException("Scientific field with ID = " + id + " does not exist.");
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        findById(id);
        scientificFieldRepository.deleteById(id);
    }

    @Override
    public ScientificFieldDto save(ScientificFieldDto ScientificFieldDto) throws EntityAlreadyExistsException {
        Optional<ScientificField> result = scientificFieldRepository.findByName(ScientificFieldDto.getName());
        if (result.isPresent()){
            throw new EntityAlreadyExistsException("Scientific field with name = " + ScientificFieldDto.getName() + " already exists.");
        }
        else {
            ScientificField scientificField = new ScientificField(ScientificFieldDto.getId(), ScientificFieldDto.getName());
            scientificField = scientificFieldRepository.save(scientificField);
            return scientificFieldConverter.toDto(scientificField);
        }
    }

    @Override
    public ScientificFieldDto update(ScientificFieldDto scientificFieldDto) throws ResourceNotFoundException {
        Optional<ScientificField> result = scientificFieldRepository.findById(scientificFieldDto.getId());
        if (result.isPresent()){
            ScientificField scientificField = result.get();
            scientificField.setName(scientificFieldDto.getName());
            scientificField = scientificFieldRepository.save(scientificField);
            return scientificFieldConverter.toDto(scientificField);
        }
        throw new ResourceNotFoundException("Scientific field with ID = " + scientificFieldDto.getId() + " does not exist.");

    }
}
