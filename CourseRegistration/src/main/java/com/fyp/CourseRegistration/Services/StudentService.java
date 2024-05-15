package com.fyp.CourseRegistration.Services;

import com.fyp.CourseRegistration.Models.Section;
import com.fyp.CourseRegistration.Models.Student;
import com.fyp.CourseRegistration.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService
{
    @Autowired
    private StudentRepository student_repo;

    public Student saveStudent(Student student)
    {
        return student_repo.save(student);
    }
    public Student findStudentByUsername(String id)
    {

        return student_repo.findById(id).orElse(null);
    }
    public Section getSectionOfStudent(String id)
    {

        return student_repo.findSection(id);
    }
}
