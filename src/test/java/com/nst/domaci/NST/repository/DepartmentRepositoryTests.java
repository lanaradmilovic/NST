package com.nst.domaci.NST.repository;

import com.nst.domaci.NST.entity.AcademicTitle;
import com.nst.domaci.NST.entity.Department;
import com.nst.domaci.NST.repository.DepartmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class DepartmentRepositoryTests {
    @Autowired
    private DepartmentRepository departmentRepository;



    @Test
    public void saveDepartmentTest() {
        Department department = Department.builder()
                .id(1L)
                .name("Test Department 1")
                .shortName("TD 1")
                .build();

        Department savedDepartment = departmentRepository.save(department);

        Assertions.assertNotNull(savedDepartment);
    }

    @Test
    public void findDepartmentByNameTest() {
        Department department = Department.builder()
                .id(2L)
                .name("Test Department 2")
                .shortName("TD 2")
                .build();
        Department saved = departmentRepository.save(department);
        Assertions.assertNotNull(saved);

        Optional<Department> foundDepartment = departmentRepository.findByName(saved.getName());

        Assertions.assertTrue(foundDepartment.isPresent());
        Assertions.assertEquals(foundDepartment.get(), saved);
        Assertions.assertEquals(saved.getName(), foundDepartment.get().getName());
    }

    @Test
    public void findAllDepartmentsTest() {
        departmentRepository.deleteAll();
        Department department1 = Department.builder()
                .id(4L)
                .name("Test Department 4")
                .shortName("TD 4")
                .build();
        Department saved1 = departmentRepository.save(department1);
        Assertions.assertNotNull(saved1);

        Department department2 = Department.builder()
                .id(5L)
                .name("Test Department 5")
                .shortName("TD 5")
                .build();
        Department saved2 = departmentRepository.save(department2);
        Assertions.assertNotNull(saved2);

        List<Department> departments = departmentRepository.findAll();

        Assertions.assertNotNull(departments);
        Assertions.assertEquals(2, departments.size());
        assertThat(departments)
                .extracting(Department::getId)
                .containsExactlyInAnyOrder(saved1.getId(), saved2.getId());
    }


}
