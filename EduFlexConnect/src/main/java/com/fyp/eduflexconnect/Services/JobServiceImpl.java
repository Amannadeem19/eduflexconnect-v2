package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.Job;
import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Repositories.JobRepository;
import com.fyp.eduflexconnect.Repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Job createJob(Job job, SuperAdmin admin) throws AnnouncementException {
        Job job1 = new Job();
        job1.setTitle(job.getTitle());
        job1.setCompanyName(job.getCompanyName());
        job1.setLocation(job.getLocation());
        job1.setJob_type(job.getJob_type());
        job1.setDescription(job.getDescription());
        job1.setAdmin(admin);
        job1.setCreatedAt(LocalDateTime.now());
        return jobRepository.save(job1);
    }

    @Override
    public Job updateJob(Long id, Job job, SuperAdmin admin) throws AnnouncementException {
        Job updateJob = getJobById(id);
        if(updateJob == null){
            throw new AnnouncementException("No job found");
        }
        if(updateJob.getAdmin().getId() != admin.getId()){
            throw new AnnouncementException("You can not update this job");
        }
        if(job.getJob_type() != null){
            updateJob.setJob_type(job.getJob_type());
        }
        if(job.getTitle() != null){
            updateJob.setTitle(job.getTitle());
        }
        if(job.getDescription() != null){
            updateJob.setDescription(job.getDescription());
        }
        if(job.getLocation() != null){
            updateJob.setLocation(job.getLocation());
        }
        if(job.getCompanyName() != null){
            updateJob.setCompanyName(job.getCompanyName());
        }
        updateJob.setCreatedAt(LocalDateTime.now());
        return jobRepository.save(updateJob);
    }

    @Override
    public void deleteJob(Long id, SuperAdmin admin) throws AnnouncementException {
        Job job = getJobById(id);
        if (job.getAdmin().getId() != admin.getId()){
            throw new AnnouncementException("You cannot delete the job");
        }
        jobRepository.deleteById(id);
    }

    @Override
    public Job getJobById(Long id) throws AnnouncementException {
        Job job = jobRepository.findById(id)
                .orElseThrow(()-> new AnnouncementException("No job found with this id" + id));
        return job;
    }

    @Override
    public List<Job> getAllJobs() throws AnnouncementException{
        List<Job> jobs = jobRepository.findAll();
//        if(jobs.isEmpty()){
//            throw new AnnouncementException("No job found");
//        }
        return jobs;
    }

    @Override
    public List<Job> SearchJobByQuery(String query) throws AnnouncementException {
       List<Job> jobs = jobRepository.searchJobs(query);
        if(jobs.isEmpty()){
            throw new AnnouncementException("No job found");
        }
        return jobs;
    }
}
