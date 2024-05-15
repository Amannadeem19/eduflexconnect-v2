package com.fyp.eduflexconnect.Generators;

import com.fyp.eduflexconnect.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SectionIdGenerator
{
    @Autowired
    DepartmentService depart_service;

    public String GenerateSectionId(String department,char alphabet)
    {
        String sectionId;
        String d_code;

        d_code = depart_service.GetDepartmentCodeByName(department);

        // Get the last two digits of the current year
        int currentYearLastTwoDigits = Integer.parseInt(new SimpleDateFormat("yy").format(new Date()));

        sectionId = String.format("%02d%s%c", currentYearLastTwoDigits, d_code, alphabet);
        return sectionId;
    }
}
