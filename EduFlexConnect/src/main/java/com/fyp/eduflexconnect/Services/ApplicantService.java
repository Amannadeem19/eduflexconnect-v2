package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.Job;
import com.fyp.eduflexconnect.Models.JobApplicant;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ApplicantService {

    public JobApplicant applyForJob(String username, String email, MultipartFile file, Job job)
            throws AnnouncementException,  IOException;
    public List<JobApplicant> getAllJobApplicants(Job job) throws AnnouncementException, IOException;
    public List<JobApplicant> findAllApplicantsByJobId(Long id) throws AnnouncementException, IOException;

}
