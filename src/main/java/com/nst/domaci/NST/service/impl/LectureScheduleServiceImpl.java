package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.LectureScheduleConverter;
import com.nst.domaci.NST.dto.LectureScheduleDto;
import com.nst.domaci.NST.entity.Lecture;
import com.nst.domaci.NST.entity.LectureSchedule;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.LectureRepository;
import com.nst.domaci.NST.repository.LectureScheduleRepository;
import com.nst.domaci.NST.service.LectureScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LectureScheduleServiceImpl implements LectureScheduleService {
    private final LectureScheduleRepository lectureScheduleRepository;
    private final LectureScheduleConverter lectureScheduleConverter;
    private final LectureRepository lectureRepository;


    public LectureScheduleServiceImpl(LectureScheduleRepository lectureScheduleRepository, LectureScheduleConverter lectureScheduleConverter, LectureRepository lectureRepository) {
        this.lectureScheduleRepository = lectureScheduleRepository;
        this.lectureScheduleConverter = lectureScheduleConverter;
        this.lectureRepository = lectureRepository;
    }

    @Override
    public List<LectureSchedule> findAll() {
        return lectureScheduleRepository.findAll();
    }

    @Override
    public LectureSchedule findById(Long id) throws ResourceNotFoundException {
        LectureSchedule lectureSchedule = lectureScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LectureSchedule with ID " + id + " not found."));
        return lectureSchedule;
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

//    @Override
//    public void addLectureToSchedule(Long lectureScheduleId, Long lectureId) {
//        Optional<LectureSchedule> optionalLectureSchedule = lectureScheduleRepository.findById(lectureScheduleId);
//        Optional<Lecture> optionalLecture = lectureRepository.findById(lectureId);
//
//        if (optionalLectureSchedule.isPresent() && optionalLecture.isPresent()) {
//            LectureSchedule lectureSchedule = optionalLectureSchedule.get();
//            Lecture lecture = optionalLecture.get();
//
//            lectureSchedule.getLectures().add(lecture);
//
//            lectureScheduleRepository.save(lectureSchedule);
//        } else {
//            throw new ResourceNotFoundException("Lecture Schedule or Lecture not found");
//        }
//    }

}
