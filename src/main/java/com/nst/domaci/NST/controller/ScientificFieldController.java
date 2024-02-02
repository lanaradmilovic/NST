package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.ScientificFieldDto;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.impl.ScientificFieldServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scientific-fields")
public class ScientificFieldController {
    private final ScientificFieldServiceImpl scientificFieldService;

    public ScientificFieldController(ScientificFieldServiceImpl scientificFieldService) {
        this.scientificFieldService = scientificFieldService;
    }

    @GetMapping
    private ResponseEntity<List<ScientificFieldDto>> findAll(){
        List<ScientificFieldDto> educationTitles = scientificFieldService.findAll();
        return new ResponseEntity<>(educationTitles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ScientificFieldDto> findById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        ScientificFieldDto ScientificFieldDto = scientificFieldService.findById(id);
        return new ResponseEntity<>(ScientificFieldDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> delete(@PathVariable("id") Long id) throws ResourceNotFoundException{
        scientificFieldService.delete(id);
        return new ResponseEntity<>("Scientific field removed.", HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<ScientificFieldDto> save(@Valid @RequestBody ScientificFieldDto ScientificFieldDto) throws EntityAlreadyExistsException{
        ScientificFieldDto ScientificFieldDto1 = scientificFieldService.save(ScientificFieldDto);
        return new ResponseEntity<>(ScientificFieldDto1, HttpStatus.OK);
    }

    @PutMapping
    private ResponseEntity<ScientificFieldDto> update(@Valid @RequestBody ScientificFieldDto ScientificFieldDto) throws ResourceNotFoundException {
        ScientificFieldDto ScientificFieldDto1 = scientificFieldService.update(ScientificFieldDto);
        return new ResponseEntity<>(ScientificFieldDto1, HttpStatus.OK);
    }

}
