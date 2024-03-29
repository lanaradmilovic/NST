package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.EngagementConverter;
import com.nst.domaci.NST.dto.EngagementDto;
import com.nst.domaci.NST.entity.*;
import com.nst.domaci.NST.exception.DepartmentMismatchException;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.exception.YearMismatch;
import com.nst.domaci.NST.repository.EngagementRepository;
import com.nst.domaci.NST.repository.LectureRepository;
import com.nst.domaci.NST.repository.MemberRepository;
import com.nst.domaci.NST.repository.SubjectRepository;
import com.nst.domaci.NST.service.EngagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EngagementServiceImpl implements EngagementService {
    private final EngagementRepository engagementRepository;
    private final EngagementConverter engagementConverter;
    private final MemberRepository memberRepository;
    private final SubjectRepository subjectRepository;
    private final LectureRepository lectureRepository;

    public EngagementServiceImpl(EngagementRepository engagementRepository, EngagementConverter engagementConverter, MemberRepository memberRepository, SubjectRepository subjectRepository, LectureRepository lectureRepository) {
        this.engagementRepository = engagementRepository;
        this.engagementConverter = engagementConverter;
        this.memberRepository = memberRepository;
        this.subjectRepository = subjectRepository;
        this.lectureRepository = lectureRepository;
    }

    @Override
    public List<Engagement> findAll() {
        return engagementRepository.findAll();
    }

    @Override
    public List<EngagementDto> findAllByMemberId(Long memberId) throws ResourceNotFoundException {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + memberId + " not found."));
        return engagementRepository.findAllByMemberId(memberId)
                .stream().map(entity -> engagementConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<EngagementDto> findAllByMemberIdAndSubjectIdOrderByIdDesc(Long memberId, Long subjectId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + memberId + " not found."));
        subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with ID " + subjectId + " not found."));
        return engagementRepository.findAllByMemberIdAndSubjectIdOrderByIdDesc(memberId, subjectId)
                .stream().map(entity -> engagementConverter.toDto(entity))
                .collect(Collectors.toList());

    }

    @Override
    public List<EngagementDto> findAllByMemberIdAndEngagementYear(Long memberId, int year) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + memberId + " not found."));
        return engagementRepository.findAllByMemberIdAndEngagementYear(memberId, year)
                .stream().map(entity -> engagementConverter.toDto(entity))
                .collect(Collectors.toList());

    }

    @Override
    public List<EngagementDto> findAllBySubjectIdAndEngagementYear(Long subjectId, int year) {
        subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with ID " + subjectId + " not found."));
        return engagementRepository.findAllBySubjectIdAndEngagementYear(subjectId, year)
                .stream().map(entity -> engagementConverter.toDto(entity))
                .collect(Collectors.toList());

    }

    @Override
    public List<EngagementDto> findAllBySubjectId(Long subjectId) throws ResourceNotFoundException {
        subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with ID " + subjectId + " not found."));
        return engagementRepository.findAllBySubjectId(subjectId)
                .stream().map(entity -> engagementConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public Engagement findById(Long id) {
        Optional<Engagement> result = engagementRepository.findById(id);
        if (result.isPresent()) {
            Engagement engagement = result.get();
            return engagement;
        } else {
            throw new ResourceNotFoundException("Engagement with ID = " + id + " does not exist.");
        }
    }

    @Override
    public EngagementDto save(EngagementDto engagementDto) {
        Engagement engagement = engagementConverter.toEntity(engagementDto);
        Member member = memberRepository.findById(engagementDto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + engagementDto.getMemberId() + " not found."));
        Subject subject = subjectRepository.findById(engagementDto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject with ID " + engagementDto.getSubjectId() + " not found."));

        // Ensure that member and subject are from same department

        long departmentIdByMember = member.getDepartment().getId();
        long departmentIdBySubject = subject.getDepartment().getId();
        if (departmentIdBySubject == departmentIdByMember) {
            engagement.setMember(member);
            engagement.setSubject(subject);
        } else {
            throw new DepartmentMismatchException("Subject with ID = " + engagementDto.getSubjectId() + " and member with ID = " + engagementDto.getMemberId() + " are not from the same department.");
        }

        // Ensure that member is not already assigned to subject

        List<Engagement> engagementList = engagementRepository.findAllByMemberIdAndSubjectIdOrderByIdDesc(member.getId(), subject.getId());

        if (!engagementList.isEmpty()) {
            throw new EntityAlreadyExistsException("Member has already been assigned to this subject.");
        }


        Engagement newEngagement = engagementRepository.save(engagement);
        return engagementConverter.toDto(newEngagement);
    }

    // year and teaching forms can only be updated
    @Override
    public EngagementDto update(EngagementDto engagementDto) {
        Optional<Engagement> result = engagementRepository.findById(engagementDto.getId());

        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Engagement with ID = " + engagementDto.getId() + " not found.");
        } else {
            Engagement engagement = result.get();
            // Checking if year is about to be changed
            // If so, check if engagement is assigned to some lecture
            // If so, set lecture schedule to null because of the year mismatch
            if (engagementDto.getEngagementYear() != engagement.getEngagementYear()) { // changed year
                List<Lecture> lecture = lectureRepository.findAllByEngagementId(engagement.getId());
                if (lecture != null && !lecture.isEmpty()) {
                    for (Lecture l : lecture) {
                        if (engagementDto.getEngagementYear() != l.getLectureSchedule().getScheduleYear())
                            throw new YearMismatch("Year mismatch. Engagement's year does not match lecture schedule's year.");
                    }
                }
            }

            // Check if year and teachingForm are not null before updating
            if (engagementDto.getEngagementYear() != null) {
                engagement.setEngagementYear(engagementDto.getEngagementYear());
            }

            if (engagementDto.getTeachingForm() != null) {
                engagement.setTeachingForm(engagementDto.getTeachingForm());
            }

            Engagement updatedEngagement = engagementRepository.save(engagement);
            return engagementConverter.toDto(updatedEngagement);
        }
    }


    @Override
    public void delete(Long id) {
        findById(id);
        engagementRepository.deleteById(id);
    }
}
