package com.fyp.CourseRegistration.Services.Clients;

import com.fyp.CourseRegistration.Models.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "EDUFLEX-CLIENT")
public interface EduFlexClient
{



    @GetMapping("/api/semester/getsemester/{id}")
    Semester getsemester(@PathVariable String id);

    @PostMapping("/api/offeredcourses/get/{sectionId}")
    List<OfferedCourse> listOfferedCourses(@PathVariable String sectionId, @RequestBody Semester semester);


    @GetMapping("/api/course/getcourse/{courseCode}")
    Course GetCourse(@PathVariable  String courseCode);

    @GetMapping("/api/semester/currentsemester")
    String currentSemester();

}
