package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.DepartmentConverter;
import com.nst.domaci.NST.converter.impl.SubjectConverter;
import com.nst.domaci.NST.dto.SubjectDto;
import com.nst.domaci.NST.entity.Department;
import com.nst.domaci.NST.entity.Subject;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.SubjectRepository;
import com.nst.domaci.NST.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectConverter subjectConverter;
    private final SubjectRepository subjectRepository;
    private final DepartmentServiceImpl departmentService;
    private final DepartmentConverter departmentConverter;

    public SubjectServiceImpl(SubjectConverter subjectConverter, SubjectRepository subjectRepository, DepartmentServiceImpl departmentService, DepartmentConverter departmentConverter) {
        this.subjectConverter = subjectConverter;
        this.subjectRepository = subjectRepository;
        this.departmentService = departmentService;
        this.departmentConverter = departmentConverter;
    }

    @Override
    public List<SubjectDto> findAll() {
        return subjectRepository.findAll()
                .stream().map(entity -> subjectConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectDto> findAllByDepartmentId(Long departmentId) {
        return subjectRepository.findAllByDepartmentId(departmentId)
                .stream().map(entity -> subjectConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public SubjectDto findById(Long id) {
        Optional<Subject> result = subjectRepository.findById(id);
        if (result.isPresent()) {
            return subjectConverter.toDto(result.get());
        } else {
            throw new ResourceNotFoundException("Subject with ID = " + id + " does not exist.");
        }
    }

    @Override
    public SubjectDto save(SubjectDto subjectDto) {
        Subject subject = subjectConverter.toEntity(subjectDto);
        Department department = departmentConverter.toEntity(departmentService.findById(subjectDto.getDepartmentId()));
        subject.setDepartment(department);
        subject = subjectRepository.save(subject);
        return subjectConverter.toDto(subject);
    }

    @Override
    public SubjectDto update(SubjectDto subjectDto) {
        Optional<Subject> result = subjectRepository.findById(subjectDto.getId());
        if (result.isPresent()) {
            Subject subject = result.get();
            Department department = departmentConverter.toEntity(departmentService.findById(subjectDto.getDepartmentId()));
            subject.setDepartment(department);
            subject.setEspb(subjectDto.getEspb());
            subject.setName(subjectDto.getName());
            subject = subjectRepository.save(subject);
            return subjectConverter.toDto(subject);

        } else {
            throw new ResourceNotFoundException("Subject with ID = " + subjectDto.getId() + " does not exist.");

        }
    }

    @Override
    public void delete(Long id) {
        findById(id);
        subjectRepository.deleteById(id);
    }
}
