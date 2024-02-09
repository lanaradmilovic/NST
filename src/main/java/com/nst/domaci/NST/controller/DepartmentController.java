package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.DepartmentDto;
import com.nst.domaci.NST.service.impl.DepartmentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentServiceImpl departmentService;

    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(departmentService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        departmentService.delete(id);
        return new ResponseEntity<>("Department removed.", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> save(@Valid @RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<>(departmentService.save(departmentDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<DepartmentDto> update(@Valid @RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<>(departmentService.update(departmentDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{departmentId}/leader/{memberId}")
    public ResponseEntity<DepartmentDto> setDepartmentLeader(
            @PathVariable("departmentId") Long departmentId,
            @PathVariable("memberId") Long memberId) {
       departmentService.setLeader(departmentId, memberId);
       return ResponseEntity.ok().build();
    }

    @PostMapping("/{departmentId}/secretary/{memberId}")
    public ResponseEntity<?> setDepartmentSecretary(
            @PathVariable("departmentId") Long departmentId,
            @PathVariable("memberId") Long memberId)  {
        departmentService.setSecretary(departmentId, memberId);
        return ResponseEntity.ok().build();
    }

}
