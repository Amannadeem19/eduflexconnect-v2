package com.fyp.eduflexconnect.Repositories;

import com.fyp.eduflexconnect.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer>
{
    @Query("Select d.department_code from Department d where d.department_name = :departName")
    String findCodeByName(@Param("departName") String departName);

    @Query("Select d from Department d where d.department_name = :departName")
    Department findByDepartment_name(@Param("departName") String departName);



}
