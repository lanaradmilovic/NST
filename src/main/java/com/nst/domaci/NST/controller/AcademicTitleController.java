package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.service.impl.AcademicTitleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-titles")
public class AcademicTitleController {
    private final AcademicTitleServiceImpl academicTitleService;


    public AcademicTitleController(AcademicTitleServiceImpl academicTitleService) {
        this.academicTitleService = academicTitleService;
    }

    @GetMapping
    public ResponseEntity<List<AcademicTitleDto>> findAll(){
        return new ResponseEntity<>(academicTitleService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AcademicTitleDto> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(academicTitleService.findById(id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        academicTitleService.delete(id);
        return new ResponseEntity<>("Academic title with ID = " + id + " removed.", HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<AcademicTitleDto> save(@Valid @RequestBody AcademicTitleDto academicTitleDto){
        return new ResponseEntity<>(academicTitleService.save(academicTitleDto), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<AcademicTitleDto> update(@Valid @RequestBody AcademicTitleDto academicTitleDto){
        return new ResponseEntity<>(academicTitleService.update(academicTitleDto), HttpStatus.OK);
    }

}
