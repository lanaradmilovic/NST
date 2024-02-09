package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.SubjectDto;
import com.nst.domaci.NST.service.impl.SubjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {
    private final SubjectServiceImpl subjectService;

    public SubjectController(SubjectServiceImpl subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<?>> findAll() {
        return new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/departments/{departmentId}/subjects")
    public ResponseEntity<List<SubjectDto>> findAllByDepartmentId(
            @PathVariable(value = "departmentId") Long departmentId) {
        return new ResponseEntity<>(subjectService.findAllByDepartmentId(departmentId), HttpStatus.OK);
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(subjectService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/subjects")
    public ResponseEntity<SubjectDto> save(@RequestBody SubjectDto subjectDto) {
        return new ResponseEntity<>(subjectService.save(subjectDto), HttpStatus.CREATED);
    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<SubjectDto> update(@RequestBody SubjectDto subjectDto) {
        return new ResponseEntity<>(subjectService.update(subjectDto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        subjectService.delete(id);
        return new ResponseEntity<>("Subject removed.", HttpStatus.OK);
    }


}
