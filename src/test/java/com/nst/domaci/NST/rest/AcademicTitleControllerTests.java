package com.nst.domaci.NST.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nst.domaci.NST.dto.AcademicTitleDto;
import com.nst.domaci.NST.exception.EntityAlreadyExistsException;
import com.nst.domaci.NST.exception.ResourceNotFoundException;
import com.nst.domaci.NST.service.impl.AcademicTitleServiceImpl;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@WebMvcTest(AcademicTitleController.class)
public class AcademicTitleControllerTests {
    @MockBean
    public AcademicTitleServiceImpl academicTitleService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void saveAcademicTitleSuccessTest() throws Exception {
        AcademicTitleDto academicTitleDto = new AcademicTitleDto();
        academicTitleDto.setId(1L);
        academicTitleDto.setName("Title 1");

        when(academicTitleService.save(academicTitleDto)).thenReturn(academicTitleDto);

        mockMvc.perform(post("/api/academic-titles")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(academicTitleDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(academicTitleDto.getId().intValue())))
                .andExpect(jsonPath("$.name", equalTo(academicTitleDto.getName())));

        verify(academicTitleService, times(1)).save(academicTitleDto);
    }

    @Test
    public void saveAcademicTitleFailureTest() throws Exception {
        AcademicTitleDto academicTitleDto = new AcademicTitleDto();
        academicTitleDto.setId(1L);
        academicTitleDto.setName("Title 1");

        when(academicTitleService.save(academicTitleDto)).thenThrow(EntityAlreadyExistsException.class);

        mockMvc.perform(post("/api/academic-titles")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(academicTitleDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAllTest() throws Exception {
        AcademicTitleDto academicTitleDto1 = AcademicTitleDto.builder()
                .id(1L)
                .name("Title 1")
                .build();
        AcademicTitleDto academicTitleDto2 = AcademicTitleDto.builder()
                .id(2L)
                .name("Title 2")
                .build();
        List<AcademicTitleDto> academicTitleDtos = Arrays.asList(academicTitleDto1, academicTitleDto2);

        Mockito.when(academicTitleService.findAll()).thenReturn(academicTitleDtos);

        MvcResult result = mockMvc.perform(get("/api/academic-titles"))
                .andExpect(status().isOk())
                .andReturn();

        List<AcademicTitleDto> returnedTitles = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<AcademicTitleDto>>() {
                });

        Assertions.assertEquals(academicTitleDtos.size(), returnedTitles.size(), "Returned list size does not match");
        Assertions.assertTrue(returnedTitles.contains(academicTitleDto1), "DTO 1 not found in returned list");
        Assertions.assertTrue(returnedTitles.contains(academicTitleDto2), "DTO 2 not found in returned list");
    }

    @Test
    public void findByIdSuccessTest() throws Exception {
        AcademicTitleDto academicTitleDto1 = AcademicTitleDto.builder()
                .id(1L)
                .name("Title 1")
                .build();

        Mockito.when(academicTitleService.findById(academicTitleDto1.getId())).thenReturn(academicTitleDto1);

        MvcResult result = mockMvc.perform(get("/api/academic-titles/{id}", academicTitleDto1.getId()))
                .andExpect(status().isOk())
                .andReturn();

        AcademicTitleDto returned = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<AcademicTitleDto>() {
                });

        Assertions.assertNotNull(returned, "Returned DTO is null");
        Assertions.assertEquals(academicTitleDto1.getId(), returned.getId(), "ID does not match");
        Assertions.assertEquals(academicTitleDto1.getName(), returned.getName(), "Name does not match");
    }

    @Test
    public void findByIdFailureTest() throws Exception {
        AcademicTitleDto academicTitleDto1 = AcademicTitleDto.builder()
                .id(1L)
                .name("Title 1")
                .build();
        Mockito.when(academicTitleService.findById(academicTitleDto1.getId())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/api/academic-titles/{id}", academicTitleDto1.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteSuccessTest() throws Exception {
        Long id = 1L;
        String deletionMessage = "Academic title with ID = " + id + " removed.";

        Mockito.doNothing().when(academicTitleService).delete(id);

        mockMvc.perform(delete("/api/academic-titles/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(deletionMessage));

        Mockito.verify(academicTitleService, times(1)).delete(id);
    }

    @Test
    public void deleteFailureTest() throws Exception {
        Long id = 1L;

        Mockito.doThrow(ResourceNotFoundException.class).when(academicTitleService).delete(id);
        mockMvc.perform(delete("/api/academic-titles/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verify(academicTitleService, times(1)).delete(id);
    }

    @Test
    public void updateSuccessTest() throws Exception {
        AcademicTitleDto updatedAcademicTitleDto = AcademicTitleDto.builder()
                .id(1L)
                .name("Updated Title")
                .build();

        Mockito.when(academicTitleService.update(updatedAcademicTitleDto)).thenReturn(updatedAcademicTitleDto);

        mockMvc.perform(put("/api/academic-titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAcademicTitleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedAcademicTitleDto.getId()))
                .andExpect(jsonPath("$.name").value(updatedAcademicTitleDto.getName()));

        Mockito.verify(academicTitleService, times(1)).update(updatedAcademicTitleDto);
    }

    @Test
    public void updateFailureTest() throws Exception {
        AcademicTitleDto updatedAcademicTitleDto = AcademicTitleDto.builder()
                .id(1L)
                .name("Updated Title")
                .build();

        Mockito.when(academicTitleService.update(updatedAcademicTitleDto)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/api/academic-titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAcademicTitleDto)))
                .andExpect(status().isBadRequest());

        Mockito.verify(academicTitleService, times(1)).update(updatedAcademicTitleDto);
    }


}
