package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.ElectiveSection;
import com.fyp.eduflexconnect.Models.Semester;
import com.fyp.eduflexconnect.Services.ElectiveSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/electivesection")
public class ElectiveSectionController
{
    @Autowired
    private ElectiveSectionService electiveSectionService;

    @PostMapping("/getelectivesection")
    public List<ElectiveSection> getElectiveSections(@RequestBody Course course,@RequestBody Semester semester)
    {
        return electiveSectionService.getElectiveSections(course,semester);
    }


}
