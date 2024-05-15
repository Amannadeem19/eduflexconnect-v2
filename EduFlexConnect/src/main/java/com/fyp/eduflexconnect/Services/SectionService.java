package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Generators.SectionIdGenerator;
import com.fyp.eduflexconnect.Models.Section;
import com.fyp.eduflexconnect.Repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SectionService
{
    @Autowired
    SectionRepository section_repo;
    @Autowired
    SectionIdGenerator section_id_generator;

    public Section GenerateSection(String department)
    {
        Section new_section = new Section();
        String section_id;
        char alphabet = 'A';
        do
        {
            section_id = section_id_generator.GenerateSectionId(department,alphabet++);
        }while(section_repo.existsById(section_id));

        new_section.setId(section_id);
        new_section.setDepartment(department);
        new_section.setSemester(1);
        LocalDate currentDate = LocalDate.now();
        new_section.setEnrollYear(currentDate.getYear());

        return section_repo.save(new_section);

    }

    public Section GetSection(String id)
    {
        return section_repo.findById(id).orElse(null);
    }
}
