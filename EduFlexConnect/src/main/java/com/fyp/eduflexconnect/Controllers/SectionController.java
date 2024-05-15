package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Models.Section;
import com.fyp.eduflexconnect.Repositories.SectionRepository;
import com.fyp.eduflexconnect.Services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/section")
public class SectionController
{
    @Autowired
    SectionService section_service;

    @GetMapping("/generate/{department}")
    public Section generateSection(@PathVariable String department)
    {
        return section_service.GenerateSection(department);
    }
}
