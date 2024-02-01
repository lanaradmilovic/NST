package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.EducationTitleConverter;
import com.nst.domaci.NST.dto.EducationTitleDto;
import com.nst.domaci.NST.entity.EducationTitle;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.EducationTitleRepository;
import com.nst.domaci.NST.service.EducationTitleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EducationTitleServiceImpl implements EducationTitleService {
    private final EducationTitleRepository educationTitleRepository;
    private final EducationTitleConverter educationTitleConverter;

    public EducationTitleServiceImpl(EducationTitleRepository educationTitleRepository, EducationTitleConverter educationTitleConverter) {
        this.educationTitleRepository = educationTitleRepository;
        this.educationTitleConverter = educationTitleConverter;
    }

    @Override
    public List<EducationTitleDto> findAll() {
        return educationTitleRepository
                .findAll()
                .stream().map(entity -> educationTitleConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public EducationTitleDto findById(Long id) throws ResourceNotFoundException{
        Optional<EducationTitle> result = educationTitleRepository.findById(id);
        if (result.isPresent()){
            EducationTitle educationTitle = result.get();
            return educationTitleConverter.toDto(educationTitle);
        }
        else {
            throw new ResourceNotFoundException("Education title with ID = " + id + " does not exist.");
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        findById(id);
        educationTitleRepository.deleteById(id);
    }

    @Override
    public EducationTitleDto save(EducationTitleDto educationTitleDto) {
        Optional<EducationTitle> result = educationTitleRepository.findByName(educationTitleDto.getName());
        if (result.isPresent()){
        throw new EntityAlreadyExistsException("Education title with name = " + educationTitleDto.getName() + " already exists.");
        }
        else {
            EducationTitle educationTitle = educationTitleConverter.toEntity(educationTitleDto);
            educationTitle = educationTitleRepository.save(educationTitle);
            return educationTitleConverter.toDto(educationTitle);
        }
    }

    @Override
    public EducationTitleDto update(EducationTitleDto educationTitleDto) {
        Optional<EducationTitle> result = educationTitleRepository.findById(educationTitleDto.getId());
        if (result.isPresent()){
            EducationTitle educationTitle = result.get();
            educationTitle.setName(educationTitleDto.getName());
            educationTitle = educationTitleRepository.save(educationTitle);
            return educationTitleConverter.toDto(educationTitle);
        }
        else {
            throw new ResourceNotFoundException("Education title with ID = " + educationTitleDto.getId() + " does not exist.");
        }
    }
}
