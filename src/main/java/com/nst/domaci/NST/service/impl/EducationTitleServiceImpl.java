package com.nst.domaci.NST.service.impl;

import com.nst.domaci.NST.converter.impl.EducationTitleConverter;
import com.nst.domaci.NST.dto.EducationTitleDto;
import com.nst.domaci.NST.entity.EducationTitle;
import com.nst.domaci.NST.entity.Member;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.IllegalArgumentException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.repository.EducationTitleRepository;
import com.nst.domaci.NST.repository.MemberRepository;
import com.nst.domaci.NST.service.EducationTitleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EducationTitleServiceImpl implements EducationTitleService {
    private final EducationTitleRepository educationTitleRepository;
    private final EducationTitleConverter educationTitleConverter;
    private final MemberRepository memberRepository;

    public EducationTitleServiceImpl(EducationTitleRepository educationTitleRepository, EducationTitleConverter educationTitleConverter, MemberRepository memberRepository) {
        this.educationTitleRepository = educationTitleRepository;
        this.educationTitleConverter = educationTitleConverter;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<EducationTitleDto> findAll() {
        return educationTitleRepository
                .findAll()
                .stream().map(entity -> educationTitleConverter.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public EducationTitleDto findById(Long id) throws ResourceNotFoundException {
        Optional<EducationTitle> result = educationTitleRepository.findById(id);
        if (result.isPresent()) {
            EducationTitle educationTitle = result.get();
            return educationTitleConverter.toDto(educationTitle);
        } else {
            throw new ResourceNotFoundException("Education title with ID = " + id + " does not exist.");
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        findById(id);

        List<Member> result = memberRepository.findAllByEducationTitleId(id);
        if (!result.isEmpty()) {
            for (Member member : result) {
                Member currentLeader = member.getDepartment().getCurrentLeader();
                Member currentSecretary = member.getDepartment().getCurrentSecretary();
                // check if member is currently secretary or leader of some department
                if (member.equals(currentLeader)) {
                    member.getDepartment().setCurrentLeader(null);
                }
                if (member.equals(currentSecretary)) {
                    member.getDepartment().setCurrentSecretary(null);
                }
            }
        }

        educationTitleRepository.deleteById(id);
    }

    @Override
    public EducationTitleDto save(EducationTitleDto educationTitleDto) {
        Optional<EducationTitle> result = educationTitleRepository.findByName(educationTitleDto.getName());
        if (result.isPresent()) {
            throw new EntityAlreadyExistsException("Education title with name = " + educationTitleDto.getName() + " already exists.");
        } else {
            EducationTitle educationTitle = educationTitleConverter.toEntity(educationTitleDto);
            educationTitle = educationTitleRepository.save(educationTitle);
            return educationTitleConverter.toDto(educationTitle);
        }
    }

    @Override
    public EducationTitleDto update(EducationTitleDto educationTitleDto) {
        Optional<EducationTitle> result = educationTitleRepository.findById(educationTitleDto.getId());
        if (result.isPresent()) {
            EducationTitle educationTitle = result.get();
            educationTitle.setName(educationTitleDto.getName());
            educationTitle = educationTitleRepository.save(educationTitle);
            return educationTitleConverter.toDto(educationTitle);
        } else {
            throw new ResourceNotFoundException("Education title with ID = " + educationTitleDto.getId() + " does not exist.");
        }
    }
}
