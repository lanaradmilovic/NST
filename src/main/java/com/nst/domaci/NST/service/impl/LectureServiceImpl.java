package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.LectureConverter;
import com.nst.domaci.NST.dto.LectureDto;
import com.nst.domaci.NST.entity.Engagement;
import com.nst.domaci.NST.entity.Lecture;
import com.nst.domaci.NST.entity.LectureSchedule;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.EngagementRepository;
import com.nst.domaci.NST.repository.LectureRepository;
import com.nst.domaci.NST.repository.LectureScheduleRepository;
import com.nst.domaci.NST.service.LectureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    private final LectureConverter lectureConverter;
    private final EngagementRepository engagementRepository;
    private final LectureScheduleRepository lectureScheduleRepository;

    public LectureServiceImpl(LectureRepository lectureRepository, LectureConverter lectureConverter, EngagementRepository engagementRepository, LectureScheduleRepository lectureScheduleRepository) {
        this.lectureRepository = lectureRepository;
        this.lectureConverter = lectureConverter;
        this.engagementRepository = engagementRepository;
        this.lectureScheduleRepository = lectureScheduleRepository;
    }

    @Override
    public List<LectureDto> findAll() {
        return lectureRepository.findAll().stream()
                .map(entity -> lectureConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public LectureDto findById(Long id) throws ResourceNotFoundException {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecture with ID " + id + " not found."));
        return lectureConverter.toDto(lecture);
    }

    @Override
    public List<LectureDto> findAllByEngagementId(Long engagementId) {
        return lectureRepository.findAllByEngagementId(engagementId).stream()
                .map(entity -> lectureConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<LectureDto> findAllByEngagementMemberIdAndEngagementYear(Long memberId, Long year) {
        return lectureRepository.findAllByEngagementMemberIdAndEngagementYear(memberId, year).stream()
                .map(entity -> lectureConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<LectureDto> findAllByEngagementSubjectIdAndEngagementYear(Long subjectId, Long year) {
        return lectureRepository.findAllByEngagementSubjectIdAndEngagementYear(subjectId, year).stream()
                .map(entity -> lectureConverter.toDto(entity))
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        findById(id);
        lectureRepository.deleteById(id);
    }

    @Override
    public LectureDto save(LectureDto lectureDto) {
        Lecture lecture = lectureConverter.toEntity(lectureDto);
        Engagement engagement = engagementRepository.findById(lectureDto.getEngagementId())
                .orElseThrow(() -> new ResourceNotFoundException("Engagement with ID = " + lectureDto.getEngagementId() + " does not exist."));
        LectureSchedule lectureSchedule = lectureScheduleRepository.findById(lectureDto.getLectureScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecture schedule with ID = " + lectureDto.getLectureScheduleId() + " does not exist."));
        lecture.setLectureSchedule(lectureSchedule);
        lecture.setEngagement(engagement);
        Lecture savedLecture = lectureRepository.save(lecture);
        return lectureConverter.toDto(savedLecture);
    }

    @Override
    public LectureDto update(LectureDto lectureDto) throws ResourceNotFoundException {
        Optional<Lecture> result = lectureRepository.findById(lectureDto.getId());
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Lecture with ID = " + lectureDto.getId() + " not found");
        }
        Lecture lecture = result.get();
        Engagement engagement = engagementRepository.findById(lectureDto.getEngagementId())
                .orElseThrow(() -> new ResourceNotFoundException("Engagement with ID = " + lectureDto.getEngagementId() + " does not exist."));
        LectureSchedule lectureSchedule = lectureScheduleRepository.findById(lectureDto.getLectureScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Lecture schedule with ID = " + lectureDto.getLectureScheduleId() + " does not exist."));
        lecture.setDateTime(lectureDto.getDateTime());
        lecture.setForm(lectureDto.getForm());
        lecture.setDescription(lectureDto.getDescription());
        lecture.setLectureSchedule(lectureSchedule);
        lecture.setEngagement(engagement);
        Lecture updatedLecture = lectureRepository.save(lecture);
        return lectureConverter.toDto(updatedLecture);
    }
}

