package ru.kuleshov.restserviceinfostudents.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Класс, описывающий
 * объект студент
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Student {

    @Id
    private UUID id;
    private String lastname;
    private String firstname;
    private String patronymic;
    private String group;
    private double averageGrade;
}
