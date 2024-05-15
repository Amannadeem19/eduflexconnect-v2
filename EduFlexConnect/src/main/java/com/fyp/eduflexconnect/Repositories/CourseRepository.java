package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Integer>
{
    @Query("SELECT c FROM Course c WHERE c.course_code = :courseCode")
    Course findByCode(@Param("courseCode") String courseCode);


}
