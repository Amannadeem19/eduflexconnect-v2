package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Models.SemesterName;
import com.fyp.eduflexconnect.Services.SemesterNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fyp/semestername")
public class SemesterNameController
{
    @Autowired
    private SemesterNameService semester_name_service;

    @PostMapping("/addsemestername")
    public SemesterName addSemesterName(@RequestBody SemesterName name)
    {
         return  semester_name_service.AddSemesterName(name);
    }

    @GetMapping("/getsemestersname")
    public List<SemesterName> getsemestersname()
    {
        return semester_name_service.GetSemestersName();
    }
}
