package ru.kuleshov.restserviceinfostudents.mapper;

import org.mapstruct.Mapper;
import ru.kuleshov.restserviceinfostudents.dto.StudentDto;
import ru.kuleshov.restserviceinfostudents.model.Student;

import java.util.List;

/**
 * Маппер объекта
 * {@link  Student}
 */
@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto toStudentDto(Student student);

    Student toStudent(StudentDto studentDto);

    List<StudentDto> toStudentDtoList(List<Student> students);
}
