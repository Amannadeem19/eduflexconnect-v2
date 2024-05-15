package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section,String>
{
    List<Section> findBySemesterAndDepartment(int semester, String department);

}
