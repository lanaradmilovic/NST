package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.LectureDto;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.LectureService;
import com.nst.domaci.NST.service.impl.LectureServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LectureController {

    private final LectureServiceImpl lectureService;

    public LectureController(LectureServiceImpl lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/lectures")
    public ResponseEntity<List<LectureDto>> findAll() {
        List<LectureDto> lectures = lectureService.findAll();
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }

    @GetMapping("/lectures/{id}")
    public ResponseEntity<LectureDto> findById(@PathVariable Long id) throws ResourceNotFoundException {
        LectureDto lecture = lectureService.findById(id);
        return new ResponseEntity<>(lecture, HttpStatus.OK);
    }

    @GetMapping("/engagements/{engagementId}/lectures")
    public ResponseEntity<List<LectureDto>> findByEngagementId(@PathVariable Long engagementId) {
        List<LectureDto> lectures = lectureService.findAllByEngagementId(engagementId);
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }

    @GetMapping("/members/{memberId}/engagements/year/{year}/lectures")
    public ResponseEntity<List<LectureDto>> findByEngagementMemberIdAndYear(
            @PathVariable Long memberId, @PathVariable Long year) {
        List<LectureDto> lectures = lectureService.findAllByEngagementMemberIdAndEngagementYear(memberId, year);
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }

    @GetMapping("/subjects/{subjectId}/engagements/year/{year}/lectures")
    public ResponseEntity<List<LectureDto>> findByEngagementSubjectIdAndYear(
            @PathVariable Long subjectId, @PathVariable Long year) {
        List<LectureDto> lectures = lectureService.findAllByEngagementSubjectIdAndEngagementYear(subjectId, year);
        return new ResponseEntity<>(lectures, HttpStatus.OK);
    }

    @PostMapping("/lectures")
    public ResponseEntity<LectureDto> save(@RequestBody LectureDto lectureDto) {
        LectureDto savedLecture = lectureService.save(lectureDto);
        return new ResponseEntity<>(savedLecture, HttpStatus.CREATED);
    }

    @PutMapping("/lectures")
    public ResponseEntity<LectureDto> update(@RequestBody LectureDto lectureDto)
            throws ResourceNotFoundException {
        LectureDto updatedLecture = lectureService.update(lectureDto);
        return new ResponseEntity<>(updatedLecture, HttpStatus.OK);
    }

    @DeleteMapping("/lectures/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        lectureService.delete(id);
        return new ResponseEntity<>("Lecture removed.",HttpStatus.NO_CONTENT);
    }
}

