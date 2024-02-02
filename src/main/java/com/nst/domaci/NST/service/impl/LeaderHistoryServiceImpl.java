package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.LeaderHistoryConverter;
import com.nst.domaci.NST.dto.LeaderHistoryDto;
import com.nst.domaci.NST.entity.LeaderHistory;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.DepartmentRepository;
import com.nst.domaci.NST.repository.LeaderHistoryRepository;
import com.nst.domaci.NST.repository.MemberRepository;
import com.nst.domaci.NST.service.LeaderHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeaderHistoryServiceImpl implements LeaderHistoryService {

    private final LeaderHistoryRepository leaderHistoryRepository;
    private final LeaderHistoryConverter leaderHistoryConverter;
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    public LeaderHistoryServiceImpl(LeaderHistoryRepository leaderHistoryRepository, LeaderHistoryConverter leaderHistoryConverter, MemberRepository memberRepository, DepartmentRepository departmentRepository) {
        this.leaderHistoryRepository = leaderHistoryRepository;
        this.leaderHistoryConverter = leaderHistoryConverter;
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<LeaderHistoryDto> findAll() {
        return leaderHistoryRepository.findAll()
                .stream().map(entity -> leaderHistoryConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaderHistoryDto> findAllByDepartmentId(Long departmentId) throws ResourceNotFoundException {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department with ID = " + departmentId + " does not exist."));
        return leaderHistoryRepository.findAllByDepartmentId(departmentId)
                .stream().map(entity -> leaderHistoryConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaderHistoryDto> findAllByMemberId(Long memberId) throws ResourceNotFoundException {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member with ID = " + memberId + " does not exist."));
        return leaderHistoryRepository.findAllByMemberId(memberId)
                .stream().map(entity -> leaderHistoryConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public LeaderHistoryDto findById(Long id) throws ResourceNotFoundException {
        Optional<LeaderHistory> result = leaderHistoryRepository.findById(id);
        if (result.isPresent()) {
            LeaderHistory leaderHistory = result.get();
            return leaderHistoryConverter.toDto(leaderHistory);
        } else {
            throw new ResourceNotFoundException("Leader with ID = " + id + " does not exist.");
        }
    }
}
