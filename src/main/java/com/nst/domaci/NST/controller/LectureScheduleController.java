package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.LectureScheduleDto;
import com.nst.domaci.NST.entity.LectureSchedule;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.impl.LectureScheduleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "LectureSchedule Controller")
@RestController
@RequestMapping("/api")
public class LectureScheduleController {

    private final LectureScheduleServiceImpl lectureScheduleService;

    public LectureScheduleController(LectureScheduleServiceImpl lectureScheduleService) {
        this.lectureScheduleService = lectureScheduleService;
    }
    @Operation(summary = "Retrieve all lecture schedules")
    @GetMapping("/lecture-schedules")
    public ResponseEntity<List<?>> findAll() {
        List<LectureSchedule> schedules = lectureScheduleService.findAll();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve a specific lecture schedule by ID")
    @GetMapping("/lecture-schedules/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws ResourceNotFoundException {
        LectureSchedule schedule = lectureScheduleService.findById(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all lecture schedules for a specific subject and year")
    @GetMapping("/subject/{subjectId}/year/{year}/lecture-schedules")
    public ResponseEntity<List<LectureSchedule>> findBySubjectIdAndYear(
            @PathVariable Long subjectId, @PathVariable int year) {
        List<LectureSchedule> schedules = lectureScheduleService.findAllBySubjectIdAndScheduleYear(subjectId, year);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @Operation(summary = "Delete a lecture schedule by ID")
    @DeleteMapping("/lecture-schedules/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        lectureScheduleService.findById(id);
        lectureScheduleService.delete(id);
        return new ResponseEntity<>("Lecture schedule removed.", HttpStatus.OK);
    }

    //    @PostMapping("/lecture-schedules/{lectureScheduleId}/lectures")
//    public ResponseEntity<Void> addLectureToSchedule(
//            @PathVariable Long lectureScheduleId,
//            @RequestParam Long lectureId) {
//        lectureScheduleService.addLectureToSchedule(lectureScheduleId, lectureId);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//
//    }
    @Operation(summary = "Create a new lecture schedule")
    @PostMapping("/lecture-schedules")
    public ResponseEntity<?> save(@RequestBody LectureScheduleDto lectureSchedule) {
        lectureScheduleService.save(lectureSchedule);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}

