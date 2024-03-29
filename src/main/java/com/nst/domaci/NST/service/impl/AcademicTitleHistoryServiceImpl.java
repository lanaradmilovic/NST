package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.AcademicTitleHistoryConverter;
import com.nst.domaci.NST.dto.AcademicTitleHistoryDto;
import com.nst.domaci.NST.entity.AcademicTitleHistory;
import com.nst.domaci.NST.entity.Member;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.AcademicTitleHistoryRepository;
import com.nst.domaci.NST.repository.MemberRepository;
import com.nst.domaci.NST.service.AcademicTitleHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AcademicTitleHistoryServiceImpl implements AcademicTitleHistoryService {
    private final AcademicTitleHistoryRepository academicTitleRepository;
    private final AcademicTitleHistoryConverter academicTitleConverter;
    private final MemberRepository memberRepository;

    public AcademicTitleHistoryServiceImpl(AcademicTitleHistoryRepository academicTitleRepository, AcademicTitleHistoryConverter academicTitleConverter, MemberRepository memberRepository) {
        this.academicTitleRepository = academicTitleRepository;
        this.academicTitleConverter = academicTitleConverter;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<AcademicTitleHistory> findAll() {
        return academicTitleRepository.findAll();
    }

    @Override
    public List<AcademicTitleHistoryDto> findAllByMemberId(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + memberId));
        return academicTitleRepository.findAllByMemberId(memberId)
                .stream().map(entity -> academicTitleConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public AcademicTitleHistory findById(Long id) {
        Optional<AcademicTitleHistory> result = academicTitleRepository.findById(id);
        if (result.isPresent()) {
            AcademicTitleHistory academicTitleHistory = result.get();
            return result.get();
        } else {
            throw new ResourceNotFoundException("Academic title history with ID = " + id + " does not exist.");
        }
    }

}
