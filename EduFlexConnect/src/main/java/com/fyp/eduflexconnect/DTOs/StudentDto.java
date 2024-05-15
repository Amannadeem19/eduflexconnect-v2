package com.fyp.eduflexconnect.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDto
{

    private String id, fullName, contact_no, address, city, image, departmentName, userType,batch,section,status,CNIC,email;
    private LocalDate DOB;
    private  boolean req_user;
}
