package com.nst.domaci.NST.controller;

import com.nst.domaci.NST.converter.impl.DepartmentConverter;
import com.nst.domaci.NST.dto.DepartmentDto;
import com.nst.domaci.NST.entity.Department;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mvc/departments")
public class DepartmentMvcController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentConverter departmentConverter;

    @GetMapping("/showFormForAdd")
    public String addForm(Model model) {
        model.addAttribute("department", new DepartmentDto());
        return "add-department";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "list-departments";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("department") DepartmentDto departmentDto, Model model) {
        try {
            departmentService.save(departmentDto);
            return "redirect:/mvc/departments/list";
        } catch (EntityAlreadyExistsException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add-department";
        }
    }

    @GetMapping("/showFormForUpdate")
    public String updateForm(@RequestParam("id") long id, Model model) {
        Department department = departmentService.findById(id);
        DepartmentDto departmentDto = departmentConverter.toDto(department);
        model.addAttribute("department", departmentDto);
        return "update-department";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute("department") DepartmentDto departmentDto) {
        departmentService.update(departmentDto);
        return "redirect:/mvc/departments/list";
    }
    @GetMapping("/delete")
    public String deleteForm(@RequestParam("id") long id, Model model){
        Department department = departmentService.findById(id);
        departmentService.delete(id);
        model.addAttribute("department", department);
        return "redirect:/mvc/departments/list";
    }
}
