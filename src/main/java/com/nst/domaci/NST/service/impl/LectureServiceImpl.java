package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.LectureConverter;
import com.nst.domaci.NST.dto.LectureDto;
import com.nst.domaci.NST.entity.Engagement;
import com.nst.domaci.NST.entity.Lecture;
import com.nst.domaci.NST.entity.LectureSchedule;
import com.nst.domaci.NST.entity.Subject;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.exception.SubjectMismatchException;
import com.nst.domaci.NST.exception.YearMismatch;
import com.nst.domaci.NST.repository.*;
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
    private final SubjectRepository subjectRepository;
    private final MemberRepository memberRepository;

    public LectureServiceImpl(LectureRepository lectureRepository, LectureConverter lectureConverter, EngagementRepository engagementRepository, LectureScheduleRepository lectureScheduleRepository, SubjectRepository subjectRepository, MemberRepository memberRepository) {
        this.lectureRepository = lectureRepository;
        this.lectureConverter = lectureConverter;
        this.engagementRepository = engagementRepository;
        this.lectureScheduleRepository = lectureScheduleRepository;
        this.subjectRepository = subjectRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Lecture> findAll() {
        List<Lecture> lectures = lectureRepository.findAll();
        return lectures;
    }

    @Override
    public Lecture findById(Long id) throws ResourceNotFoundException {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecture with ID " + id + " not found."));
        return lecture;
    }

    @Override
    public List<Lecture> findAllByEngagementId(Long engagementId) {
        engagementRepository.findById(engagementId)
                .orElseThrow(() -> new ResourceNotFoundException("Engagement with ID = " + engagementId + " not found."));
        return lectureRepository.findAllByEngagementId(engagementId);
    }

    @Override
    public List<Lecture> findAllByEngagementMemberIdAndEngagementYear(Long memberId, Long year) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with ID = " + memberId + " not found."));
        return lectureRepository.findAllByEngagementMemberIdAndEngagementYear(memberId, year);

    }

    @Override
    public List<Lecture> findAllByEngagementSubjectIdAndEngagementYear(Long subjectId, Long year) {
        subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with ID = " + subjectId + " not found."));
        return lectureRepository.findAllByEngagementSubjectIdAndEngagementYear(subjectId, year);
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
        if (!engagement.getSubject().getId().equals(lectureSchedule.getSubject().getId())) {
            throw new SubjectMismatchException(
                    "Subject mismatch. Engagement's subject and Lecture Schedule's subject have different IDs. " +
                            "Engagement's subject ID: " + engagement.getSubject().getId() + ", " +
                            "Lecture Schedule's subject ID: " + lectureSchedule.getSubject().getId());

        }
        if (engagement.getYear() != lectureSchedule.getYear()) {
            throw new YearMismatch("Year mismatch. Engagement's year and Lecture Schedule's year have different values.");
        }


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

