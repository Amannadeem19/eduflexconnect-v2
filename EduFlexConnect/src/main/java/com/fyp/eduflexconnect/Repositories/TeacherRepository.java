package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher, String>
{
    @Query("SELECT COUNT(t.login_password) > 0  from Teacher t where t.login_password = :password")
    boolean existsByLoginPassword(@Param("password") String password);

    @Query("SELECT COUNT(t.personal_info.cnic) > 0  from Teacher t where t.personal_info.cnic = :cnic")
    boolean existsByCnic(@Param("cnic") String cnic);

    public Teacher findByUsername(String username);


}
