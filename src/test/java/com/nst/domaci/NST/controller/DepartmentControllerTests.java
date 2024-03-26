package com.nst.domaci.NST.controller;

import static org.hamcrest.CoreMatchers.equalTo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.dto.DepartmentDto;
import com.nst.domaci.NST.dto.MemberDto;
import com.nst.domaci.NST.entity.Department;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentServiceImpl departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void findAllDepartmentsTest() throws Exception {
        List<Department> departments = Arrays.asList(
                Department.builder().id(1L).name("Department 1")
                        .build(),
                Department.builder().id(2L).name("Department 2").build()
        );

        Mockito.when(departmentService.findAll()).thenReturn(departments);
        MvcResult result = mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk()).andReturn();
        List<Department> returnedDepartments = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Department>>() {
                });

        Assertions.assertTrue(returnedDepartments.contains(departments.get(0)));
        Assertions.assertTrue(returnedDepartments.contains(departments.get(1)));
        Mockito.verify(departmentService, Mockito.times(1)).findAll();
    }

    @Test
    public void findDepartmentByIdSuccess() throws Exception {
        Long departmentId = 1L;
        Department department = Department.builder().id(1L).name("Department 1").build();

        Mockito.when(departmentService.findById(departmentId)).thenReturn(department);

        MvcResult result = mockMvc.perform(get("/api/departments/{id}", departmentId))
                .andExpect(status().isOk()).andReturn();
         Department returnedDepartment = objectMapper.readValue(result.getResponse().getContentAsString(),
                 new TypeReference<Department>() {
                 });

        Assertions.assertNotNull(returnedDepartment, "Returned DTO is null");
        Assertions.assertEquals(departmentId, returnedDepartment.getId(), "ID does not match");
    }
    @Test
    public void findDepartmentByIdFailure() throws Exception {
        Long departmentId = 1L;
        Department department = Department.builder().id(1L).name("Department 1").build();

        Mockito.when(departmentService.findById(departmentId)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/api/departments/{id}", departmentId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteDepartmentSuccess() throws Exception {
        Long departmentId = 1L;
        String expectedMessage = "Department with ID = " + departmentId + " removed.";
        Mockito.doNothing().when(departmentService).delete(departmentId);
        mockMvc.perform(delete("/api/departments/{id}", departmentId))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));

        Mockito.verify(departmentService, Mockito.times(1)).delete(departmentId);
    }
    @Test
    public void deleteDepartmentFailure() throws Exception {
        Long departmentId = 1L;
        Mockito.doThrow(ResourceNotFoundException.class).when(departmentService).delete(departmentId);
        mockMvc.perform(delete("/api/departments/{id}", departmentId))
                .andExpect(status().isBadRequest());

        Mockito.verify(departmentService, Mockito.times(1)).delete(departmentId);
    }
    @Test
    public void saveDepartmentSuccess() throws Exception {
        DepartmentDto departmentDto = DepartmentDto.builder().id(1L).name("Department 1").build();

        Mockito.when(departmentService.save(departmentDto)).thenReturn(departmentDto);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(departmentDto.getId().intValue())))
                .andExpect(jsonPath("$.name", equalTo(departmentDto.getName())));

        Mockito.verify(departmentService, Mockito.times(1)).save(departmentDto);
    }
    @Test
    public void saveDepartmentFailure() throws Exception {
        DepartmentDto departmentDto = DepartmentDto.builder().id(1L).name("Department 1").build();

        Mockito.when(departmentService.save(departmentDto)).thenThrow(EntityAlreadyExistsException.class);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void updateDepartmentSuccess() throws Exception {
        DepartmentDto departmentDto = DepartmentDto.builder().id(1L).name("Department 1").build();

        Mockito.when(departmentService.update(departmentDto)).thenReturn(departmentDto);

        mockMvc.perform(put("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDto)))
                .andExpect(status().isNoContent());

        Mockito.verify(departmentService, Mockito.times(1)).update(departmentDto);
    }
    @Test
    public void updateDepartmentFailure() throws Exception {
        DepartmentDto departmentDto = DepartmentDto.builder().id(1L).name("Department 1").build();

        Mockito.when(departmentService.update(departmentDto)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDto)))
                .andExpect(status().isBadRequest());

        Mockito.verify(departmentService, Mockito.times(1)).update(departmentDto);
    }

    @Test
    public void setLeaderTest() throws Exception{
        Long departmentId = 1L;
        Long memberId = 1L;
        Mockito.doNothing().when(departmentService).setLeader(departmentId, memberId);
        mockMvc.perform(post("/api/departments/{departmentId}/leader/{memberId}", departmentId, memberId))
                .andExpect(status().isOk());
        Mockito.verify(departmentService, Mockito.times(1)).setLeader(departmentId, memberId);
    }

    @Test
    public void setDepartmentLeaderFailure() throws Exception {
        Long departmentId = 1L;
        Long memberId = 1L;
        Mockito.doThrow(ResourceNotFoundException.class).when(departmentService).setLeader(departmentId, memberId);
        mockMvc.perform(post("/api/departments/{departmentId}/leader/{memberId}", departmentId, memberId))
                .andExpect(status().isBadRequest());
        Mockito.verify(departmentService, Mockito.times(1)).setLeader(departmentId, memberId);
    }
    @Test
    public void setSecretaryTest() throws Exception{
        Long departmentId = 1L;
        Long memberId = 1L;
        Mockito.doNothing().when(departmentService).setSecretary(departmentId, memberId);
        mockMvc.perform(post("/api/departments/{departmentId}/secretary/{memberId}", departmentId, memberId))
                .andExpect(status().isOk());
        Mockito.verify(departmentService, Mockito.times(1)).setSecretary(departmentId, memberId);
    }

    @Test
    public void setDepartmentSecretaryFailure() throws Exception {
        Long departmentId = 1L;
        Long memberId = 1L;
        Mockito.doThrow(ResourceNotFoundException.class).when(departmentService).setSecretary(departmentId, memberId);
        mockMvc.perform(post("/api/departments/{departmentId}/secretary/{memberId}", departmentId, memberId))
                .andExpect(status().isBadRequest());
        Mockito.verify(departmentService, Mockito.times(1)).setSecretary(departmentId, memberId);
    }

}
