package com.fyp.CourseRegistration.Services;

import com.fyp.CourseRegistration.Models.Enrollment;
import com.fyp.CourseRegistration.Models.Semester;
import com.fyp.CourseRegistration.Models.Student;
import com.fyp.CourseRegistration.Repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService
{
    @Autowired
    private EnrollmentRepository enrollment_repo;

    public boolean isRegister(String course_code,String student_id, String semester_id)
    {
        return enrollment_repo.isRegister(course_code,student_id,semester_id);

    }

    public List<Enrollment> getStudentCurrentEnrollments(Student student, Semester semester)
    {
        return enrollment_repo.findByStudentAndSemester(student,semester);
    }

    public Enrollment saveEnrollment(Enrollment e)
    {
        return  enrollment_repo.save(e);
    }


}
