package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.EducationTitleDto;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.impl.EducationTitleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education-titles")
public class EducationTitleController {
    private final EducationTitleServiceImpl educationTitleService;

    public EducationTitleController(EducationTitleServiceImpl educationTitleService) {
        this.educationTitleService = educationTitleService;
    }

    @GetMapping
    private ResponseEntity<List<EducationTitleDto>> findAll() {
        List<EducationTitleDto> educationTitles = educationTitleService.findAll();
        return new ResponseEntity<>(educationTitles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<EducationTitleDto> findById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        EducationTitleDto educationTitleDto = educationTitleService.findById(id);
        return new ResponseEntity<>(educationTitleDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
        educationTitleService.delete(id);
        return new ResponseEntity<>("Education title with ID = " + id + " removed.", HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<EducationTitleDto> save(@Valid @RequestBody EducationTitleDto educationTitleDto) throws EntityAlreadyExistsException {
        EducationTitleDto educationTitleDto1 = educationTitleService.save(educationTitleDto);
        return new ResponseEntity<>(educationTitleDto1, HttpStatus.OK);
    }

    @PutMapping
    private ResponseEntity<EducationTitleDto> update(@Valid @RequestBody EducationTitleDto educationTitleDto) throws ResourceNotFoundException {
        EducationTitleDto educationTitleDto1 = educationTitleService.update(educationTitleDto);
        return new ResponseEntity<>(educationTitleDto1, HttpStatus.OK);
    }

}
