package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.Job;
import com.fyp.eduflexconnect.Models.SuperAdmin;

import java.util.List;

public interface JobService {

    public Job createJob(Job job, SuperAdmin admin) throws AnnouncementException;
    public Job updateJob(Long id, Job job,  SuperAdmin admin) throws AnnouncementException;
    public void deleteJob(Long id,  SuperAdmin admin) throws AnnouncementException;
    public Job getJobById(Long id) throws AnnouncementException;
    public List<Job> getAllJobs() throws AnnouncementException;

    public List<Job> SearchJobByQuery(String query) throws AnnouncementException;

}
