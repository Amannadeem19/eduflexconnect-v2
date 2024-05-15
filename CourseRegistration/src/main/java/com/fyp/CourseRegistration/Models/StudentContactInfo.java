package com.fyp.CourseRegistration.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentContactInfo
{
    @Id
    private String id;
    private String street_address;
    private String city;
    private String home_phone;
    private String father_name;
    private String father_cnic;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    private Student student;

}
