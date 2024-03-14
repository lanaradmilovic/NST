package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.SecretaryHistoryDto;
import com.nst.domaci.NST.service.impl.SecretaryHistoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "SecretaryHistory Controller")
@RestController
@RequestMapping("/api")
public class SecretaryHistoryController {
    private final SecretaryHistoryServiceImpl secretaryHistoryService;

    public SecretaryHistoryController(SecretaryHistoryServiceImpl secretaryHistoryService) {
        this.secretaryHistoryService = secretaryHistoryService;
    }
    @Operation(summary = "Retrieve all secretary histories")
    @GetMapping("/secretary-histories")
    public ResponseEntity<List<SecretaryHistoryDto>> findAll(){
        return new ResponseEntity<>(secretaryHistoryService.findAll(), HttpStatus.OK);
    }
    @Operation(summary = "Retrieve secretary histories by Member ID")
    @GetMapping("/members/{id}/secretary-histories")
    public ResponseEntity<List<SecretaryHistoryDto>> findAllByMemberId(@PathVariable("id") Long memberId){
        return new ResponseEntity<>(secretaryHistoryService.findAllByMemberId(memberId), HttpStatus.OK);
    }
    @Operation(summary = "Retrieve secretary histories by Department ID")
    @GetMapping("/departments/{id}/secretary-histories")
    public ResponseEntity<List<SecretaryHistoryDto>> findAllByDepartmentId(@PathVariable("id") Long departmentId){
        return new ResponseEntity<>(secretaryHistoryService.findAllByDepartmentId(departmentId), HttpStatus.OK);
    }
    @Operation(summary = "Retrieve secretary history by ID")
    @GetMapping("/secretary-histories/{id}")
    public ResponseEntity<SecretaryHistoryDto> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(secretaryHistoryService.findById(id), HttpStatus.OK);
    }
}
