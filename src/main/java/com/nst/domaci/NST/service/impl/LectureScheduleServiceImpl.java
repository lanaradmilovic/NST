package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.LectureScheduleConverter;
import com.nst.domaci.NST.dto.LectureScheduleDto;
import com.nst.domaci.NST.entity.LectureSchedule;
import com.nst.domaci.NST.entity.Subject;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.LectureRepository;
import com.nst.domaci.NST.repository.LectureScheduleRepository;
import com.nst.domaci.NST.repository.SubjectRepository;
import com.nst.domaci.NST.service.LectureScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureScheduleServiceImpl implements LectureScheduleService {
    private final LectureScheduleRepository lectureScheduleRepository;
    private final LectureScheduleConverter lectureScheduleConverter;
    private final LectureRepository lectureRepository;
    private final SubjectRepository subjectRepository;


    public LectureScheduleServiceImpl(LectureScheduleRepository lectureScheduleRepository, LectureScheduleConverter lectureScheduleConverter, LectureRepository lectureRepository, SubjectRepository subjectRepository) {
        this.lectureScheduleRepository = lectureScheduleRepository;
        this.lectureScheduleConverter = lectureScheduleConverter;
        this.lectureRepository = lectureRepository;
        this.subjectRepository = subjectRepository;
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
    public List<LectureSchedule> findAllBySubjectIdAndYear(Long subjectId, int year) {
        return lectureScheduleRepository.findAllBySubjectIdAndYear(subjectId, year);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        findById(id);
        lectureScheduleRepository.deleteById(id);
    }

    @Override
    public LectureScheduleDto save(LectureScheduleDto lectureSchedule) throws EntityAlreadyExistsException {
        // Check if subjectId is present
        if (lectureSchedule.getSubjectId() == null) {
            throw new IllegalArgumentException("Subject ID cannot be null.");
        }

        // Check if the lecture schedule already exists
        Optional<LectureSchedule> existingSchedule = lectureScheduleRepository.findById(lectureSchedule.getId());
        if (existingSchedule.isPresent()) {
            throw new EntityAlreadyExistsException("Lecture schedule with ID = " + lectureSchedule.getId() + " already exists.");
        }

        // Fetch the subject by ID
        Subject subject = subjectRepository.findById(lectureSchedule.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject with ID " + lectureSchedule.getSubjectId() + " not found."));

        // Set the subject in the lectureSchedule entity
        LectureSchedule lectureScheduleEntity = lectureScheduleConverter.toEntity(lectureSchedule);
        lectureScheduleEntity.setSubject(subject);

        // Save the lecture schedule
        LectureSchedule saved = lectureScheduleRepository.save(lectureScheduleEntity);
        return lectureScheduleConverter.toDto(saved);
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
