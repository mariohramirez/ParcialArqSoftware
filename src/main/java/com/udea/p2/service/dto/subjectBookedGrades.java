package com.udea.p2.service.dto;

import java.util.List;

public class subjectBookedGrades {

    public List<nota> grades;
    public String name;

    subjectBookedGrades() {}

    subjectBookedGrades(String name, List<nota> grades) {
        this.grades = grades;
        this.name = name;
    }
}
