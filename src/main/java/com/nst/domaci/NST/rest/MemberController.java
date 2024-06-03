package com.nst.domaci.NST.rest;

import com.nst.domaci.NST.converter.impl.MemberConverter;
import com.nst.domaci.NST.dto.*;
import com.nst.domaci.NST.entity.Member;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.impl.AcademicTitleServiceImpl;
import com.nst.domaci.NST.service.impl.MemberServiceImpl;
import com.nst.domaci.NST.service.impl.ScientificFieldServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member Controller")
@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberServiceImpl memberService;
    private final AcademicTitleServiceImpl academicTitleService;
    private final ScientificFieldServiceImpl scientificFieldService;
    private final MemberConverter memberConverter;

    public MemberController(MemberServiceImpl memberService, AcademicTitleServiceImpl academicTitleService, ScientificFieldServiceImpl scientificFieldService, MemberConverter memberConverter) {
        this.memberService = memberService;
        this.academicTitleService = academicTitleService;
        this.scientificFieldService = scientificFieldService;
        this.memberConverter = memberConverter;
    }

    @Operation(summary = "Retrieve all Member entities.")
    @GetMapping("/members")
    public ResponseEntity<List<Member>> findAll() {
        return new ResponseEntity<>(memberService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Delete Member entity by id.")
    @GetMapping("/members/{id}")
    public ResponseEntity<Member> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(memberService.findById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve Members of a Department.",
            description = "Fetches a list of members belonging to the specified Department based on the provided Department ID."
    )
    @GetMapping("/department/{id}/members")
    public ResponseEntity<List<MemberDto>> findAllByDepartmentId(@PathVariable("id") Long departmentId) {
        return new ResponseEntity<>(memberService.findAllByDepartmentId(departmentId), HttpStatus.OK);
    }

    @Operation(summary = "Delete Member entity by id.")
    @DeleteMapping("/members/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        memberService.delete(id);
        return new ResponseEntity<>("Member with ID = " + id + " removed.", HttpStatus.OK);
    }

    @Operation(summary = "Generate a new Member entity.")
    @PostMapping("/members")
    public ResponseEntity<MemberDto> save(@Valid @RequestBody MemberDto MemberDto) {
        return new ResponseEntity<>(memberService.save(MemberDto), HttpStatus.OK);
    }

    @Operation(summary = "Modify Member entity.")
    @PutMapping("/members")
    public ResponseEntity<MemberDto> update(@Valid @RequestBody MemberDto MemberDto) {
        return new ResponseEntity<>(memberService.update(MemberDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Update Member's Academic Title",
            description = "Updates the academic title of a member by associating them with a new academic title and scientific field."
    )
    @PutMapping("/members/{memberId}/academicTitle")
    public ResponseEntity<?> updateAcademicTitle(@PathVariable("memberId") long memberId, @RequestBody PromoteMemberDto promoteMemberDto) {
        try {
            MemberDto memberDto = memberConverter.toDto(memberService.findById(memberId));
            ScientificFieldDto scientificFieldDto = scientificFieldService.findById(promoteMemberDto.getScientificFieldId());
            AcademicTitleDto academicTitleDto = academicTitleService.findById(promoteMemberDto.getAcademicTitleId());
            return new ResponseEntity<>(memberService.updateAcademicTitle(memberDto, academicTitleDto, scientificFieldDto), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
       }

}
