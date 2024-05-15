package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Models.SemesterName;
import com.fyp.eduflexconnect.Repositories.SemesterNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterNameService
{
    @Autowired
    private SemesterNameRepository semester_name_repo;

    public SemesterName AddSemesterName(SemesterName name)
    {

        return semester_name_repo.save(name);
    }
    public List<SemesterName> GetSemestersName()
    {
        return semester_name_repo.findAll();
    }
}
