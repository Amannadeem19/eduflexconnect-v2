package com.fyp.eduflexconnect.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecord
{
    LocalDate date;
    String courseCode;
    String semesterId;
    List<AttendanceRequest> attendanceReq;
    String teacherId;
}
