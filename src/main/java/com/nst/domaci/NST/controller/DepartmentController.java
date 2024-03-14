package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.DepartmentDto;
import com.nst.domaci.NST.service.impl.DepartmentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Department Controller")
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentServiceImpl departmentService;

    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(summary = "Retrieve all Department entities.")
    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        return new ResponseEntity<>(departmentService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve Department entity by id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(departmentService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Delete Department entity by id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        departmentService.delete(id);
        return new ResponseEntity<>("Department with ID = " + id + " removed.", HttpStatus.OK);
    }

    @Operation(summary = "Generate a new Department entity.")
    @PostMapping
    public ResponseEntity<DepartmentDto> save(@Valid @RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<>(departmentService.save(departmentDto), HttpStatus.OK);
    }

    @Operation(summary = "Modify Department entity.")
    @PutMapping
    public ResponseEntity<DepartmentDto> update(@Valid @RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<>(departmentService.update(departmentDto), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Set Department Leader.",
            description = "Assign a member as the leader of the specified department.")
    @PostMapping("/{departmentId}/leader/{memberId}")
    public ResponseEntity<DepartmentDto> setDepartmentLeader(
            @PathVariable("departmentId") Long departmentId,
            @PathVariable("memberId") Long memberId) {
        departmentService.setLeader(departmentId, memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Set Department Secretary.",
            description = "Assign a member as the secretary of the specified department.")
    @PostMapping("/{departmentId}/secretary/{memberId}")
    public ResponseEntity<?> setDepartmentSecretary(
            @PathVariable("departmentId") Long departmentId,
            @PathVariable("memberId") Long memberId) {
        departmentService.setSecretary(departmentId, memberId);
        return ResponseEntity.ok().build();
    }

}
