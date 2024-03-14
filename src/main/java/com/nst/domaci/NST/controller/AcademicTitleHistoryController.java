package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.AcademicTitleHistoryDto;
import com.nst.domaci.NST.service.impl.AcademicTitleHistoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "AcademicTitleHistory Controller")
@RestController
@RequestMapping("/api")
public class AcademicTitleHistoryController {
    private final AcademicTitleHistoryServiceImpl academicTitleHistoryService;

    public AcademicTitleHistoryController(AcademicTitleHistoryServiceImpl academicTitleHistoryService) {
        this.academicTitleHistoryService = academicTitleHistoryService;
    }

    @Operation(summary = "Retrieve all AcademicTitleHistory entities.")
    @GetMapping("/academic-title-histories")
    public ResponseEntity<List<?>> findAll() {
        return new ResponseEntity<>(academicTitleHistoryService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve AcademicTitleHistory entity by id.")
    @GetMapping("/academic-title-histories/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(academicTitleHistoryService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all AcademicTitleHistory entities by Member Id.")
    @GetMapping("members/{id}/academic-title-histories")
    public ResponseEntity<List<AcademicTitleHistoryDto>> findAllByMemberId(@PathVariable("id") Long memberId) {
        return new ResponseEntity<>(academicTitleHistoryService.findAllByMemberId(memberId), HttpStatus.OK);
    }


}
