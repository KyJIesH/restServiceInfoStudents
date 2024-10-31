package ru.kuleshov.restserviceinfostudents.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kuleshov.restserviceinfostudents.dto.StudentDto;
import ru.kuleshov.restserviceinfostudents.exception.NotFoundException;
import ru.kuleshov.restserviceinfostudents.exception.ValidationException;
import ru.kuleshov.restserviceinfostudents.mapper.StudentMapper;
import ru.kuleshov.restserviceinfostudents.model.Student;
import ru.kuleshov.restserviceinfostudents.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис,
 * для работы с объектом {@link  Student}
 */
@Service
@Slf4j
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private static final String TAG = "STUDENT SERVICE";

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public StudentDto createStudent(StudentDto studentDto) {
        log.info("{} - Обработка запроса на добавление объекта студент {}", TAG, studentDto);
        studentDto.setId(UUID.randomUUID());
        Student student = studentMapper.toStudent(studentDto);
        return studentMapper.toStudentDto(studentRepository.save(student));
    }

    @Override
    public StudentDto getStudent(UUID id) {
        log.info("{} - Обработка запроса на получение объекта студент по id {}", TAG, id);
        checkStudentId(id);
        Student response = studentRepository.getStudentById(id);
        if (response != null) {
            return studentMapper.toStudentDto(response);
        } else throw new NotFoundException("Студент не найден");
    }

    @Override
    public List<StudentDto> getAllStudents() {
        log.info("{} - Обработка запроса на получение списка всех объектов студент", TAG);
        List<Student> response = studentRepository.findAllByOrderByLastnameAsc();
        return studentMapper.toStudentDtoList(response);
    }

    @Override
    @Transactional
    public StudentDto updateStudent(UUID id, StudentDto studentDto) {
        log.info("{} - Обработка запроса на обновление объекта студент c id {}", TAG, id);

        Student updatedStudent = new Student();

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            updatedStudent = update(student, studentDto);
            studentRepository.save(updatedStudent);
        } else throw new NotFoundException("Студент не найден");

        return studentMapper.toStudentDto(updatedStudent);
    }

    @Override
    @Transactional
    public boolean deleteStudent(UUID id) {
        log.info("{} - Обработка запроса на удаление объекта студент c id {}", TAG, id);
        checkStudentId(id);
        studentRepository.deleteById(id);
        return studentRepository.existsById(id);
    }

    private void checkStudentId(UUID id) {
        if (id == null) {
            throw new ValidationException("Некорректный формат id студента");
        }
    }

    private Student update(Student student, StudentDto studentDto) {
        student.setId(student.getId());
        student.setLastname(studentDto.getLastname());
        student.setFirstname(studentDto.getFirstname());
        student.setPatronymic(studentDto.getPatronymic());
        student.setGroup(studentDto.getGroup());
        student.setAverageGrade(studentDto.getAverageGrade());
        return student;
    }
}
