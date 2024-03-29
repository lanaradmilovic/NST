package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.dto.MemberDto;
import com.nst.domaci.NST.dto.ScientificFieldDto;
import com.nst.domaci.NST.entity.Member;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;

import java.util.List;

public interface MemberService {
    List<Member> findAll();

    Member findById(Long id) throws ResourceNotFoundException;

    List<MemberDto> findAllByDepartmentId(Long departmentId);

    void delete(Long id) throws ResourceNotFoundException;

    MemberDto save(MemberDto memberDto) throws EntityAlreadyExistsException;

    MemberDto update(MemberDto memberDto) throws ResourceNotFoundException;

    MemberDto updateAcademicTitle(MemberDto memberDto, AcademicTitleDto newTitle, ScientificFieldDto newField);
}
