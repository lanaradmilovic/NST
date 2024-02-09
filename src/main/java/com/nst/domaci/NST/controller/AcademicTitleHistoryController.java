package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.AcademicTitleHistoryDto;
import com.nst.domaci.NST.service.impl.AcademicTitleHistoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AcademicTitleHistoryController {
    private final AcademicTitleHistoryServiceImpl academicTitleHistoryService;

    public AcademicTitleHistoryController(AcademicTitleHistoryServiceImpl academicTitleHistoryService) {
        this.academicTitleHistoryService = academicTitleHistoryService;
    }

    @GetMapping("/academic-title-histories")
    public ResponseEntity<List<?>> findAll() {
        return new ResponseEntity<>(academicTitleHistoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/academic-title-histories/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(academicTitleHistoryService.findById(id), HttpStatus.OK);
    }

    @GetMapping("members/{id}/academic-title-histories")
    public ResponseEntity<List<AcademicTitleHistoryDto>> findAllByMemberId(@PathVariable("id") Long memberId) {
        return new ResponseEntity<>(academicTitleHistoryService.findAllByMemberId(memberId), HttpStatus.OK);
    }


}
