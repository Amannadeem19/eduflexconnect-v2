package com.fyp.eduflexconnect.DTOs;

import com.fyp.eduflexconnect.Models.Attendance;
import com.fyp.eduflexconnect.Models.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO
{
    private Student student;
    private List<Attendance> attendanceList = new ArrayList<>();

}
