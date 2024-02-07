package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.LectureScheduleDto;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.LectureScheduleService;
import com.nst.domaci.NST.service.impl.LectureScheduleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LectureScheduleController {

    private final LectureScheduleServiceImpl lectureScheduleService;

    public LectureScheduleController(LectureScheduleServiceImpl lectureScheduleService) {
        this.lectureScheduleService = lectureScheduleService;
    }

    @GetMapping("/lecture-schedules")
    public ResponseEntity<List<LectureScheduleDto>> findAll() {
        List<LectureScheduleDto> schedules = lectureScheduleService.findAll();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/lecture-schedules/{id}")
    public ResponseEntity<LectureScheduleDto> findById(@PathVariable Long id) throws ResourceNotFoundException {
        LectureScheduleDto schedule = lectureScheduleService.findById(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @GetMapping("/subject/{subjectId}/lecture-schedules/year/{year}")
    public ResponseEntity<List<LectureScheduleDto>> findBySubjectIdAndYear(
            @PathVariable Long subjectId, @PathVariable int year) {
        List<LectureScheduleDto> schedules = lectureScheduleService.findAllBySubjectIdAndYear(subjectId, year);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @DeleteMapping("/lecture-schedules/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        lectureScheduleService.delete(id);
        return new ResponseEntity<>("Lecture schedule removed.",HttpStatus.NO_CONTENT);
    }
}

