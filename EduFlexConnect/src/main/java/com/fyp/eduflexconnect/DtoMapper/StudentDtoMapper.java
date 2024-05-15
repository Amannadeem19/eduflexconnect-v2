package com.fyp.eduflexconnect.DtoMapper;


import com.fyp.eduflexconnect.DTOs.StudentDto;
import com.fyp.eduflexconnect.Models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDtoMapper {

//        private String id, fullName, DOB, contact_no, address, city, image, departmentName, userType,batch,section,status,CNIC,email;
    public static StudentDto toStudentDto(Student student)
    {
            StudentDto studentDto = new StudentDto();
            studentDto.setId(student.getId());
            studentDto.setFullName(student.getPersonal_info().getFull_name());
            studentDto.setDOB(student.getPersonal_info().getDob());
            studentDto.setContact_no(student.getPersonal_info().getMobile());
            studentDto.setAddress(student.getContact_info().getStreet_address());
            studentDto.setCity(student.getContact_info().getCity());
            studentDto.setImage(student.getImage());
            studentDto.setDepartmentName(student.getDegree());
            studentDto.setBatch(student.getBatch());
            studentDto.setSection(student.getSection().getId());
            studentDto.setStatus(student.getStatus());
            studentDto.setCNIC(student.getPersonal_info().getCnic());
            studentDto.setEmail(student.getPersonal_info().getEmail());
            studentDto.setUserType("std");

            return studentDto;
    }
    public static List<StudentDto> studentDtos(List<Student> students){
            List<StudentDto> studentDtos = new ArrayList<>();
            for(Student student : students)
            {
                    StudentDto studentDto = new StudentDto();
                    studentDto.setId(student.getId());
                    studentDto.setFullName(student.getPersonal_info().getFull_name());
                    studentDto.setDOB(student.getPersonal_info().getDob());
                    studentDto.setContact_no(student.getPersonal_info().getMobile());
                    studentDto.setAddress(student.getContact_info().getStreet_address());
                    studentDto.setCity(student.getContact_info().getCity());
                    studentDto.setImage(student.getImage());
                    studentDto.setDepartmentName(student.getDegree());
                    studentDto.setBatch(student.getBatch());
                    studentDto.setSection(student.getSection().getId());
                    studentDto.setStatus(student.getStatus());
                    studentDto.setCNIC(student.getPersonal_info().getCnic());
                    studentDto.setEmail(student.getPersonal_info().getEmail());
                    studentDto.setUserType("std");

                    studentDtos.add(studentDto);
            }
            return studentDtos;
    }
}
