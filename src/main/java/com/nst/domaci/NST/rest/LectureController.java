package com.nst.domaci.NST.rest;

import com.nst.domaci.NST.dto.LectureDto;
import com.nst.domaci.NST.entity.Lecture;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.impl.LectureServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lecture Controller")
@RestController
@RequestMapping("/api")
public class LectureController {

    private final LectureServiceImpl lectureService;

    public LectureController(LectureServiceImpl lectureService) {
        this.lectureService = lectureService;
    }

    @Operation(summary = "Retrieve all lectures")
    @GetMapping("/lectures")
    public ResponseEntity<List<Lecture>> findAll() {
        List<Lecture> lectures = lectureService.findAll();
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve a specific lecture by ID")
    @GetMapping("/lectures/{id}")
    public ResponseEntity<Lecture> findById(@PathVariable Long id) throws ResourceNotFoundException {
        Lecture lecture = lectureService.findById(id);
        return new ResponseEntity<>(lecture, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all lectures by engagement ID")
    @GetMapping("/engagements/{engagementId}/lectures")
    public ResponseEntity<List<Lecture>> findByEngagementId(@PathVariable Long engagementId) {
        List<Lecture> lectures = lectureService.findAllByEngagementId(engagementId);
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }
    @Operation(summary = "Retrieve all lectures by member ID and year")
    @GetMapping("/members/{memberId}/year/{year}/lectures")
    public ResponseEntity<List<Lecture>> findByEngagementMemberIdAndEngagementYear(
            @PathVariable Long memberId, @PathVariable Long year) {
        List<Lecture> lectures = lectureService.findAllByEngagementMemberIdAndEngagementYear(memberId, year);
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }
    @Operation(summary = "Retrieve all lectures by subject ID and year")
    @GetMapping("/subjects/{subjectId}/year/{year}/lectures")
    public ResponseEntity<List<Lecture>> findByEngagementSubjectIdAndYear(
            @PathVariable Long subjectId, @PathVariable Long year) {
        List<Lecture> lectures = lectureService.findAllByEngagementSubjectIdAndEngagementYear(subjectId, year);
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }

    @Operation(summary = "Create a new lecture")
    @PostMapping("/lectures")
    public ResponseEntity<LectureDto> save(@RequestBody LectureDto lectureDto) {
        LectureDto savedLecture = lectureService.save(lectureDto);
        return new ResponseEntity<>(savedLecture, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing lecture")
    @PutMapping("/lectures")
    public ResponseEntity<LectureDto> update(@RequestBody LectureDto lectureDto)
            throws ResourceNotFoundException {
        LectureDto updatedLecture = lectureService.update(lectureDto);
        return new ResponseEntity<>(updatedLecture, HttpStatus.OK);
    }

    @Operation(summary = "Delete a lecture by ID")
    @DeleteMapping("/lectures/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        lectureService.findById(id);
        lectureService.delete(id);
        return new ResponseEntity<>("Lecture with ID = " + id + " removed.", HttpStatus.OK);
    }
}

