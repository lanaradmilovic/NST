package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.LectureScheduleConverter;
import com.nst.domaci.NST.dto.LectureScheduleDto;
import com.nst.domaci.NST.entity.LectureSchedule;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.LectureScheduleRepository;
import com.nst.domaci.NST.service.LectureScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureScheduleServiceImpl implements LectureScheduleService {
    private final LectureScheduleRepository lectureScheduleRepository;
    private final LectureScheduleConverter lectureScheduleConverter;

    public LectureScheduleServiceImpl(LectureScheduleRepository lectureScheduleRepository, LectureScheduleConverter lectureScheduleConverter) {
        this.lectureScheduleRepository = lectureScheduleRepository;
        this.lectureScheduleConverter = lectureScheduleConverter;
    }

    @Override
    public List<LectureScheduleDto> findAll() {
        return lectureScheduleRepository.findAll().stream()
                .map(entity -> lectureScheduleConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public LectureScheduleDto findById(Long id) throws ResourceNotFoundException {
        LectureSchedule lectureSchedule = lectureScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LectureSchedule with ID " + id + " not found."));
        return lectureScheduleConverter.toDto(lectureSchedule);
    }

    @Override
    public List<LectureScheduleDto> findAllBySubjectIdAndYear(Long subjectId, int year) {
        return lectureScheduleRepository.findAllBySubjectIdAndYear(subjectId, year).stream()
                .map(entity -> lectureScheduleConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        findById(id);
        lectureScheduleRepository.deleteById(id);
    }

}
