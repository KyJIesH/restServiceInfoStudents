package ru.kuleshov.restserviceinfostudents.service;

import ru.kuleshov.restserviceinfostudents.dto.StudentDto;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс, описывающий методы,
 * реализованные в сервисном слое
 */
public interface StudentService {

    StudentDto createStudent(StudentDto studentDto);

    StudentDto getStudent(UUID id);

    List<StudentDto> getAllStudents();

    StudentDto updateStudent(UUID id, StudentDto studentDto);

    boolean deleteStudent(UUID id);
}
