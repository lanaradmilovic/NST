package com.nst.domaci.NST.rest;

import com.nst.domaci.NST.dto.FundDto;
import com.nst.domaci.NST.dto.SubjectDto;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.impl.SubjectServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Subject Controller")
@RestController
@RequestMapping("/api")
public class SubjectController {
    private final SubjectServiceImpl subjectService;

    public SubjectController(SubjectServiceImpl subjectService) {
        this.subjectService = subjectService;
    }

    @Operation(summary = "Retrieve all Subject entities.")
    @GetMapping("/subjects")
    public ResponseEntity<List<?>> findAll() {
        return new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve Subjects of a Department.",
            description = "Fetches a list of subjects associated with the specified department based on the provided department ID."
    )
    @GetMapping("/departments/{departmentId}/subjects")
    public ResponseEntity<List<SubjectDto>> findAllByDepartmentId(
            @PathVariable(value = "departmentId") Long departmentId) {
        return new ResponseEntity<>(subjectService.findAllByDepartmentId(departmentId), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve Subject entity by id.")
    @GetMapping("/subjects/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(subjectService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Generate a new Subject entity.")
    @PostMapping("/subjects")
    public ResponseEntity<SubjectDto> save(@RequestBody SubjectDto subjectDto) {
        return new ResponseEntity<>(subjectService.save(subjectDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Modify Subject entity.")
    @PutMapping("/subjects/{id}")
    public ResponseEntity<SubjectDto> update(@RequestBody SubjectDto subjectDto) {
        return new ResponseEntity<>(subjectService.update(subjectDto), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete Subject entity by id.")
    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        try {
            subjectService.delete(id);
            return new ResponseEntity<>("Subject with ID = " + id + " removed.", HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Set fund to Subject.")
    @PostMapping("/subjects/{subjectId}/fund")
    public ResponseEntity<?> setFund(@PathVariable(value = "subjectId") Long subjectId, @RequestBody FundDto fundDto) {
        return new ResponseEntity<>(subjectService.saveFund(subjectId, fundDto), HttpStatus.CREATED);
    }


}
