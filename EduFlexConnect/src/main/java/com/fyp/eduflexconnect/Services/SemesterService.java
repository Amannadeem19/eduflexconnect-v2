package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Models.Semester;
import com.fyp.eduflexconnect.Repositories.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SemesterService
{
    @Autowired
    SemesterRepository semester_repo;

    public Semester AddSemester(Semester semester)
    {
        Semester check_semester = semester_repo.findById(semester.getName()+semester.getYear()).orElse(null);
        if(check_semester != null)
        {
            throw new CustomException("This semester already exist");
        }
        if(semester_repo.existsByStatusIsCurrent())
        {
            throw new CustomException("Semester(s) already exist in current status");
        }
        semester.setStatus("Current");
        return semester_repo.save(semester);
    }

    public String EndSemester(String semester_id,LocalDate end_date)
    {
        Semester existing_semester =  semester_repo.findById(semester_id).orElse(null);

        if(existing_semester != null && existing_semester.getStatus().equals("Current"))
        {
            existing_semester.setEnd_date(end_date);
            existing_semester.setStatus("Passed");
            semester_repo.save(existing_semester);
            return existing_semester.getSemester_id()+" ended on "+existing_semester.getEnd_date();
        }
        else if(existing_semester != null && existing_semester.getStatus().equals("Passed"))
        {
            return "Already passed";
        }

        else
        {
            return "Semester not found";
        }
    }

    public String startCourseRegistration(String semester_id)
    {
       Semester existing_semester = semester_repo.findById(semester_id).orElse(null);
       if(existing_semester == null)
       {
           throw new CustomException("Invalid Semester.");
       }
       else if(existing_semester.getStatus().equals("Passed"))
       {
            throw new CustomException("This operation can not be performed on already passed semester");
       }
       else if(existing_semester.isRegistrationOpen())
       {
           return "Registration already started";
       }
       else
       {
           existing_semester.setRegistrationOpen(true);
           semester_repo.save(existing_semester);
           return "Course registration period started for semester: "+existing_semester.getSemester_id();
       }
    }
    public String endCourseRegistration(String semester_id)
    {
        Semester existing_semester = semester_repo.findById(semester_id).orElse(null);
        if(existing_semester == null)
        {
            throw new CustomException("Invalid Semester.");
        }
        else if(existing_semester.getStatus().equals("Passed"))
        {
            throw new CustomException("This operation can not be performed on already passed semester");
        }
        else if(!existing_semester.isRegistrationOpen())
        {
            return "Registration already ended";
        }
        else
        {
            existing_semester.setRegistrationOpen(false);
            semester_repo.save(existing_semester);
            return "Course registration period ended for semester: "+existing_semester.getSemester_id();
        }
    }
    public String currentSemester()
    {
        return semester_repo.currentSemesterId();
    }
    public Semester GetSemester(String id)
    {
        return semester_repo.findById(id).orElse(null);
    }
    public List<Semester> GetSemesters()
    {
        return semester_repo.findAll();
    }
}
