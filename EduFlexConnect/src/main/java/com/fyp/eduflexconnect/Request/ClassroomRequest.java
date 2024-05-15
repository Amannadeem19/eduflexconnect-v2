package com.fyp.eduflexconnect.Request;

import com.fyp.eduflexconnect.Models.Classroom;
import com.fyp.eduflexconnect.Models.ElectiveClassroom;
import lombok.Data;

import java.util.List;

@Data
public class ClassroomRequest
{
    private List<Classroom> classrooms;
    private List<ElectiveClassroom> electiveClassrooms;
}
