package com.nst.domaci.NST.service;

import com.nst.domaci.NST.dto.DepartmentDto;
import com.nst.domaci.NST.entity.Department;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();

    Department findById(Long id) throws ResourceNotFoundException;

    DepartmentDto save(DepartmentDto departmentDto) throws EntityAlreadyExistsException;

    DepartmentDto update(DepartmentDto departmentDto) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
    void setLeader(Long departmentId, Long memberId);
    void setSecretary(Long departmentId, Long memberId);

}
