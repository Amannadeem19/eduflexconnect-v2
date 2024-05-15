package com.fyp.CourseRegistration.Services;

import com.fyp.CourseRegistration.Models.Course;
import com.fyp.CourseRegistration.Models.ElectiveSection;
import com.fyp.CourseRegistration.Models.Semester;
import com.fyp.CourseRegistration.Repositories.ElectiveSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectiveSectionService
{
    @Autowired
    private ElectiveSectionRepository electiveSection_repo;

    public List<ElectiveSection> getElectiveSections(Course course, Semester semester)
    {
        return electiveSection_repo.findByCourseAndSemester(course, semester);
    }
    public ElectiveSection getElectiveSection(String name)
    {
        return electiveSection_repo.findByName(name);

    }

    public ElectiveSection saveElectiveSection(ElectiveSection electiveSection)
    {
        return electiveSection_repo.save(electiveSection);
    }
}
