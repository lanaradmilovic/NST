package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.LeaderHistoryDto;
import com.nst.domaci.NST.service.impl.LeaderHistoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaderHistoryController {

    private final LeaderHistoryServiceImpl leaderHistoryService;

    public LeaderHistoryController(LeaderHistoryServiceImpl leaderHistoryService) {
        this.leaderHistoryService = leaderHistoryService;
    }

    @GetMapping("/leader-histories")
    public ResponseEntity<List<LeaderHistoryDto>> findAll(){
        return new ResponseEntity<>(leaderHistoryService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/members/{id}/leader-histories")
    public ResponseEntity<List<LeaderHistoryDto>> findAllByMemberId(@PathVariable("id") Long memberId){
        return new ResponseEntity<>(leaderHistoryService.findAllByMemberId(memberId), HttpStatus.OK);
    }
    @GetMapping("/departments/{id}/leader-histories")
    public ResponseEntity<List<LeaderHistoryDto>> findAllByDepartmentId(@PathVariable("id") Long departmentId){
        return new ResponseEntity<>(leaderHistoryService.findAllByDepartmentId(departmentId), HttpStatus.OK);
    }
    @GetMapping("/leader-histories/{id}")
    public ResponseEntity<LeaderHistoryDto> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(leaderHistoryService.findById(id), HttpStatus.OK);
    }

}
