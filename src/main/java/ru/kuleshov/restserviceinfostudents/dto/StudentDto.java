package ru.kuleshov.restserviceinfostudents.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Класс, описывающий
 * DTO объект
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private UUID id;
    private String lastname;
    private String firstname;
    private String patronymic;
    private String group;
    private double averageGrade;
}
