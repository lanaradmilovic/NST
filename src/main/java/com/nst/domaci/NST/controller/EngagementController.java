package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.EngagementDto;
import com.nst.domaci.NST.service.impl.EngagementServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Engagement Controller")
@RestController
@RequestMapping("/api")
public class EngagementController {
    private final EngagementServiceImpl engagementService;

    public EngagementController(EngagementServiceImpl engagementService) {
        this.engagementService = engagementService;
    }

    @Operation(summary = "Retrieve all Engagement entities.")
    @GetMapping("/engagements")
    public ResponseEntity<List<?>> findAll() {
        return new ResponseEntity<>(engagementService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve Engagement entity by id.")
    @GetMapping("/engagements/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(engagementService.findById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve Engagements by Member ID",
            description = "Fetches a list of engagements associated with the specified member based on the provided member ID."
    )
    @GetMapping("/members/{id}/engagements")
    public ResponseEntity<List<EngagementDto>> findAllByMemberId(@PathVariable("id") Long memberId) {
        return new ResponseEntity<>(engagementService.findAllByMemberId(memberId), HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve Engagements by Subject ID",
            description = "Fetches a list of engagements associated with the specified subject based on the provided subject ID."
    )
    @GetMapping("/subjects/{id}/engagements")
    public ResponseEntity<List<EngagementDto>> findAllBySubjectId(@PathVariable("id") Long subjectId) {
        return new ResponseEntity<>(engagementService.findAllBySubjectId(subjectId), HttpStatus.OK);
    }
    @Operation(
            summary = "Retrieve Engagements by Member and Subject ID",
            description = "Fetches a list of engagements associated with the specified member and subject based on the provided member and subject IDs. Results are ordered by ID in descending order."
    )
    @GetMapping("/subjects/{subjectId}/members/{memberId}/engagements")
    public ResponseEntity<List<EngagementDto>> findAllByMemberIdAndSubjectIdOrderByIdDesc(@PathVariable("memberId") Long memberId,
                                                                                          @PathVariable("subjectId") Long subjectId) {
        return new ResponseEntity<>(engagementService.findAllByMemberIdAndSubjectIdOrderByIdDesc(memberId, subjectId), HttpStatus.OK);
    }
    @Operation(
            summary = "Retrieve Engagements by Member ID and Year",
            description = "Fetches a list of engagements associated with the specified member based on the provided member ID and year."
    )
    @GetMapping("/members/{memberId}/year/{year}/engagements")
    public ResponseEntity<List<EngagementDto>> findAllByMemberIdAndYear(@PathVariable("memberId") Long memberId,
                                                                        @PathVariable("year") int year) {
        return new ResponseEntity<>(engagementService.findAllByMemberIdAndYear(memberId, year), HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve Engagements by Subject ID and Year",
            description = "Fetches a list of engagements associated with the specified subject based on the provided subject ID and year."
    )@GetMapping("/subject/{subjectId}/year/{year}/engagements")
    public ResponseEntity<List<EngagementDto>> findAllBySubjectIdAndYear(@PathVariable("subjectId") Long subjectId,
                                                                        @PathVariable("year") int year) {
        return new ResponseEntity<>(engagementService.findAllBySubjectIdAndYear(subjectId, year), HttpStatus.OK);
    }

    @Operation(summary = "Delete Engagement entity by id.")
    @DeleteMapping("/engagements/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        engagementService.delete(id);
        return new ResponseEntity<>("Engagement removed.", HttpStatus.OK);
    }

    @Operation(summary = "Generate a new Engagement entity.")
    @PostMapping("/engagements")
    public ResponseEntity<EngagementDto> save(@Valid @RequestBody EngagementDto engagementDto) {
        return new ResponseEntity<>(engagementService.save(engagementDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Modify Engagement entity.",
            description = "Updates the 'year' and 'teachingForm' attributes of the specified Engagement entity."
    )
    @PutMapping("/engagements")
    public ResponseEntity<EngagementDto> update(@Valid @RequestBody EngagementDto engagementDto) {
        return new ResponseEntity<>(engagementService.update(engagementDto), HttpStatus.OK);
    }
}
