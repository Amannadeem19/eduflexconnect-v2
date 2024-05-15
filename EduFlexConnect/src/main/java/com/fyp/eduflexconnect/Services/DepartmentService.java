package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Models.Department;
import com.fyp.eduflexconnect.Repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService
{
    @Autowired
    DepartmentRepository department_repo;

    public Department AddDepartment(Department department)
    {
        return department_repo.save(department);
    }

    public List<Department> ListDepartment()
    {
        return department_repo.findAll();
    }

    public Department GetDepartment(int id)
    {
        return department_repo.findById(id).orElse(null);
    }



    public String GetDepartmentCodeByName(String department)
    {
        return department_repo.findCodeByName(department);
    }
}
