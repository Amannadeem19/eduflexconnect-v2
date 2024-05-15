package com.fyp.CourseRegistration.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TeacherContactInfo
{
    @Id
    private String username;
    private String street_address;
    private String city;
    private String home_phone;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "username")
    @JsonBackReference
    private Teacher teacher;


}
