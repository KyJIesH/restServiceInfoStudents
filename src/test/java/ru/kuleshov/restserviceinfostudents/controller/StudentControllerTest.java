package ru.kuleshov.restserviceinfostudents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.kuleshov.restserviceinfostudents.dto.StudentDto;
import ru.kuleshov.restserviceinfostudents.service.StudentService;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    private UUID studentId;
    private StudentDto studentDto;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();

        studentDto = new StudentDto();
        studentDto.setId(studentId);
        studentDto.setLastname("Dostoevskiy");
        studentDto.setFirstname("Fedor");
        studentDto.setPatronymic("Michailovich");
        studentDto.setGroup("1");
        studentDto.setAverageGrade(4.77);
    }

    @Test
    @WithMockUser
    void addStudentTest() throws Exception {
        when(studentService.createStudent(any(StudentDto.class))).thenReturn(studentDto);

        mockMvc.perform(post("/students")
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(studentId.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void addStudentRedirectTest() throws Exception {
        mockMvc.perform(post("/students")
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void getStudentTest() throws Exception {
        when(studentService.getStudent(any(UUID.class))).thenReturn(studentDto);

        mockMvc.perform(get("/students/{studentId}", studentId)
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void getStudentRedirectTest() throws Exception {
        mockMvc.perform(get("/students/{studentId}", studentId)
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void getAllStudentsTest() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Collections.singletonList(studentDto));

        mockMvc.perform(get("/students")
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(studentId.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void getAllStudentsRedirectTest() throws Exception {
        mockMvc.perform(get("/students")
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void updateStudentTest() throws Exception {
        when(studentService.updateStudent(any(UUID.class), any(StudentDto.class))).thenReturn(studentDto);

        mockMvc.perform(put("/students/{studentId}", studentId)
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(studentId.toString()))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void updateStudentRedirectTest() throws Exception {
        mockMvc.perform(put("/students/{studentId}", studentId)
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void deleteStudentTest() throws Exception {
        when(studentService.deleteStudent(any(UUID.class))).thenReturn(false);

        mockMvc.perform(delete("/students/{studentId}", studentId)
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void deleteStudentRedirectTest() throws Exception {
        mockMvc.perform(delete("/students/{studentId}", studentId)
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void deleteStudentNoContentTest() throws Exception {
        when(studentService.deleteStudent(any(UUID.class))).thenReturn(true);

        mockMvc.perform(delete("/students/{studentId}", studentId)
                        .content(objectMapper.writeValueAsString(studentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isInternalServerError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}