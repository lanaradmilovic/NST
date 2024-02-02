package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.EngagementDto;
import com.nst.domaci.NST.service.impl.EngagementServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EngagementController {
    private final EngagementServiceImpl engagementService;

    public EngagementController(EngagementServiceImpl engagementService) {
        this.engagementService = engagementService;
    }
    @GetMapping("/engagements")
    public ResponseEntity<List<EngagementDto>> findAll(){
        return new ResponseEntity<>(engagementService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/engagements/{id}")
    public ResponseEntity<EngagementDto> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(engagementService.findById(id), HttpStatus.OK);
    }
    @GetMapping("/members/{id}/engagements")
    public ResponseEntity<List<EngagementDto>> findAllByMemberId(@PathVariable("id") Long memberId){
        return new ResponseEntity<>(engagementService.findAllByMemberId(memberId), HttpStatus.OK);
    }
    @GetMapping("/subjects/{id}/engagements")
    public ResponseEntity<List<EngagementDto>> findAllBySubjectId(@PathVariable("id") Long subjectId){
        return new ResponseEntity<>(engagementService.findAllBySubjectId(subjectId), HttpStatus.OK);
    }

    @DeleteMapping("/engagements/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        engagementService.delete(id);
        return new ResponseEntity<>("Engagement removed.", HttpStatus.OK);
    }
    @PostMapping("/engagements")
    public ResponseEntity<EngagementDto> save(@Valid @RequestBody EngagementDto engagementDto){
        return new ResponseEntity<>(engagementService.save(engagementDto), HttpStatus.OK);
    }
    @PutMapping("/engagements")
    public ResponseEntity<EngagementDto> update(@Valid @RequestBody EngagementDto engagementDto){
        return new ResponseEntity<>(engagementService.update(engagementDto), HttpStatus.OK);
    }
}
