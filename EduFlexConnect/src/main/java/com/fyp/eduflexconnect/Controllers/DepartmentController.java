package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Models.Department;
import com.fyp.eduflexconnect.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fyp/department")
public class DepartmentController
{
    @Autowired
    DepartmentService department_service;

    @PostMapping("/adddepartment")
    public Department addDepartment(@RequestBody Department department)
    {
        return department_service.AddDepartment(department);
    }

    @GetMapping("listdepartment")
    public List<Department> listDepartment()
    {
        return department_service.ListDepartment();
    }

    @GetMapping("/getcode/{department_name}")
    public String getcode(@PathVariable String department_name)
    {
        return department_service.GetDepartmentCodeByName(department_name);
    }

}
