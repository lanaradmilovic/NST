package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.DepartmentConverter;
import com.nst.domaci.NST.converter.impl.MemberConverter;
import com.nst.domaci.NST.dto.DepartmentDto;
import com.nst.domaci.NST.entity.*;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.MemberNotInDepartmentException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.exception.RoleConflictException;
import com.nst.domaci.NST.repository.DepartmentRepository;
import com.nst.domaci.NST.repository.LeaderHistoryRepository;
import com.nst.domaci.NST.repository.MemberRepository;
import com.nst.domaci.NST.repository.SecretaryHistoryRepository;
import com.nst.domaci.NST.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentConverter departmentConverter;
    private final MemberConverter memberConverter;
    private final MemberRepository memberRepository;
    private final LeaderHistoryRepository leaderHistoryRepository;
    private final SecretaryHistoryRepository secretaryHistoryRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentConverter departmentConverter, MemberConverter memberConverter, MemberRepository memberRepository, LeaderHistoryRepository leaderHistoryRepository, SecretaryHistoryRepository secretaryHistoryRepository) {
        this.departmentRepository = departmentRepository;
        this.departmentConverter = departmentConverter;
        this.memberConverter = memberConverter;
        this.memberRepository = memberRepository;
        this.leaderHistoryRepository = leaderHistoryRepository;
        this.secretaryHistoryRepository = secretaryHistoryRepository;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(Long id) throws ResourceNotFoundException {
        Optional<Department> result = departmentRepository.findById(id);
        if (result.isPresent()) {
            Department department = result.get();
            return department;
        } else {
            throw new ResourceNotFoundException("Department with ID = " + id + " does not exist.");
        }
    }

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) throws EntityAlreadyExistsException {
        Optional<Department> result = departmentRepository.findByName(departmentDto.getName());
        if (result.isPresent()) {
            throw new EntityAlreadyExistsException("Department with name = " + departmentDto.getName() + " already exists.");
        } else {
            Department department = departmentConverter.toEntity(departmentDto);
            department = departmentRepository.save(department);
            return departmentConverter.toDto(department);
        }
    }

    @Override
    public DepartmentDto update(DepartmentDto departmentDto) throws ResourceNotFoundException {
        Optional<Department> result = departmentRepository.findById(departmentDto.getId());
        if (result.isPresent()) {
            Department department = result.get();
            department.setName(departmentDto.getName());
            department.setShortName(departmentDto.getShortName());
            department = departmentRepository.save(department);
            return departmentConverter.toDto(department);
        }
        throw new ResourceNotFoundException("Department with ID = " + departmentDto.getId() + " does not exist.");
    }


    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        findById(id);
        departmentRepository.deleteById(id);
    }

    @Override
    public void setLeader(Long departmentId, Long memberId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + memberId));
        // Check if the member is in the list of department members
        if (!department.getMembers().contains(member)) {
            throw new MemberNotInDepartmentException("Member with ID: " + memberId + " is not in the department's list of members.");
        }
        department.setCurrentLeader(member);
        // Ensure that the department can have only ONE leader at a time
        if (leaderHistoryRepository.findCurrentLeaderByDepartmentId(departmentId) != null) {
            LeaderHistory currentLeader = leaderHistoryRepository.findCurrentLeaderByDepartmentId(departmentId);
            currentLeader.setEndDate(LocalDate.now());
        }
        // Ensure that the member is not currently a secretary
        if (secretaryHistoryRepository.findCurrentByMemberId(memberId) != null) {
            throw new RoleConflictException("Cannot set leader to a member who is currently a secretary.");
        }
        // Ensure that the member is not a leader of a different department
        if (leaderHistoryRepository.findCurrentByMemberId(memberId) != null) {
            throw new RoleConflictException("Cannot set leader to a member who is currently a leader in another department.");
        }
        // member can't be leader of different department

        LeaderHistory leaderHistory = new LeaderHistory();
        leaderHistory.setDepartment(department);
        leaderHistory.setMember(member);
        leaderHistory.setStartDate(LocalDate.now());
        department.getLeaderHistories().add(leaderHistory);
        departmentRepository.save(department);
    }

    @Override
    public void setSecretary(Long departmentId, Long memberId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + departmentId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + memberId));
        // Check if the member is in the list of department members
        if (!department.getMembers().contains(member)) {
            throw new MemberNotInDepartmentException("Member with ID: " + memberId + " is not in the department's list of members.");
        }
        department.setCurrentSecretary(member);
        // Ensure that the department can have only ONE secretary at a time
        if (secretaryHistoryRepository.findCurrentSecretaryByDepartmentId(departmentId) != null) {
            SecretaryHistory currentSecretary = secretaryHistoryRepository.findCurrentSecretaryByDepartmentId(departmentId);
            currentSecretary.setEndDate(LocalDate.now());
        }
        // Ensure that the member is not currently a leader
        if (leaderHistoryRepository.findCurrentByMemberId(memberId) != null) {
            throw new RoleConflictException("Cannot set secretary to a member who is currently a leader.");
        }
        // Ensure that the member is not currently a secretary in another department
        if (secretaryHistoryRepository.findCurrentByMemberId(memberId) != null) {
            throw new RoleConflictException("Cannot set secretary to a member who is currently a secretary in another department.");
        }
        SecretaryHistory secretaryHistory = new SecretaryHistory();
        secretaryHistory.setDepartment(department);
        secretaryHistory.setMember(member);
        secretaryHistory.setStartDate(LocalDate.now());
        department.getSecretaryHistories().add(secretaryHistory);
        departmentRepository.save(department);
    }


}
