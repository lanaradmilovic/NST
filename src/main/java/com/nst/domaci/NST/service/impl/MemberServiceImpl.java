package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.*;
import com.nst.domaci.NST.dto.*;
import com.nst.domaci.NST.entity.*;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.*;
import com.nst.domaci.NST.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberConverter memberConverter;
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final ScientificFieldRepository scientificFieldRepository;
    private final EducationTitleRepository educationTitleRepository;
    private final AcademicTitleRepository academicTitleRepository;
    private final AcademicTitleHistoryRepository academicTitleHistoryRepository;

    public MemberServiceImpl(MemberConverter memberConverter, MemberRepository memberRepository, DepartmentRepository departmentRepository, AcademicTitleRepository academicTitleRepository, EducationTitleRepository educationTitleRepository, ScientificFieldRepository scientificFieldRepository, AcademicTitleHistoryRepository academicTitleHistoryRepository) {
        this.memberConverter = memberConverter;
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
        this.academicTitleRepository = academicTitleRepository;
        this.educationTitleRepository = educationTitleRepository;
        this.scientificFieldRepository = scientificFieldRepository;
        this.academicTitleHistoryRepository = academicTitleHistoryRepository;
    }

    @Override
    public List<MemberDto> findAll() {
        return memberRepository
                .findAll()
                .stream().map(entity -> memberConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto findById(Long id) throws ResourceNotFoundException {
        Optional<Member> result = memberRepository.findById(id);
        if (result.isPresent()) {
            Member member = result.get();
            return memberConverter.toDto(member);
        } else {
            throw new ResourceNotFoundException("Member with ID = " + id + " does not exist.");
        }
    }

    @Override
    public List<MemberDto> findAllByDepartmentId(Long departmentId) throws ResourceNotFoundException{
        departmentRepository.findById(departmentId); // checking if exists
        return memberRepository
                .findAllByDepartmentId(departmentId)
                .stream().map(entity -> memberConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        findById(id);
        memberRepository.deleteById(id);
    }

    @Override
    public MemberDto save(MemberDto memberDto) throws EntityAlreadyExistsException {
        Department department = departmentRepository.findById(memberDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Department with ID " + memberDto.getId() + " not found."));

        AcademicTitle academicTitle = academicTitleRepository.findById(memberDto.getAcademicTitleId())
                .orElseThrow(() -> new EntityNotFoundException("AcademicTitle with ID " + memberDto.getAcademicTitleId() + " not found."));

        EducationTitle educationTitle = educationTitleRepository.findById(memberDto.getEducationTitleId())
                .orElseThrow(() -> new EntityNotFoundException("EducationTitle with ID " + memberDto.getEducationTitleId() + " not found."));

        ScientificField scientificField = scientificFieldRepository.findById(memberDto.getScientificFieldId())
                .orElseThrow(() -> new EntityNotFoundException("ScientificField with ID " + memberDto.getScientificFieldId() + " not found."));

        Member member = memberConverter.toEntity(memberDto);
        member.setAcademicTitle(academicTitle);
        member.setDepartment(department);
        member.setEducationTitle(educationTitle);
        member.setScientificField(scientificField);

        Member vracenMember = memberRepository.save(member);

        AcademicTitleHistory academicTitleHistory = new AcademicTitleHistory(null, vracenMember, LocalDate.now(), null, academicTitle, scientificField);
        academicTitleHistoryRepository.save(academicTitleHistory);

        return memberConverter.toDto(vracenMember);
    }

    @Override
    public MemberDto update(MemberDto memberDto) throws ResourceNotFoundException {

        Optional<Member> result = memberRepository.findById(memberDto.getId());
        if (result.isPresent()) {
            Member oldMember = result.get();

            long currentAcademicTitleId = oldMember.getAcademicTitle().getId();
            long newAcademicTitleId = memberDto.getAcademicTitleId();

            Department department = departmentRepository.findById(memberDto.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Department with ID " + memberDto.getDepartmentId() + " not found."));

            AcademicTitle academicTitle = academicTitleRepository.findById(memberDto.getAcademicTitleId())
                    .orElseThrow(() -> new EntityNotFoundException("AcademicTitle with ID " + memberDto.getAcademicTitleId() + " not found."));

            EducationTitle educationTitle = educationTitleRepository.findById(memberDto.getEducationTitleId())
                    .orElseThrow(() -> new EntityNotFoundException("EducationTitle with ID " + memberDto.getEducationTitleId() + " not found."));

            ScientificField scientificField = scientificFieldRepository.findById(memberDto.getScientificFieldId())
                    .orElseThrow(() -> new EntityNotFoundException("ScientificField with ID " + memberDto.getScientificFieldId() + " not found."));

            oldMember.setAcademicTitle(academicTitle);
            oldMember.setDepartment(department);
            oldMember.setEducationTitle(educationTitle);
            oldMember.setScientificField(scientificField);
            oldMember.setFirstName(memberDto.getFirstName());
            oldMember.setLastName(memberDto.getLastName());

            Member updatedMember = memberRepository.save(oldMember);

            if (currentAcademicTitleId != newAcademicTitleId) { // changed
                // create new
                AcademicTitleHistory academicTitleHistory = AcademicTitleHistory.builder()
                        .member(updatedMember)
                        .startDate(LocalDate.now())
                        .academicTitle(academicTitle)
                        .scientificField(scientificField)
                        .build();

                academicTitleHistoryRepository.save(academicTitleHistory);
                // update old
                AcademicTitleHistory oldAcademicTitleHistory = academicTitleHistoryRepository.findCurrentAcademicTitleByMemberId(updatedMember.getId());
                oldAcademicTitleHistory.setEndDate(LocalDate.now());
                academicTitleHistoryRepository.save(oldAcademicTitleHistory);
            }

            return memberConverter.toDto(updatedMember);
        } else {
            throw new ResourceNotFoundException("Member with ID = " + memberDto.getId() + " does not exist.");
        }
    }
}
