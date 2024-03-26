package com.nst.domaci.NST.service;

import com.nst.domaci.NST.converter.impl.AcademicTitleConverter;
import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.entity.AcademicTitle;
import com.nst.domaci.NST.entity.Department;
import com.nst.domaci.NST.entity.Member;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.AcademicTitleRepository;
import com.nst.domaci.NST.repository.MemberRepository;
import com.nst.domaci.NST.service.AcademicTitleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.*;

@SpringBootTest
public class AcademicTitleServiceTests {

    @Autowired
    private AcademicTitleService academicTitleService;
    @MockBean
    private AcademicTitleRepository academicTitleRepository;
    @MockBean
    private AcademicTitleConverter academicTitleConverter;
    @MockBean
    private MemberRepository memberRepository;

    @Test
    public void saveAcademicTitleSuccessTest() throws EntityAlreadyExistsException {
        AcademicTitleDto academicTitleDto = AcademicTitleDto.builder()
                .id(1L)
                .name("Associate Professor")
                .build();

        AcademicTitle academicTitleEntity = AcademicTitle.builder()
                .id(1L)
                .name("Associate Professor")
                .build();

        Mockito.when(academicTitleRepository.findByName(academicTitleDto.getName())).thenReturn(Optional.empty());

        Mockito.when(academicTitleConverter.toEntity(academicTitleDto)).thenReturn(academicTitleEntity);
        Mockito.when(academicTitleRepository.save(academicTitleEntity)).thenReturn(academicTitleEntity);
        Mockito.when(academicTitleConverter.toDto(academicTitleEntity)).thenReturn(academicTitleDto);

        AcademicTitleDto savedDto = academicTitleService.save(academicTitleDto);

        Assertions.assertNotNull(savedDto);
        Assertions.assertEquals(academicTitleDto, savedDto);
    }

