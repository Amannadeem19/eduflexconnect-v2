package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
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

    public StudentContactInfo() {
    }

    public StudentContactInfo(String id, String street_address, String city, String home_phone, String father_name, String father_cnic, Student student) {
        this.id = id;
        this.street_address = street_address;
        this.city = city;
        this.home_phone = home_phone;
        this.father_name = father_name;
        this.father_cnic = father_cnic;
        this.student = student;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public void setFather_cnic(String father_cnic) {
        this.father_cnic = father_cnic;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
