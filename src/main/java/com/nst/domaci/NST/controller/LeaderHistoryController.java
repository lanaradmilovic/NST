package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.LeaderHistoryDto;
import com.nst.domaci.NST.service.impl.LeaderHistoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "LeaderHistory Controller")
@RestController
@RequestMapping("/api")
public class LeaderHistoryController {

    private final LeaderHistoryServiceImpl leaderHistoryService;

    public LeaderHistoryController(LeaderHistoryServiceImpl leaderHistoryService) {
        this.leaderHistoryService = leaderHistoryService;
    }

    @Operation(summary = "Retrieve all leader histories")
    @GetMapping("/leader-histories")
    public ResponseEntity<List<LeaderHistoryDto>> findAll(){
        return new ResponseEntity<>(leaderHistoryService.findAll(), HttpStatus.OK);
    }
    @Operation(summary = "Retrieve leader histories by Member ID")
    @GetMapping("/members/{id}/leader-histories")
    public ResponseEntity<List<LeaderHistoryDto>> findAllByMemberId(@PathVariable("id") Long memberId){
        return new ResponseEntity<>(leaderHistoryService.findAllByMemberId(memberId), HttpStatus.OK);
    }
    @Operation(summary = "Retrieve leader histories by Department ID")
    @GetMapping("/departments/{id}/leader-histories")
    public ResponseEntity<List<LeaderHistoryDto>> findAllByDepartmentId(@PathVariable("id") Long departmentId){
        return new ResponseEntity<>(leaderHistoryService.findAllByDepartmentId(departmentId), HttpStatus.OK);
    }
    @Operation(summary = "Retrieve leader history by ID")
    @GetMapping("/leader-histories/{id}")
    public ResponseEntity<LeaderHistoryDto> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(leaderHistoryService.findById(id), HttpStatus.OK);
    }

}
