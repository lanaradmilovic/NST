package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.SecretaryHistoryConverter;
import com.nst.domaci.NST.dto.SecretaryHistoryDto;
import com.nst.domaci.NST.entity.SecretaryHistory;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.DepartmentRepository;
import com.nst.domaci.NST.repository.MemberRepository;
import com.nst.domaci.NST.repository.SecretaryHistoryRepository;
import com.nst.domaci.NST.service.SecretaryHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SecretaryHistoryServiceImpl implements SecretaryHistoryService {
    private final SecretaryHistoryRepository secretaryHistoryRepository;
    private final SecretaryHistoryConverter secretaryHistoryConverter;
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    public SecretaryHistoryServiceImpl(SecretaryHistoryRepository secretaryHistoryRepository, SecretaryHistoryConverter secretaryHistoryConverter, MemberRepository memberRepository, DepartmentRepository departmentRepository) {
        this.secretaryHistoryRepository = secretaryHistoryRepository;
        this.secretaryHistoryConverter = secretaryHistoryConverter;
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<SecretaryHistoryDto> findAll() {
        return secretaryHistoryRepository.findAll()
                .stream().map(entity -> secretaryHistoryConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<SecretaryHistoryDto> findAllByDepartmentId(Long departmentId) throws ResourceNotFoundException {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department with ID = " + departmentId + " does not exist."));
        return secretaryHistoryRepository.findAllByDepartmentId(departmentId)
                .stream().map(entity -> secretaryHistoryConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<SecretaryHistoryDto> findAllByMemberId(Long memberId) throws ResourceNotFoundException {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with ID = " + memberId + " does not exist."));
        return secretaryHistoryRepository.findAllByMemberId(memberId)
                .stream().map(entity -> secretaryHistoryConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public SecretaryHistoryDto findById(Long id) {
        Optional<SecretaryHistory> result = secretaryHistoryRepository.findById(id);
        if (result.isPresent()) {
            SecretaryHistory leaderHistory = result.get();
            return secretaryHistoryConverter.toDto(leaderHistory);
        } else {
            throw new ResourceNotFoundException("Secretary with ID = " + id + " does not exist.");
        }
    }
}
