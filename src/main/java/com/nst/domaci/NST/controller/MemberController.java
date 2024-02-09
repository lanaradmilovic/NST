package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.dto.MemberDto;
import com.nst.domaci.NST.entity.Member;
import com.nst.domaci.NST.service.impl.MemberServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemberController {
    private final MemberServiceImpl memberService;

    public MemberController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }
    @GetMapping("/members")
    public ResponseEntity<List<Member>> findAll(){
        return new ResponseEntity<>(memberService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(memberService.findById(id), HttpStatus.OK);
    }
    @GetMapping("/departments/{id}/members")
    public ResponseEntity<List<MemberDto>> findAllByDepartmentId(@PathVariable("id") Long departmentId){
        return new ResponseEntity<>(memberService.findAllByDepartmentId(departmentId), HttpStatus.OK);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        memberService.delete(id);
        return new ResponseEntity<>("Member removed.", HttpStatus.OK);
    }
    @PostMapping("/members")
    public ResponseEntity<MemberDto> save(@Valid @RequestBody MemberDto MemberDto){
        return new ResponseEntity<>(memberService.save(MemberDto), HttpStatus.OK);
    }
    @PutMapping("/members")
    public ResponseEntity<MemberDto> update(@Valid @RequestBody MemberDto MemberDto){
        return new ResponseEntity<>(memberService.update(MemberDto), HttpStatus.OK);
    }
    
}
