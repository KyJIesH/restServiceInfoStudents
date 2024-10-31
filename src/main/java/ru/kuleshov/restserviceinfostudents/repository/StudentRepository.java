package ru.kuleshov.restserviceinfostudents.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kuleshov.restserviceinfostudents.model.Student;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс, описывающий методы,
 * для работы с репозиторием
 */
public interface StudentRepository extends MongoRepository<Student, UUID> {

    Student getStudentById(UUID id);

    List<Student> findAllByOrderByLastnameAsc();

    void deleteById(UUID id);
}
