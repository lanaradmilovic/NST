package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.*;
import com.nst.domaci.NST.dto.*;
import com.nst.domaci.NST.entity.*;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.IllegalArgumentException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.*;
import com.nst.domaci.NST.service.MemberService;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
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

    public MemberServiceImpl(MemberConverter memberConverter, MemberRepository memberRepository, DepartmentRepository departmentRepository, ScientificFieldRepository scientificFieldRepository, EducationTitleRepository educationTitleRepository, AcademicTitleRepository academicTitleRepository, AcademicTitleHistoryRepository academicTitleHistoryRepository) {
        this.memberConverter = memberConverter;
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
        this.scientificFieldRepository = scientificFieldRepository;
        this.educationTitleRepository = educationTitleRepository;
        this.academicTitleRepository = academicTitleRepository;
        this.academicTitleHistoryRepository = academicTitleHistoryRepository;
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member findById(Long id) throws ResourceNotFoundException {
        Optional<Member> result = memberRepository.findById(id);
        if (result.isPresent()) {
            Member member = result.get();
            return member;
        } else {
            throw new ResourceNotFoundException("Member with ID = " + id + " does not exist.");
        }
    }

    @Override
    public List<MemberDto> findAllByDepartmentId(Long departmentId) throws ResourceNotFoundException {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department with ID = " + departmentId + " does not exist."));
        return memberRepository
                .findAllByDepartmentId(departmentId)
                .stream().map(entity -> memberConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Member> result = memberRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Member with ID = " + id + " not found");
        }
        Member member = result.get();

        Member currentLeader = member.getDepartment().getCurrentLeader();
        Member currentSecretary = member.getDepartment().getCurrentSecretary();

        // check if member is currently secretary or leader of some department
        if (member.equals(currentLeader)) {
            throw new IllegalArgumentException("This member is currently leader at department: with ID = : " + member.getDepartment().getId());
        }
        if (member.equals(currentSecretary)) {
            throw new IllegalArgumentException("This member is currently secretary at department with ID = : " + member.getDepartment().getId());
        }

        memberRepository.deleteById(id);
    }


    @Override
    public MemberDto save(MemberDto memberDto) throws EntityAlreadyExistsException {
        Department department = departmentRepository.findById(memberDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + memberDto.getDepartmentId() + " not found."));

        AcademicTitle academicTitle = academicTitleRepository.findById(memberDto.getAcademicTitleId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicTitle with ID " + memberDto.getAcademicTitleId() + " not found."));

        EducationTitle educationTitle = educationTitleRepository.findById(memberDto.getEducationTitleId())
                .orElseThrow(() -> new ResourceNotFoundException("EducationTitle with ID " + memberDto.getEducationTitleId() + " not found."));

        ScientificField scientificField = scientificFieldRepository.findById(memberDto.getScientificFieldId())
                .orElseThrow(() -> new ResourceNotFoundException("ScientificField with ID " + memberDto.getScientificFieldId() + " not found."));

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
                    .orElseThrow(() -> new ResourceNotFoundException("Department with ID " + memberDto.getDepartmentId() + " not found."));

            // if department is about to be changed, check FIRST if member is currently leader or secretary at that department
            if (!oldMember.getDepartment().equals(department)) { // changed
                Member currentLeader = oldMember.getDepartment().getCurrentLeader();
                Member currentSecretary = oldMember.getDepartment().getCurrentSecretary();

                if (oldMember.equals(currentLeader)) {
                    throw new IllegalArgumentException("This member is currently leader at department: with ID = : " + oldMember.getDepartment().getId());
                }
                if (oldMember.equals(currentSecretary)) {
                    throw new IllegalArgumentException("This member is currently secretary at department with ID = : " + oldMember.getDepartment().getId());
                }
            }

            AcademicTitle academicTitle = academicTitleRepository.findById(memberDto.getAcademicTitleId())
                    .orElseThrow(() -> new ResourceNotFoundException("AcademicTitle with ID " + memberDto.getAcademicTitleId() + " not found."));

            EducationTitle educationTitle = educationTitleRepository.findById(memberDto.getEducationTitleId())
                    .orElseThrow(() -> new ResourceNotFoundException("EducationTitle with ID " + memberDto.getEducationTitleId() + " not found."));

            ScientificField scientificField = scientificFieldRepository.findById(memberDto.getScientificFieldId())
                    .orElseThrow(() -> new ResourceNotFoundException("ScientificField with ID " + memberDto.getScientificFieldId() + " not found."));

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

                // update old
                AcademicTitleHistory oldAcademicTitleHistory = academicTitleHistoryRepository.findCurrentAcademicTitleByMemberId(updatedMember.getId());
                oldAcademicTitleHistory.setEndDate(LocalDate.now());
                academicTitleHistoryRepository.save(oldAcademicTitleHistory);

                academicTitleHistoryRepository.save(academicTitleHistory);

            }

            return memberConverter.toDto(updatedMember);
        } else {
            throw new ResourceNotFoundException("Member with ID = " + memberDto.getId() + " does not exist.");
        }
    }

    @Override
    public MemberDto updateAcademicTitle(MemberDto memberDto, AcademicTitleDto newTitle, ScientificFieldDto newField) throws ResourceNotFoundException {
        Optional<Member> result = memberRepository.findById(memberDto.getId());
        if (result.isPresent()) {
            Member oldMember = result.get();
            if (memberDto.getAcademicTitleId() == newTitle.getId()) {
                throw new IllegalArgumentException("Member already have the same academic title.");
            }

            AcademicTitle academicTitle = academicTitleRepository.findById(newTitle.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("AcademicTitle with ID " + newTitle.getId() + " not found."));
            ScientificField scientificField = scientificFieldRepository.findById(newField.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("ScientificField with ID " + newField.getId() + " not found."));

            // update old
            AcademicTitleHistory oldAcademicTitleHistory = academicTitleHistoryRepository.findCurrentAcademicTitleByMemberId(oldMember.getId());
            oldAcademicTitleHistory.setEndDate(LocalDate.now());
            academicTitleHistoryRepository.save(oldAcademicTitleHistory);

            Member newMember = oldMember;

            newMember.setAcademicTitle(academicTitle);
            newMember.setScientificField(scientificField);
            Member updated = memberRepository.save(newMember);

            // create new
            AcademicTitleHistory academicTitleHistory = AcademicTitleHistory.builder()
                    .member(updated)
                    .startDate(LocalDate.now())
                    .academicTitle(academicTitle)
                    .scientificField(scientificField)
                    .build();

//            oldMember.getAcademicTitleHistories().add(academicTitleHistory);
//
//            Member updated = memberRepository.save(oldMember);
////            academicTitleHistoryRepository.save(oldAcademicTitleHistory);
            academicTitleHistoryRepository.save(academicTitleHistory);

            return memberConverter.toDto(updated);

        } else {
            throw new ResourceNotFoundException("Member with ID = " + memberDto.getId() + " does not exist.");
        }
    }


}
