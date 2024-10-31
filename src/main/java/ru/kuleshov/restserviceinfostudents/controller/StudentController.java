package ru.kuleshov.restserviceinfostudents.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kuleshov.restserviceinfostudents.dto.StudentDto;
import ru.kuleshov.restserviceinfostudents.model.Student;
import ru.kuleshov.restserviceinfostudents.service.StudentService;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для выполнения CRUD операций над объектом {@link  Student}
 * Доступен админимтратору
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private static final String TAG = "STUDENT CONTROLLER";
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto) {
        log.info("{} - Пришел запрос на добавление объекта студент (POST /students)", TAG);
        StudentDto response = studentService.createStudent(studentDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable UUID studentId) {
        log.info("{} - Пришел запрос на получение объекта студент по id {} (GET /students/{studentId})", TAG, studentId);
        StudentDto response = studentService.getStudent(studentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        log.info("{} - Пришел запрос на получение списка объектов студент (GET /students)", TAG);
        List<StudentDto> response = studentService.getAllStudents();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable UUID studentId,
                                                    @RequestBody StudentDto studentDto) {
        log.info("{} - Пришел запрос на обновление объекта студент с id {} (PUT /students/{studentId})", TAG, studentId);
        StudentDto response = studentService.updateStudent(studentId, studentDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable UUID studentId) {
        log.info("{} - Пришел запрос на удаление объекта студент по id {} (DELETE /students/{studentId})", TAG, studentId);
        if (!studentService.deleteStudent(studentId)) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
