package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Models.Course;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Repositories.CourseRepository;
import com.fyp.eduflexconnect.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService
{
    @Autowired
    CourseRepository course_repo;

    @Autowired
    TeacherRepository teacher_repo;

    public Course AddCourse(Course course)
    {
        return course_repo.save(course);

    }

    public Course GetCourse(int id)
    {
        return course_repo.findById(id).orElse(null);
    }
    public Course GetCourse(String code)
    {
         return course_repo.findByCode(code);

    }
    public List<Course> ListAllCourses()
    {
        return course_repo.findAll();
    }

    public Course assignTeacherToCourses(String teacher_id, int id)
    {
        List<Teacher> teachersList = null;
        Course course = course_repo.findById(id).orElse(null);
        Teacher teacher = teacher_repo.findById(teacher_id).orElse(null);
        if(course !=null && teacher !=null)
        {
            if(!course.getTeacher().contains(teacher))
            {
                teachersList = course.getTeacher();
                teachersList.add(teacher);
                course.setTeacher(teachersList);
                return course_repo.save(course);
            }
            else {
                throw new RuntimeException("teacher exixt in same course");
            }
        }
        else {
            throw new RuntimeException("course or teacher not exist in same course");
        }



    }
}
