package com.fyp.eduflexconnect.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherDto
{
    private String username,fullName, contact_no, bio, experience, qualification, userType,image;
    private  boolean req_user;
    LocalDate DOB;
}