    @Test
    public void saveAcademicTitleFailureTest() {
        AcademicTitleDto academicTitleDto = AcademicTitleDto.builder()
                .id(1L)
                .name("Associate Professor")
                .build();

        AcademicTitle academicTitleEntity = AcademicTitle.builder()
                .id(1L)
                .name("Associate Professor")
                .build();

        Mockito.when(academicTitleRepository.findByName(academicTitleDto.getName())).thenReturn(Optional.of(academicTitleEntity));
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> academicTitleService.save(academicTitleDto));

    }

    @Test
    public void findByIdSuccessTest() {
        Long id = 1L;
        AcademicTitleDto academicTitleDto = AcademicTitleDto.builder()
                .id(id)
                .name("Associate Professor")
                .build();

        AcademicTitle academicTitleEntity = AcademicTitle.builder()
                .id(id)
                .name("Associate Professor")
                .build();

        Mockito.when(academicTitleRepository.findById(id)).thenReturn(Optional.of(academicTitleEntity));
        Mockito.when(academicTitleConverter.toDto(academicTitleEntity)).thenReturn(academicTitleDto);

        AcademicTitleDto found = academicTitleService.findById(id);
        Assertions.assertEquals(academicTitleDto, found);
    }

    @Test
    public void findByIdFailureTest() {
        Long id = 1L;

        Mockito.when(academicTitleRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> academicTitleService.findById(id));
    }


    @Test
    void deleteSuccessTest() throws ResourceNotFoundException {
        Long id = 1L;
        AcademicTitle academicTitle = AcademicTitle.builder()
                .id(id)
                .name("Full professor")
                .build();
        Member member1 = Member.builder()
                .academicTitle(academicTitle)
                .firstName("John")
                .build();
        Member member2 = Member.builder()
                .academicTitle(academicTitle)
                .firstName("Ena")
                .build();
        Department department = Department.builder()
                .id(1L)
                .name("Department 1")
                .currentLeader(member1)
                .currentSecretary(member2)
                .build();
        member1.setDepartment(department);
        member2.setDepartment(department);

        List<Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);
        Mockito.when(academicTitleRepository.findById(id)).thenReturn(Optional.of(academicTitle));
        Mockito.when(memberRepository.findAllByAcademicTitleId(id)).thenReturn(members);

        academicTitleService.delete(id);

        Mockito.verify(academicTitleRepository, Mockito.times(1)).deleteById(id);
        Assertions.assertNull(department.getCurrentLeader());
        Assertions.assertNull(department.getCurrentSecretary());

    }
    @Test
    public void deleteEmptyResultList() throws ResourceNotFoundException {
        Long academicTitleId = 1L;
        AcademicTitle academicTitle = AcademicTitle.builder()
                .id(academicTitleId)
                .name("Full professor")
                .build();
        Mockito.when(academicTitleRepository.findById(academicTitleId)).thenReturn(Optional.of(academicTitle));
        Mockito.when(memberRepository.findAllByAcademicTitleId(academicTitleId)).thenReturn(Collections.emptyList());
        academicTitleService.delete(academicTitleId);
        Mockito.verify(academicTitleRepository, Mockito.times(1)).deleteById(academicTitleId);
    }

    @Test
    public void deleteFailureTest() {
        Long id = 1L;

        Mockito.when(academicTitleRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> academicTitleService.delete(id));
        // Verify that academicTitleRepository.delete() method was not called
        Mockito.verify(academicTitleRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void findAllTest() {
        AcademicTitle academicTitle1 = AcademicTitle.builder()
                .name("Full professor")
                .build();
        AcademicTitle academicTitle2 = AcademicTitle.builder()
                .name("Assistant")
                .build();
        List<AcademicTitle> academicTitles = Arrays.asList(academicTitle1, academicTitle2);

        Mockito.when(academicTitleRepository.findAll()).thenReturn(academicTitles);

        AcademicTitleDto academicTitleDto1 = AcademicTitleDto.builder()
                .name(academicTitle1.getName())
                .build();
        AcademicTitleDto academicTitleDto2 = AcademicTitleDto.builder()
                .name(academicTitle2.getName())
                .build();
        Mockito.when(academicTitleConverter.toDto(academicTitle1)).thenReturn(academicTitleDto1);
        Mockito.when(academicTitleConverter.toDto(academicTitle2)).thenReturn(academicTitleDto2);

        List<AcademicTitleDto> academicTitleDtos = academicTitleService.findAll();

        Assertions.assertNotNull(academicTitleDtos);
        Assertions.assertEquals(academicTitles.size(), academicTitleDtos.size());
        Assertions.assertTrue(academicTitleDtos.contains(academicTitleDto1));
        Assertions.assertTrue(academicTitleDtos.contains(academicTitleDto2));
    }

    @Test
    public void updateSuccessTest() throws ResourceNotFoundException {
        Long academicTitleId = 1L;
        String updatedName = "Assistant";

        AcademicTitleDto academicTitleDto = AcademicTitleDto.builder()
                .id(academicTitleId)
                .name(updatedName)
                .build();

        AcademicTitle academicTitle = AcademicTitle.builder()
                .id(academicTitleId)
                .name("Professor")
                .build();

        Mockito.when(academicTitleRepository.findById(academicTitleId)).thenReturn(Optional.of(academicTitle));
        academicTitle.setName(updatedName);

        Mockito.when(academicTitleRepository.save(academicTitle)).thenReturn(academicTitle);
        Mockito.when(academicTitleConverter.toDto(academicTitle)).thenReturn(academicTitleDto);

        AcademicTitleDto updated = academicTitleService.update(academicTitleDto);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(academicTitleDto, updated);
        Assertions.assertEquals(academicTitleDto.getName(), updated.getName());
    }

    @Test
    public void updateFailureTest() {
        AcademicTitleDto academicTitleDto1 = AcademicTitleDto.builder()
                .name("Assistant")
                .build();

        Mockito.when(academicTitleRepository.findById(academicTitleDto1.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> academicTitleService.update(academicTitleDto1));

    }
}
