package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.DTOs.ElectiveSectionDTO;
import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.ElectiveSection;
import com.fyp.eduflexconnect.Models.Semester;
import com.fyp.eduflexconnect.Repositories.ElectiveSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectiveSectionService
{
    @Autowired
    private ElectiveSectionRepository elective_section_repo;
    @Autowired
    private CourseService course_service;
    @Autowired
    private SemesterService semester_service;
    @Autowired
    private ElectiveClassroomService elective_classroom_service;


    // For Student Registration Page
    public List<ElectiveSection> getElectiveSections(Course course, Semester semester)
    {
        return elective_section_repo.findByCourseAndSemester(course, semester);
    }
    public ElectiveSection generateElectiveSection(ElectiveSectionDTO elective_section)
    {
        ElectiveSection newSection = new ElectiveSection();

        newSection.setName(elective_section.getName());
        newSection.setNumberOfSeats(elective_section.getNumberOfSeats());
        newSection.setCourse(course_service.course_repo.findByCode(elective_section.getCourse()));
        newSection.setSemester(semester_service.semester_repo.findById(elective_section.getSemester()).orElse(null));
        elective_section_repo.save(newSection);
        elective_classroom_service.GenerateElectiveClassroom(newSection.getCourse().getCourse_id(),newSection,newSection.getSemester().getSemester_id());
        return newSection;


    }

    public ElectiveSection getElectiveSection(String name)
    {
        return elective_section_repo.findByName(name);
    }

}
