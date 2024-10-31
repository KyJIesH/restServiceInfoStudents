package ru.kuleshov.restserviceinfostudents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kuleshov.restserviceinfostudents.dto.StudentDto;
import ru.kuleshov.restserviceinfostudents.exception.NotFoundException;
import ru.kuleshov.restserviceinfostudents.mapper.StudentMapper;
import ru.kuleshov.restserviceinfostudents.model.Student;
import ru.kuleshov.restserviceinfostudents.repository.StudentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private StudentRepository studentRepository;

    private UUID studentId;
    private Student student;
    private StudentDto studentDto;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();

        student = new Student();
        student.setId(studentId);
        student.setLastname("Dostoevskiy");
        student.setFirstname("Fedor");
        student.setPatronymic("Michailovich");
        student.setGroup("1");
        student.setAverageGrade(4.77);

        studentDto = new StudentDto();
        studentDto.setId(studentId);
        studentDto.setLastname("Dostoevskiy");
        studentDto.setFirstname("Fedor");
        studentDto.setPatronymic("Michailovich");
        studentDto.setGroup("1");
        studentDto.setAverageGrade(4.77);
    }

    @Test
    void createStudentTest() {
        when(studentMapper.toStudent(studentDto)).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toStudentDto(student)).thenReturn(studentDto);

        StudentDto createdStudent = studentService.createStudent(studentDto);

        assertNotNull(createdStudent);
        assertNotNull(createdStudent.getId());
        verify(studentRepository).save(any());
    }

    @Test
    void getStudentNotFoundTest() {
        when(studentRepository.getStudentById(studentId)).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            studentService.getStudent(studentId);
        });

        assertEquals("Студент не найден", exception.getMessage());
    }

    @Test
    void getStudentTest() {
        when(studentRepository.getStudentById(studentId)).thenReturn(student);
        when(studentMapper.toStudentDto(student)).thenReturn(studentDto);

        StudentDto result = studentService.getStudent(studentId);

        assertNotNull(result);
        assertEquals(studentId, result.getId());
    }

    @Test
    void getAllStudentsTest() {
        when(studentRepository.findAllByOrderByLastnameAsc()).thenReturn(Collections.singletonList(student));
        when(studentMapper.toStudentDtoList(anyList())).thenReturn(Collections.singletonList(studentDto));

        List<StudentDto> result = studentService.getAllStudents();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void updateStudentNotFoundTest() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            studentService.updateStudent(studentId, studentDto);
        });

        assertEquals("Студент не найден", exception.getMessage());
    }

    @Test
    void updateStudentTest() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentMapper.toStudentDto(student)).thenReturn(studentDto);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDto updatedStudent = studentService.updateStudent(studentId, studentDto);

        assertNotNull(updatedStudent);
        verify(studentRepository).save(any());
    }

    @Test
    void deleteStudentNotFoundTest() {
        when(studentRepository.existsById(studentId)).thenReturn(false);
        studentService.deleteStudent(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void deleteStudentTest() {
        when(studentRepository.existsById(studentId)).thenReturn(true);
        studentService.deleteStudent(studentId);

        assertTrue(studentRepository.existsById(studentId));
    }
}