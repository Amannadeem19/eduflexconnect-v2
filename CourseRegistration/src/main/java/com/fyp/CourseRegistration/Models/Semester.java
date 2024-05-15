package com.fyp.CourseRegistration.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Semester
{
    @Id
    @GeneratedValue(generator = "semester_id_generator")
    @GenericGenerator(name = "semester_id_generator", strategy = "com.fyp.eduflexconnect.Generators.SemesterIdGenerator")
    private String semester_id;
    private String name;
    private int year;
    private LocalDate start_date;
    private LocalDate end_date;
    private String status;
    private boolean isRegistrationOpen;

    @OneToMany(mappedBy = "semester")
    @JsonIgnore
    private List<OfferedCourse> offeredCourses;
}
