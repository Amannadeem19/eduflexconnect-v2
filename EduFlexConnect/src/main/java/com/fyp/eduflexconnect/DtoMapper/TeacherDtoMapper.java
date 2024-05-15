package com.fyp.eduflexconnect.DtoMapper;



import com.fyp.eduflexconnect.DTOs.TeacherDto;
import com.fyp.eduflexconnect.Models.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherDtoMapper
{

    public static TeacherDto toTeacherDto(Teacher teacher)
    {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setUsername(teacher.getUsername());
        teacherDto.setFullName(teacher.getPersonal_info().getFirst_name()+' '+teacher.getPersonal_info().getLast_name());
        teacherDto.setDOB(teacher.getPersonal_info().getDob());
        teacherDto.setContact_no(teacher.getPersonal_info().getMobile());
        teacherDto.setBio(teacher.getBio());
        teacherDto.setExperience(teacher.getExperience());
        teacherDto.setQualification(teacher.getQualification());
        teacherDto.setUserType("teacher");
        return teacherDto;
    }

    public static List<TeacherDto> toTeacherDtos(List<Teacher> teachers){
       List<TeacherDto> teacherDtos = new ArrayList<>();
       for(Teacher teacher : teachers)
       {
            TeacherDto teacherDto = new TeacherDto();
           teacherDto.setUsername(teacher.getUsername());
           teacherDto.setFullName(teacher.getPersonal_info().getFirst_name()+' '+teacher.getPersonal_info().getLast_name());
           teacherDto.setDOB(teacher.getPersonal_info().getDob());
           teacherDto.setContact_no(teacher.getPersonal_info().getMobile());
           teacherDto.setBio(teacher.getBio());
           teacherDto.setExperience(teacher.getExperience());
           teacherDto.setQualification(teacher.getQualification());
           teacherDto.setUserType("teacher");
           teacherDto.setUserType("teacher");
           teacherDtos.add(teacherDto);
       }
       return teacherDtos;

    }
}
