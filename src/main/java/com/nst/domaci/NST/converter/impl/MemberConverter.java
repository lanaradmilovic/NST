package com.nst.domaci.NST.converter.impl;

import com.nst.domaci.NST.converter.DtoEntityConverter;
import com.nst.domaci.NST.dto.MemberDto;
import com.nst.domaci.NST.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter implements DtoEntityConverter<MemberDto, Member> {

    @Override
    public MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .departmentId(member.getDepartment().getId())
                .educationTitleId(member.getEducationTitle().getId())
                .academicTitleId(member.getAcademicTitle().getId())
                .scientificFieldId(member.getScientificField().getId())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .build();
    }

    @Override
    public Member toEntity(MemberDto memberDto) {
        Member member = Member.builder()
                .firstName(memberDto.getFirstName())
                .lastName(memberDto.getLastName())
                .build();

        return member;
    }

}
