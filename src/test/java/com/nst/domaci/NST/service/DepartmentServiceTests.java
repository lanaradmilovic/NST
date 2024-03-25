package com.nst.domaci.NST.service;

import com.nst.domaci.NST.converter.impl.DepartmentConverter;
import com.nst.domaci.NST.dto.DepartmentDto;
import com.nst.domaci.NST.entity.Department;
import com.nst.domaci.NST.entity.LeaderHistory;
import com.nst.domaci.NST.entity.Member;
import com.nst.domaci.NST.entity.SecretaryHistory;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.MemberNotInDepartmentException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.exception.RoleConflictException;
import com.nst.domaci.NST.repository.DepartmentRepository;
import com.nst.domaci.NST.repository.LeaderHistoryRepository;
import com.nst.domaci.NST.repository.MemberRepository;
import com.nst.domaci.NST.repository.SecretaryHistoryRepository;
import com.nst.domaci.NST.service.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DepartmentServiceTests {

    @Autowired
    public DepartmentService departmentService;
    @MockBean
    public DepartmentRepository departmentRepository;
    @MockBean
    public DepartmentConverter departmentConverter;
    @MockBean
    public MemberRepository memberRepository;
    @MockBean
    public LeaderHistoryRepository leaderHistoryRepository;
    @MockBean
    public SecretaryHistoryRepository secretaryHistoryRepository;

    @Test
    public void saveSuccessTest() {
        DepartmentDto departmentDto = DepartmentDto.builder()
                .name("Engineering")
                .shortName("ENG")
                .build();
        Department department = Department.builder()
                .name("Engineering")
                .shortName("ENG")
                .build();
        Mockito.when(departmentRepository.findByName(departmentDto.getName())).thenReturn(Optional.empty());
        Mockito.when(departmentConverter.toEntity(departmentDto)).thenReturn(department);
        Mockito.when(departmentRepository.save(department)).thenReturn(department);
        Mockito.when(departmentConverter.toDto(department)).thenReturn(departmentDto);

        DepartmentDto savedDto = departmentService.save(departmentDto);
        Assertions.assertNotNull(savedDto);
        Assertions.assertEquals(departmentDto, savedDto);
    }

    @Test
    public void saveFailureTest() {
        DepartmentDto departmentDto = DepartmentDto.builder()
                .name("Engineering")
                .build();
        Department department = Department.builder()
                .name("Engineering")
                .build();
        Mockito.when(departmentRepository.findByName(departmentDto.getName())).thenReturn(Optional.of(department));
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> departmentService.save(departmentDto));
    }

    @Test
    public void findByIdSuccessTest() {
        Long id = 1L;
        Department department = Department.builder()
                .name("Engineering")
                .build();
        Mockito.when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        Department found = departmentService.findById(id);
        Assertions.assertEquals(department, found);
    }

    @Test
    public void findByIdFailure() {
        Long id = 1L;

        Mockito.when(departmentRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> departmentService.findById(id));
    }

    @Test
    public void deleteSuccessTest() {
        Long id = 1L;
        Department department = Department.builder()
                .id(id)
                .name("Engineering")
                .build();
        Mockito.when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        departmentService.delete(id);
        Mockito.verify(departmentRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void deleteFailureTest() {
        Long id = 1L;
        Mockito.when(departmentRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> departmentService.delete(id));
        Mockito.verify(departmentRepository, Mockito.never()).deleteById(id);
    }

    @Test
    public void findAllTest() {
        Department department1 = Department.builder()
                .name("Department 1")
                .build();
        Department department2 = Department.builder()
                .name("Department 2")
                .build();
        List<Department> departments = new ArrayList<>();
        departments.add(department1);
        departments.add(department2);
        Mockito.when(departmentRepository.findAll()).thenReturn(departments);
        List<Department> found = departmentService.findAll();

        Assertions.assertNotNull(departments);
        Assertions.assertEquals(departments.size(), found.size());
        Assertions.assertTrue(found.contains(department1));
        Assertions.assertTrue(found.contains(department2));
    }

    @Test
    public void updateSuccessTest() {
        Long id = 1L;
        String updatedName = "Computer Science";
        String updatedShortName = "CS";


        DepartmentDto departmentDto = DepartmentDto.builder()
                .id(id)
                .name(updatedName)
                .shortName(updatedShortName)
                .build();
        Department department = Department.builder()
                .id(id)
                .name("Engineering")
                .shortName("ENG")
                .build();
        Mockito.when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        department.setName(updatedName);
        department.setShortName(updatedShortName);

        Mockito.when(departmentRepository.save(department)).thenReturn(department);
        Mockito.when(departmentConverter.toDto(department)).thenReturn(departmentDto);

        DepartmentDto updated = departmentService.update(departmentDto);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(departmentDto, updated);
        Assertions.assertEquals(departmentDto.getName(), updated.getName());
        Assertions.assertEquals(departmentDto.getShortName(), updated.getShortName());
    }

    @Test
    public void updateFailureTest() {
        Long id = 1L;
        DepartmentDto departmentDto = DepartmentDto.builder()
                .id(id)
                .name("Computer Science")
                .build();
        Mockito.when(departmentRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> departmentService.update(departmentDto));
    }

    @Test
    public void setLeaderSuccessTest() {
        Long departmentId = 1L;
        Long memberId = 1L;

        Department department = Department.builder()
                .id(departmentId)
                .name("Engineering")
                .shortName("ENG")
                .members(new ArrayList<>())
                .leaderHistories(new ArrayList<>())
                .build();
        Member member = Member.builder()
                .id(memberId)
                .department(department)
                .firstName("Jenny")
                .lastName("Roy")
                .build();

        department.getMembers().add(member);

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        Mockito.when(leaderHistoryRepository.findCurrentLeaderByDepartmentId(departmentId)).thenReturn(null);
        Mockito.when(secretaryHistoryRepository.findCurrentSecretaryByMemberId(memberId)).thenReturn(null);
        Mockito.when(leaderHistoryRepository.findCurrentLeaderByMemberId(memberId)).thenReturn(null);

        departmentService.setLeader(departmentId, memberId);

        Assertions.assertEquals(memberId, department.getCurrentLeader().getId());
    }

    @Test
    public void setLeaderNotFoundDepartment() {
        Long departmentId = 1L;
        Long memberId = 1L;
        Mockito.when(departmentRepository.findById(departmentId)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, ()-> departmentService.setLeader(departmentId, memberId));
    }

    @Test
    public void setLeaderNotFoundMember() {
        Long departmentId = 1L;
        Long memberId = 1L;
        Mockito.when(memberRepository.findById(memberId)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, ()-> departmentService.setLeader(departmentId, memberId));
    }


    @Test
    public void departmentCanHaveOnlyOneLeader() {
        Long departmentId = 1L;
        Long leaderId = 1L;
        Long memberId=2L;

        Department department = Department.builder()
                .id(departmentId)
                .members(new ArrayList<>())
                .leaderHistories(new ArrayList<>())
                .build();
        Member leader = Member.builder()
                .id(leaderId)
                .department(department)
                .build();
        Member member = Member.builder()
                .id(memberId)
                .department(department)
                .firstName("Jenny")
                .lastName("Roy")
                .build();
        department.setCurrentLeader(leader);
        department.getMembers().add(member);
        LeaderHistory leaderHistory = LeaderHistory.builder()
                .department(department)
                .member(leader)
                .startDate(LocalDate.of(2023,12,4))
                .build();
        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        Mockito.when(leaderHistoryRepository.findCurrentLeaderByDepartmentId(departmentId)).thenReturn(leaderHistory);
        departmentService.setLeader(department.getId(), member.getId());
        Assertions.assertEquals(department.getCurrentLeader(), member);

    }
    @Test
    public void memberNotInDepartmentSetLeaderFailure() {
        Long departmentId = 1L;
        Long memberId = 1L;

        Department department = Department.builder()
                .id(departmentId)
                .members(new ArrayList<>())
                .build();
        Member member = Member.builder()
                .id(memberId)
                .department(Department.builder().id(2L).build())
                .build();

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // member is not in the list of department members
        Assertions.assertThrows(MemberNotInDepartmentException.class, () -> departmentService.setLeader(departmentId, memberId));
    }

    @Test
    public void memberIsSecretarySetLeaderFailure() {
        Long departmentId = 1L;
        Long memberId = 1L;
        Department department = Department.builder()
                .id(departmentId)
                .members(new ArrayList<>())
                .build();
        Member member = Member.builder()
                .id(memberId)
                .department(department)
                .build();
        SecretaryHistory secretaryHistory = SecretaryHistory.builder()
                .department(department)
                .member(member)
                .startDate(LocalDate.of(2023, 8, 25))
                .build();

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        department.getMembers().add(member);
        department.setCurrentSecretary(member);

        Mockito.when(secretaryHistoryRepository.findCurrentSecretaryByMemberId(memberId)).thenReturn(secretaryHistory);
        Assertions.assertThrows(RoleConflictException.class, ()-> departmentService.setLeader(departmentId, memberId));
    }

    // nepotrebno jer member moze pripadati samo jednom departmentu
    @Test
    public void memberIsLeaderOfDifferentDepartmentSetLeaderFailure(){
        Long departmentId = 1L;
        Long memberId = 1L;
        Department department = Department.builder()
                .id(departmentId)
                .members(new ArrayList<>())
                .build();
        Department membersDept = Department.builder()
                .id(2L)
                .members(new ArrayList<>())
                .leaderHistories(new ArrayList<>())
                .build();
        Member member = Member.builder()
                .id(memberId)
                .department(membersDept)
                .build();

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        membersDept.getMembers().add(member);
        membersDept.setCurrentLeader(member);

        LeaderHistory leaderHistory = LeaderHistory.builder()
                .department(membersDept)
                .member(member)
                .startDate(LocalDate.now())
                .build();
        membersDept.getLeaderHistories().add(leaderHistory);

        Mockito.when(leaderHistoryRepository.findCurrentLeaderByMemberId(memberId)).thenReturn(leaderHistory);
        Assertions.assertThrows(MemberNotInDepartmentException.class, () -> departmentService.setLeader(departmentId, memberId));
    }

@Test
public void setSecretarySuccessTest() {
    Long departmentId = 1L;
    Long memberId = 1L;

    Department department = Department.builder()
            .id(departmentId)
            .name("Engineering")
            .shortName("ENG")
            .members(new ArrayList<>())
            .secretaryHistories(new ArrayList<>())
            .build();
    Member member = Member.builder()
            .id(memberId)
            .department(department)
            .firstName("Jenny")
            .lastName("Roy")
            .build();
    department.getMembers().add(member);

    Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
    Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    Mockito.when(secretaryHistoryRepository.findCurrentSecretaryByDepartmentId(departmentId)).thenReturn(null);
    Mockito.when(leaderHistoryRepository.findCurrentLeaderByMemberId(memberId)).thenReturn(null);
    Mockito.when(secretaryHistoryRepository.findCurrentSecretaryByMemberId(memberId)).thenReturn(null);

    departmentService.setSecretary(departmentId, memberId);

    Assertions.assertEquals(memberId, department.getCurrentSecretary().getId());
}

    @Test
    public void memberNotInDepartmentSetSecretaryFailure() {
        Long departmentId = 1L;
        Long memberId = 1L;

        Department department = Department.builder()
                .id(departmentId)
                .members(new ArrayList<>())
                .build();
        Member member = Member.builder()
                .id(memberId)
                .department(Department.builder().id(2L).build()) // Associate the member with a different department
                .build();

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        Assertions.assertThrows(MemberNotInDepartmentException.class, () -> departmentService.setSecretary(departmentId, memberId));
    }
    @Test
    public void setSecretaryNotFoundDepartment() {
        Long departmentId = 1L;
        Long memberId = 1L;
        Mockito.when(departmentRepository.findById(departmentId)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> departmentService.setSecretary(departmentId, memberId));
    }

    @Test
    public void setSecretaryNotFoundMember() {
        Long departmentId = 1L;
        Long memberId = 1L;
        Mockito.when(memberRepository.findById(memberId)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> departmentService.setSecretary(departmentId, memberId));
    }

    @Test
    public void memberIsLeaderSetSecretaryFailure() {
        Long departmentId = 1L;
        Long memberId = 1L;

        Department department = Department.builder()
                .id(departmentId)
                .members(new ArrayList<>())
                .build();
        Member member = Member.builder()
                .id(memberId)
                .department(department)
                .build();
        LeaderHistory leaderHistory = LeaderHistory.builder()
                .department(department)
                .member(member)
                .startDate(LocalDate.of(2023, 8, 25))
                .build();

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        department.getMembers().add(member);
        department.setCurrentLeader(member);

        Mockito.when(leaderHistoryRepository.findCurrentLeaderByMemberId(memberId)).thenReturn(leaderHistory);

        Assertions.assertThrows(RoleConflictException.class, ()-> departmentService.setSecretary(departmentId, memberId));
    }

    @Test
    public void memberIsSecretaryOfDifferentDepartmentSetSecretaryFailure() {
        Long departmentId = 1L;
        Long memberId = 1L;

        Department department = Department.builder()
                .id(departmentId)
                .members(new ArrayList<>())
                .build();
        Department membersDepartment = Department.builder()
                .id(2L)
                .members(new ArrayList<>())
                .secretaryHistories(new ArrayList<>())
                .build();
        Member member = Member.builder()
                .id(memberId)
                .department(membersDepartment)
                .build();

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        membersDepartment.getMembers().add(member);
        membersDepartment.setCurrentSecretary(member);

        SecretaryHistory secretaryHistory = SecretaryHistory.builder()
                .department(membersDepartment)
                .member(member)
                .startDate(LocalDate.now())
                .build();
        membersDepartment.getSecretaryHistories().add(secretaryHistory);

        Mockito.when(secretaryHistoryRepository.findCurrentSecretaryByMemberId(memberId)).thenReturn(secretaryHistory);

        Assertions.assertThrows(MemberNotInDepartmentException.class, () -> departmentService.setSecretary(departmentId, memberId));
    }
    @Test
    public void departmentCanHaveOnlyOneSecretary() {
        Long departmentId = 1L;
        Long secretaryId = 1L;
        Long memberId=2L;

        Department department = Department.builder()
                .id(departmentId)
                .members(new ArrayList<>())
                .secretaryHistories(new ArrayList<>())
                .build();
        Member secretary = Member.builder()
                .id(secretaryId)
                .department(department)
                .build();
        Member member = Member.builder()
                .id(memberId)
                .department(department)
                .firstName("Jenny")
                .lastName("Roy")
                .build();
        department.setCurrentSecretary(secretary);
        department.getMembers().add(member);
        SecretaryHistory secretaryHistory = SecretaryHistory.builder()
                .department(department)
                .member(secretary)
                .startDate(LocalDate.of(2023,12,4))
                .build();
        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        Mockito.when(secretaryHistoryRepository.findCurrentSecretaryByDepartmentId(departmentId)).thenReturn(secretaryHistory);
        departmentService.setSecretary(department.getId(), member.getId());
        Assertions.assertEquals(department.getCurrentSecretary(), member);

    }
    @Test
    public void setLeaderMemberNotFoundTest() {
        Long departmentId = 1L;
        Long memberId = 1L;

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(new Department()));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> departmentService.setLeader(departmentId, memberId));
        Mockito.verify(leaderHistoryRepository, Mockito.never()).findCurrentLeaderByDepartmentId(departmentId);
        Mockito.verify(secretaryHistoryRepository, Mockito.never()).findCurrentSecretaryByMemberId(memberId);
    }
    @Test
    public void setSecretaryMemberNotFoundTest() {
        Long departmentId = 1L;
        Long memberId = 1L;

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(new Department()));
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> departmentService.setSecretary(departmentId, memberId));
        Mockito.verify(secretaryHistoryRepository, Mockito.never()).findCurrentSecretaryByDepartmentId(departmentId);
        Mockito.verify(leaderHistoryRepository, Mockito.never()).findCurrentLeaderByMemberId(memberId);
    }


}

