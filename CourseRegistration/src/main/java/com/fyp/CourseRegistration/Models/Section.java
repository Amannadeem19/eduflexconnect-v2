package com.fyp.CourseRegistration.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Section
{
    @Id
    private String id;
    private int enrollYear;
    private String department;
    private int semester;
}
