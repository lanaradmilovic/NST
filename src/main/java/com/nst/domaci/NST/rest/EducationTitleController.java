package com.nst.domaci.NST.rest;

import com.nst.domaci.NST.dto.EducationTitleDto;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.impl.EducationTitleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "EducationTitle Controller")
@RestController
@RequestMapping("/api/education-titles")
public class EducationTitleController {
    private final EducationTitleServiceImpl educationTitleService;

    public EducationTitleController(EducationTitleServiceImpl educationTitleService) {
        this.educationTitleService = educationTitleService;
    }

    @Operation(summary = "Retrieve all EducationTitle entities.")
    @GetMapping
    private ResponseEntity<List<EducationTitleDto>> findAll() {
        List<EducationTitleDto> educationTitles = educationTitleService.findAll();
        return new ResponseEntity<>(educationTitles, HttpStatus.OK);
    }
    @Operation(summary = "Retrieve EducationTitle entity by id.")
    @GetMapping("/{id}")
    private ResponseEntity<EducationTitleDto> findById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        EducationTitleDto educationTitleDto = educationTitleService.findById(id);
        return new ResponseEntity<>(educationTitleDto, HttpStatus.OK);
    }
    @Operation(summary = "Delete EducationTitle entity by id.")
    @DeleteMapping("/{id}")
    private ResponseEntity<String> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
        educationTitleService.delete(id);
        return new ResponseEntity<>("Education title with ID = " + id + " removed.", HttpStatus.OK);
    }
    @Operation(summary = "Generate a new EducationTitle entity.")
    @PostMapping
    private ResponseEntity<EducationTitleDto> save(@Valid @RequestBody EducationTitleDto educationTitleDto) throws EntityAlreadyExistsException {
        EducationTitleDto educationTitleDto1 = educationTitleService.save(educationTitleDto);
        return new ResponseEntity<>(educationTitleDto1, HttpStatus.OK);
    }
    @Operation(summary = "Modify EducationTitle entity.")
    @PutMapping
    private ResponseEntity<EducationTitleDto> update(@Valid @RequestBody EducationTitleDto educationTitleDto) throws ResourceNotFoundException {
        EducationTitleDto educationTitleDto1 = educationTitleService.update(educationTitleDto);
        return new ResponseEntity<>(educationTitleDto1, HttpStatus.OK);
    }

}
