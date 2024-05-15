package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "Registration")
public class Registrations
{
    @Id
    @GeneratedValue(generator = "arn_generator")
    @GenericGenerator(name = "arn_generator", strategy = "com.fyp.eduflexconnect.Generators.ArnGenerator")
    private int arn;
    private String fullName;
    private String gender;
    private LocalDate dob;
    @Column(unique = true)
    private String cnic;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String mobile;
    private String streetAddress;
    private String homePhone;
    private String fatherName;
    private String fatherCnic;
    private String city;
    private String degree;
    private String campus;
    @OneToOne(mappedBy = "registration",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    private RegistrationPreference registrationReference;

    public Registrations(int arn, String fullName, String gender, LocalDate dob, String cnic, String email, String mobile, String streetAddress, String homePhone, String fatherName, String fatherCnic, String city,String degree,String campus ,RegistrationPreference registrationReference) {
        this.arn = arn;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.cnic = cnic;
        this.email = email;
        this.mobile = mobile;
        this.streetAddress = streetAddress;
        this.homePhone = homePhone;
        this.fatherName = fatherName;
        this.fatherCnic = fatherCnic;
        this.city = city;
        this.degree = degree;
        this.campus = campus;
        this.registrationReference = registrationReference;
    }

    public Registrations() {

    }


    public void setCampus(String campus) {
        this.campus = campus;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setFatherCnic(String fatherCnic) {
        this.fatherCnic = fatherCnic;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRegistrationReference(RegistrationPreference registrationReference) {
        this.registrationReference = registrationReference;
    }

}
