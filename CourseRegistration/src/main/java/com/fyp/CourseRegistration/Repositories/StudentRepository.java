package com.fyp.CourseRegistration.Repositories;

import com.fyp.CourseRegistration.Models.Section;
import com.fyp.CourseRegistration.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,String>
{
    public Optional<Student> findById(String id);
    @Query("SELECT DISTINCT s from Student s where s.personal_info.full_name LIKE %:query% OR s.id LIKE %:query%")
    public List<Student> searchStudents(@Param("query") String query);
    @Query("SELECT COUNT(s.login_password) > 0 FROM Student s WHERE s.login_password = :password")
    boolean existsByLoginPassword(@Param("password") String password);

    @Query("Select s.section from Student s where s.id = :student_id")
    Section findSection(@Param("student_id") String student_id);

    List<Student> findBySection(Section section);
}
