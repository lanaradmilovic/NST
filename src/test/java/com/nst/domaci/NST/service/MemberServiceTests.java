package com.nst.domaci.NST.service;

import com.nst.domaci.NST.converter.impl.AcademicTitleConverter;
import com.nst.domaci.NST.converter.impl.MemberConverter;
import com.nst.domaci.NST.converter.impl.ScientificFieldConverter;
import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.dto.MemberDto;
import com.nst.domaci.NST.dto.ScientificFieldDto;
import com.nst.domaci.NST.entity.*;
import com.nst.domaci.NST.exception.IllegalArgumentException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.*;
import com.nst.domaci.NST.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.*;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MemberServiceTests {
    @MockBean
    private MemberConverter memberConverter;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private AcademicTitleRepository academicTitleRepository;

    @MockBean
    private EducationTitleRepository educationTitleRepository;

    @MockBean
    private ScientificFieldRepository scientificFieldRepository;

    @MockBean
    private AcademicTitleHistoryRepository academicTitleHistoryRepository;

    @Autowired
    private MemberServiceImpl memberService;
    @MockBean
    private AcademicTitleConverter academicTitleConverter;
    @MockBean
    private ScientificFieldConverter scientificFieldConverter;

    @Test
    void testFindAll() {
        List<Member> members = new ArrayList<>();
        members.add(new Member());
        Mockito.when(memberRepository.findAll()).thenReturn(members);
        List<Member> result = memberService.findAll();
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void testFindByIdSuccess() {
        Member member = new Member();
        Mockito.when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        Assertions.assertDoesNotThrow(() -> memberService.findById(1L));
    }

    @Test
    void testFindByIdNotFound() {
        Mockito.when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.findById(1L));
    }

    @Test
    void testFindAllByDepartmentIdSuccess() {
        Long departmentId = 1L;
        List<Member> members = new ArrayList<>();
        members.add(new Member());
        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(new Department()));
        Mockito.when(memberRepository.findAllByDepartmentId(departmentId)).thenReturn(members);
        Assertions.assertDoesNotThrow(() -> memberService.findAllByDepartmentId(departmentId));
    }

    @Test
    void testFindAllByDepartmentIdNotFound() {
        Long departmentId = 1L;
        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.findAllByDepartmentId(departmentId));
    }

    @Test
    void deleteMemberIsNoLeaderAndSecretarySuccess() {
        Long memberId = 1L;

        Member member = new Member();
        member.setId(memberId);
        member.setDepartment(new Department());
        member.getDepartment().setCurrentLeader(new Member());
        member.getDepartment().setCurrentSecretary(new Member());
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        memberService.delete(memberId);
        Mockito.verify(memberRepository, Mockito.times(1)).deleteById(memberId);
    }

    @Test
    void testDeleteNotFound() {
        Long memberId = 1L;
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.delete(1L));
    }

    @Test
    void testDeleteLeader() {
        Long memberId = 1L;
        Member member = new Member();
        member.setId(memberId);
        Department department = new Department();
        member.setDepartment(department);
        member.getDepartment().setCurrentLeader(member);
        Mockito.when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        Assertions.assertThrows(IllegalArgumentException.class, () -> memberService.delete(memberId));
        Mockito.verify(memberRepository, Mockito.never()).deleteById(memberId);

    }

    @Test
    void testDeleteSecretary() {
        Long memberId = 1L;
        Member member = new Member();
        member.setId(memberId);
        Department department = new Department();
        member.setDepartment(department);
        member.getDepartment().setCurrentSecretary(member);
        Mockito.when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        Assertions.assertThrows(IllegalArgumentException.class, () -> memberService.delete(memberId));
        Mockito.verify(memberRepository, Mockito.never()).deleteById(memberId);
    }

    @Test
    void testSave() {
        MemberDto memberDto = new MemberDto();
        memberDto.setDepartmentId(1L);
        memberDto.setAcademicTitleId(1L);
        memberDto.setEducationTitleId(1L);
        memberDto.setScientificFieldId(1L);

        Department department = new Department();
        AcademicTitle academicTitle = new AcademicTitle();
        EducationTitle educationTitle = new EducationTitle();
        ScientificField scientificField = new ScientificField();

        Mockito.when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        Mockito.when(academicTitleRepository.findById(anyLong())).thenReturn(Optional.of(academicTitle));
        Mockito.when(educationTitleRepository.findById(anyLong())).thenReturn(Optional.of(educationTitle));
        Mockito.when(scientificFieldRepository.findById(anyLong())).thenReturn(Optional.of(scientificField));
        Mockito.when(memberConverter.toEntity(any(MemberDto.class))).thenReturn(new Member());

        Assertions.assertDoesNotThrow(() -> memberService.save(memberDto));
    }

    @Test
    void updateMemberLeaderChangeDepartmentFailureTest() {
        Long memberId = 1L;
        Long departmentId = 1L;
        Long academicTitleId = 1L;

        Long newDepartmentId = 2L;
        Department newDepartment = new Department();
        Mockito.when(departmentRepository.findById(newDepartmentId)).thenReturn(Optional.of(newDepartment));
        newDepartment.setId(newDepartmentId);

        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);
        memberDto.setDepartmentId(newDepartmentId);
        memberDto.setAcademicTitleId(academicTitleId);

        Member member = new Member();
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        member.setId(memberId);
        AcademicTitle academicTitle = AcademicTitle.builder().id(academicTitleId).build();
        member.setAcademicTitle(academicTitle);
        Department oldDepartment = new Department();
        oldDepartment.setMembers(new ArrayList<>());
        oldDepartment.setId(departmentId);
        oldDepartment.getMembers().add(member);

        oldDepartment.setCurrentLeader(member); // current leader

        member.setDepartment(oldDepartment);

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberService.update(memberDto));
    }

    @Test
    void updateMemberSecretaryChangeDepartmentFailureTest() {
        Long memberId = 1L;
        Long departmentId = 1L;
        Long academicTitleId = 1L;

        Long newDepartmentId = 2L;
        Department newDepartment = new Department();
        Mockito.when(departmentRepository.findById(newDepartmentId)).thenReturn(Optional.of(newDepartment));
        newDepartment.setId(newDepartmentId);

        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);
        memberDto.setDepartmentId(newDepartmentId);
        memberDto.setAcademicTitleId(academicTitleId);

        Member member = new Member();
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        member.setId(memberId);
        AcademicTitle academicTitle = AcademicTitle.builder().id(academicTitleId).build();
        member.setAcademicTitle(academicTitle);
        Department oldDepartment = new Department();
        oldDepartment.setMembers(new ArrayList<>());
        oldDepartment.setId(departmentId);
        oldDepartment.getMembers().add(member);

        oldDepartment.setCurrentSecretary(member); // current secretary

        member.setDepartment(oldDepartment);

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberService.update(memberDto));
    }

    @Test
    public void updateMemberAcademicTitleNotChangedSuccessTest() {
        Long memberDtoId = 1L;
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberDtoId);
        memberDto.setFirstName("Lina");
        memberDto.setLastName("Jason");

        Member oldMember = new Member();
        oldMember.setId(memberDtoId);
        oldMember.setFirstName("Jessica");
        oldMember.setLastName("Roy");
        Mockito.when(memberRepository.findById(memberDtoId)).thenReturn(Optional.of(oldMember));

        Long departmentId = 1L;
        Department department = new Department();
        department.setMembers(new ArrayList<>());
        department.setId(departmentId);
        department.getMembers().add(oldMember);
        memberDto.setDepartmentId(departmentId);
        oldMember.setDepartment(department);
        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        Long academicTitleId = 1L;
        // academic title not changed
        AcademicTitle academicTitle = AcademicTitle.builder().id(academicTitleId).build();
        Mockito.when(academicTitleRepository.findById(academicTitleId)).thenReturn(Optional.of(academicTitle));
        oldMember.setAcademicTitle(academicTitle);
        memberDto.setAcademicTitleId(academicTitleId);

        Long scientificFieldId = 1L;
        Long educationTitleId = 1L;
        ScientificField scientificField = ScientificField.builder().id(scientificFieldId).build();
        EducationTitle educationTitle = EducationTitle.builder().id(educationTitleId).build();
        Mockito.when(scientificFieldRepository.findById(scientificFieldId)).thenReturn(Optional.of(scientificField));
        Mockito.when(educationTitleRepository.findById(educationTitleId)).thenReturn(Optional.of(educationTitle));

        memberDto.setScientificFieldId(scientificFieldId);
        memberDto.setEducationTitleId(educationTitleId);

        Member updated = new Member();
        updated.setId(memberDtoId);
        Mockito.when(memberRepository.save(oldMember)).thenReturn(updated);

        MemberDto resultDto = new MemberDto();
        resultDto.setId(memberDtoId);
        Mockito.when(memberConverter.toDto(updated)).thenReturn(resultDto);
        resultDto = memberService.update(memberDto);

        Assertions.assertEquals(resultDto.getFirstName(), updated.getFirstName());
        Assertions.assertEquals(resultDto.getLastName(), updated.getLastName());
        Assertions.assertEquals(resultDto.getAcademicTitleId(), updated.getAcademicTitle());
    }

    @Test
    public void updateMemberAcademicTitleChangedSuccessTest() {
        Long memberDtoId = 1L;
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberDtoId);
        memberDto.setFirstName("Lina");
        memberDto.setLastName("Jason");

        Member oldMember = new Member();
        oldMember.setId(memberDtoId);
        oldMember.setFirstName("Jessica");
        oldMember.setLastName("Roy");
        Mockito.when(memberRepository.findById(memberDtoId)).thenReturn(Optional.of(oldMember));

        Long departmentId = 1L;
        Department department = new Department();
        department.setMembers(new ArrayList<>());
        department.setId(departmentId);
        department.getMembers().add(oldMember);
        memberDto.setDepartmentId(departmentId);
        oldMember.setDepartment(department);
        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        Long academicTitleId = 1L;
        Long newAcademicTitleId = 2L;
        // academic title not changed
        AcademicTitle academicTitle = AcademicTitle.builder().id(academicTitleId).build();
        oldMember.setAcademicTitle(academicTitle);

        Mockito.when(academicTitleRepository.findById(newAcademicTitleId)).thenReturn(Optional.of(academicTitle));
        memberDto.setAcademicTitleId(newAcademicTitleId);

        Long scientificFieldId = 1L;
        Long educationTitleId = 1L;
        ScientificField scientificField = ScientificField.builder().id(scientificFieldId).build();
        EducationTitle educationTitle = EducationTitle.builder().id(educationTitleId).build();
        Mockito.when(scientificFieldRepository.findById(scientificFieldId)).thenReturn(Optional.of(scientificField));
        Mockito.when(educationTitleRepository.findById(educationTitleId)).thenReturn(Optional.of(educationTitle));


        memberDto.setScientificFieldId(scientificFieldId);
        memberDto.setEducationTitleId(educationTitleId);

        Member updated = new Member();
        updated.setId(memberDtoId);
        Mockito.when(memberRepository.save(oldMember)).thenReturn(updated);

        //changed academic title
        AcademicTitleHistory academicTitleHistory = AcademicTitleHistory.builder().member(updated).build();
        AcademicTitleHistory currentAcademicTitle = AcademicTitleHistory.builder().member(updated).build();
        Mockito.when(academicTitleHistoryRepository.findCurrentAcademicTitleByMemberId(updated.getId())).thenReturn(currentAcademicTitle);
        Mockito.when(academicTitleHistoryRepository.save(currentAcademicTitle)).thenReturn(currentAcademicTitle);
        Mockito.when(academicTitleHistoryRepository.save(academicTitleHistory)).thenReturn(academicTitleHistory);

        MemberDto resultDto = new MemberDto();
        resultDto.setId(memberDtoId);
        Mockito.when(memberConverter.toDto(updated)).thenReturn(resultDto);
        resultDto = memberService.update(memberDto);

        Assertions.assertEquals(resultDto.getFirstName(), updated.getFirstName());
        Assertions.assertEquals(resultDto.getLastName(), updated.getLastName());
        Assertions.assertNotEquals(oldMember.getAcademicTitle().getId(), updated.getAcademicTitle());
    }

    @Test
    public void updateNotFoundMemberTest() {
        Long memberId = 1L;
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);

        Mockito.when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.update(memberDto));
    }

    @Test
    public void updateNotFoundDepartmentTest() {
        Long departmentId = 1L;
        Long memberId = 1L;
        MemberDto memberDto = new MemberDto();
        memberDto.setDepartmentId(departmentId);
        Member member = new Member();
        member.setId(memberId);
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        Mockito.when(departmentRepository.findById(memberDto.getDepartmentId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.update(memberDto));
    }

    @Test
    public void saveNotFoundDepartmentTest() {
        Long departmentId = 1L;
        MemberDto memberDto = new MemberDto();
        memberDto.setDepartmentId(departmentId);

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.save(memberDto));
    }

    @Test
    public void updateNotFoundAcademicTitleTest() {
        Long academicTitleId = 1L;
        MemberDto memberDto = new MemberDto();
        memberDto.setAcademicTitleId(academicTitleId);

        Mockito.when(academicTitleRepository.findById(academicTitleId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.update(memberDto));
    }

    @Test
    public void updateNotFoundEducationTitleTest() {
        Long educationTitleId = 1L;
        MemberDto memberDto = new MemberDto();
        memberDto.setEducationTitleId(educationTitleId);

        Mockito.when(academicTitleRepository.findById(educationTitleId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.update(memberDto));
    }

    @Test
    public void updateAcademicTitleSuccess() {
        // Prepare test data
        Long memberId = 1L;
        Long academicTitleId = 2L;
        Long scientificFieldId = 3L;

        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);

        AcademicTitleDto newTitle = new AcademicTitleDto();
        newTitle.setId(academicTitleId);

        ScientificFieldDto newField = new ScientificFieldDto();
        newField.setId(scientificFieldId);

        Member oldMember = new Member();
        oldMember.setId(memberId);

        AcademicTitle academicTitle = new AcademicTitle();
        academicTitle.setId(academicTitleId);

        ScientificField scientificField = new ScientificField();
        scientificField.setId(scientificFieldId);

        Member updatedMember = new Member();
        updatedMember.setId(memberId);
        updatedMember.setAcademicTitle(academicTitle);
        updatedMember.setScientificField(scientificField);

        AcademicTitleHistory academicTitleHistory = new AcademicTitleHistory(); // Create a non-null instance

        // Mock repository methods
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(oldMember));
        Mockito.when(academicTitleRepository.findById(academicTitleId)).thenReturn(Optional.of(academicTitle));
        Mockito.when(scientificFieldRepository.findById(scientificFieldId)).thenReturn(Optional.of(scientificField));
        Mockito.when(memberRepository.save(oldMember)).thenReturn(updatedMember);
        Mockito.when(academicTitleHistoryRepository.findCurrentAcademicTitleByMemberId(memberId)).thenReturn(academicTitleHistory); // Mock to return non-null

        MemberDto resultDto = new MemberDto();
        resultDto.setId(memberId);
        Mockito.when(memberConverter.toDto(updatedMember)).thenReturn(resultDto);

        // Call the service method
        resultDto = memberService.updateAcademicTitle(memberDto, newTitle, newField);

        // Assertions
        Assertions.assertEquals(updatedMember.getId(), resultDto.getId());
        Assertions.assertNotEquals(oldMember.getAcademicTitle().getId(), resultDto.getAcademicTitleId());
        Assertions.assertNotEquals(oldMember.getScientificField().getId(), resultDto.getScientificFieldId());

    }

    @Test
    public void updateAcademicTitleSameTitleTest() {
        Long memberId = 1L;
        Long academicTitleId = 2L;
        Long scientificFieldId = 3L;

        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);
        memberDto.setAcademicTitleId(academicTitleId); // Set the same academic title ID

        AcademicTitleDto newTitle = new AcademicTitleDto();
        newTitle.setId(academicTitleId);

        ScientificFieldDto newField = new ScientificFieldDto();
        newField.setId(scientificFieldId);

        Member oldMember = new Member();
        oldMember.setId(memberId);

        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(oldMember));

        Assertions.assertThrows(IllegalArgumentException.class, () -> memberService.updateAcademicTitle(memberDto, newTitle, newField));
    }

    @Test
    public void updateAcademicTitleNonExistentMemberTest() {
        // Prepare test data
        Long memberId = 1L;
        Long academicTitleId = 2L;
        Long scientificFieldId = 3L;

        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);

        AcademicTitleDto newTitle = new AcademicTitleDto();
        newTitle.setId(academicTitleId);

        ScientificFieldDto newField = new ScientificFieldDto();
        newField.setId(scientificFieldId);

        // Mock repository methods to simulate non-existent member
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // Call the service method and assert for ResourceNotFoundException
        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.updateAcademicTitle(memberDto, newTitle, newField));
    }

    @Test
    public void updateAcademicTitleNonExistentTitleTest() {
        // Prepare test data
        Long memberId = 1L;
        Long academicTitleId = 2L;
        Long scientificFieldId = 3L;

        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);

        AcademicTitleDto newTitle = new AcademicTitleDto();
        newTitle.setId(academicTitleId);

        ScientificFieldDto newField = new ScientificFieldDto();
        newField.setId(scientificFieldId);

        Member oldMember = new Member();
        oldMember.setId(memberId);

        // Mock repository methods to simulate non-existent academic title
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(oldMember));
        Mockito.when(academicTitleRepository.findById(academicTitleId)).thenReturn(Optional.empty());

        // Call the service method and assert for ResourceNotFoundException
        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.updateAcademicTitle(memberDto, newTitle, newField));
    }

    @Test
    public void updateAcademicTitleNonExistentFieldTest() {
        // Prepare test data
        Long memberId = 1L;
        Long academicTitleId = 2L;
        Long scientificFieldId = 3L;

        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);

        AcademicTitleDto newTitle = new AcademicTitleDto();
        newTitle.setId(academicTitleId);

        ScientificFieldDto newField = new ScientificFieldDto();
        newField.setId(scientificFieldId);

        Member oldMember = new Member();
        oldMember.setId(memberId);
        AcademicTitle academicTitle = new AcademicTitle();
        academicTitle.setId(academicTitleId);

        // Mock repository methods to simulate non-existent scientific field
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(oldMember));
        Mockito.when(academicTitleRepository.findById(academicTitleId)).thenReturn(Optional.of(academicTitle));
        Mockito.when(scientificFieldRepository.findById(scientificFieldId)).thenReturn(Optional.empty());

        // Call the service method and assert for ResourceNotFoundException
        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.updateAcademicTitle(memberDto, newTitle, newField));
    }

    @Test
    public void saveNotFoundAcademicTitleTest() {
        MemberDto memberDto = new MemberDto();
        memberDto.setAcademicTitleId(1L);

        Mockito.when(academicTitleRepository.findById(memberDto.getAcademicTitleId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.save(memberDto));
        // Refactor lambda expression into a separate method
        Assertions.assertThrows(ResourceNotFoundException.class, () -> throwResourceNotFoundExceptionForAcademicTitle(memberDto));

    }

    @Test
    public void saveNotFoundEducationTitleTest() {
        MemberDto memberDto = new MemberDto();
        memberDto.setEducationTitleId(1L);

        Mockito.when(educationTitleRepository.findById(memberDto.getEducationTitleId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.save(memberDto));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> throwResourceNotFoundExceptionForEducationTitle(memberDto));

    }

    @Test
    public void saveNotFoundScientificFieldTest() {
        MemberDto memberDto = new MemberDto();
        memberDto.setScientificFieldId(1L);

        Mockito.when(scientificFieldRepository.findById(memberDto.getScientificFieldId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.save(memberDto));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> throwResourceNotFoundExceptionForScientificField(memberDto));

    }

    @Test
    public void updateNotFoundAcademicTitleSecretaryTest() {
        Long memberId = 1L;
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);

        // non-existing academic title
        Long academicTitleId = 2L;
        memberDto.setAcademicTitleId(academicTitleId);

        Member oldMember = new Member();
        oldMember.setId(memberId);
        AcademicTitle academicTitle = AcademicTitle.builder().id(1L).build();
        oldMember.setAcademicTitle(academicTitle);
        Department department = new Department();
        department.setCurrentSecretary(oldMember);
        oldMember.setDepartment(department);

        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(oldMember));
        Mockito.when(academicTitleRepository.findById(academicTitleId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.update(memberDto));
        // Refactor lambda expression into a separate method
        Assertions.assertThrows(ResourceNotFoundException.class, () -> throwResourceNotFoundExceptionForAcademicTitle(memberDto));
    }


    @Test
    public void updateNotFoundEducationTitleSecretaryTest() {
        Long memberId = 1L;
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);

        // non-existing education title
        Long educationId = 1L;
        memberDto.setEducationTitleId(educationId);
        // non-existing academic title and scientific field
        Long academicTitleId=1L;
        Long scientificFieldId=1L;
        memberDto.setAcademicTitleId(academicTitleId);
        memberDto.setScientificFieldId(scientificFieldId);


        Member oldMember = new Member();
        oldMember.setId(memberId);
        AcademicTitle academicTitle = AcademicTitle.builder().id(2L).build();
        EducationTitle educationTitle = EducationTitle.builder().id(2L).build();
        oldMember.setEducationTitle(educationTitle);
        oldMember.setAcademicTitle(academicTitle);
        Department department = new Department();
        department.setCurrentSecretary(oldMember);
        oldMember.setDepartment(department);

        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(oldMember));
        Mockito.when(educationTitleRepository.findById(memberDto.getEducationTitleId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.update(memberDto));
        // Refactor lambda expression into a separate method
        Assertions.assertThrows(ResourceNotFoundException.class, () -> throwResourceNotFoundExceptionForEducationTitle(memberDto));

    }


    @Test
    public void updateNotFoundScientificFieldSecretaryTest() {
        Long memberId = 1L;
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberId);

        // non-existing academic title, education title and scientific field
        Long educationId = 1L;
        memberDto.setEducationTitleId(educationId);
        Long academicTitleId=1L;
        Long scientificFieldId=1L;
        memberDto.setAcademicTitleId(academicTitleId);
        memberDto.setScientificFieldId(scientificFieldId);

        memberDto.setScientificFieldId(1L);

        Member oldMember = new Member();
        oldMember.setId(memberId);
        AcademicTitle academicTitle = AcademicTitle.builder().id(2L).build();
        oldMember.setAcademicTitle(academicTitle);
        Department department = new Department();
        department.setCurrentSecretary(oldMember);
        oldMember.setDepartment(department);

        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.of(oldMember));
        Mockito.when(scientificFieldRepository.findById(memberDto.getScientificFieldId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> memberService.update(memberDto));
        // Refactor lambda expression into a separate method
        Assertions.assertThrows(ResourceNotFoundException.class, () -> throwResourceNotFoundExceptionForScientificField(memberDto));

    }
    // Method to throw ResourceNotFoundException for academic title
    private void throwResourceNotFoundExceptionForAcademicTitle(MemberDto memberDto) {
        throw new ResourceNotFoundException("AcademicTitle with ID " + memberDto.getAcademicTitleId() + " not found.");
    }

    // Method to throw ResourceNotFoundException for scientific field
    private void throwResourceNotFoundExceptionForScientificField(MemberDto memberDto) {
        throw new ResourceNotFoundException("ScientificField with ID " + memberDto.getScientificFieldId() + " not found.");
    }

    // Method to throw ResourceNotFoundException for education title
    private void throwResourceNotFoundExceptionForEducationTitle(MemberDto memberDto) {
        throw new ResourceNotFoundException("EducationTitle with ID " + memberDto.getEducationTitleId() + " not found.");
    }



}
