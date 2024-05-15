package com.fyp.eduflexconnect.DTOs;

import lombok.Data;

@Data
public class ElectiveSectionDTO
{
    private String name;
    private int numberOfSeats;
    private String course;
    private String semester;

    public ElectiveSectionDTO() {
    }

    public ElectiveSectionDTO(String name, int numberOfSeats, String course, String semester) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.course = course;
        this.semester = semester;
    }
}
