package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Models.Semester;
import com.fyp.eduflexconnect.Services.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/semester")
public class SemesterController
{
    @Autowired
    private SemesterService semester_service;

    @PostMapping("/addsemester")
    public Semester addsemester(@RequestBody Semester semester)
    {
        return semester_service.AddSemester(semester);
    }

    @GetMapping("/endsemester/{semester_id}/{end_date}")
    public String endsemester(@PathVariable String semester_id,@PathVariable LocalDate end_date)
    {
        return semester_service.EndSemester(semester_id,end_date);
    }
    @GetMapping("/getsemesters")
    public List<Semester> getsemesters()
    {
        return semester_service.GetSemesters();
    }
    @GetMapping("/getsemester/{id}")
    public Semester getsemester(@PathVariable String id)
    {
        return semester_service.GetSemester(id);
    }

    @GetMapping("/startregistration/{semester_id}")
    public String startcourseregistration(@PathVariable String semester_id)
    {
        return semester_service.startCourseRegistration(semester_id);
    }

    @GetMapping("/endregistration/{semester_id}")
    public String endcourseregistration(@PathVariable String semester_id)
    {
        return semester_service.endCourseRegistration(semester_id);
    }

    @GetMapping("/currentsemester")
    public String currentSemester()
    {

        return semester_service.currentSemester();
    }
}
