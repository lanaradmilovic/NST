package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.impl.AcademicTitleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "AcademicTitle Controller")
@RestController
@RequestMapping("/api/academic-titles")
public class AcademicTitleController {
    private final AcademicTitleServiceImpl academicTitleService;


    public AcademicTitleController(AcademicTitleServiceImpl academicTitleService) {
        this.academicTitleService = academicTitleService;
    }

    @Operation(summary = "Retrieve all AcademicTitle entities.")
    @GetMapping
    public ResponseEntity<List<AcademicTitleDto>> findAll() {
        return new ResponseEntity<>(academicTitleService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve AcademicTitle entity by id.")
    @GetMapping("/{id}")
    public ResponseEntity<AcademicTitleDto> findById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(academicTitleService.findById(id), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Delete AcademicTitle entity by id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            academicTitleService.delete(id);
            return new ResponseEntity<>("Academic title with ID = " + id + " removed.", HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
    }

    @Operation(summary = "Generate a new AcademicTitle entity.")
    @PostMapping
    public ResponseEntity<AcademicTitleDto> save(@Valid @RequestBody AcademicTitleDto academicTitleDto) {
        try {
            return new ResponseEntity<>(academicTitleService.save(academicTitleDto), HttpStatus.OK);
        } catch (EntityAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Modify AcademicTitle entity.")
    @PutMapping
    public ResponseEntity<AcademicTitleDto> update(@Valid @RequestBody AcademicTitleDto academicTitleDto) {
        try {
            return new ResponseEntity<>(academicTitleService.update(academicTitleDto), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}
