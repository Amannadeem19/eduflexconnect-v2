package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.FilesEntity;
import com.fyp.eduflexconnect.Models.Job;
import com.fyp.eduflexconnect.Models.JobApplicant;
import com.fyp.eduflexconnect.Repositories.ApplicantJobRepository;
import com.fyp.eduflexconnect.Repositories.FilesRepository;
import com.fyp.eduflexconnect.Util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private ApplicantJobRepository applicantJobRepository;
    @Override
    public JobApplicant applyForJob(String username, String email, MultipartFile file, Job job) throws
            AnnouncementException, IOException {
        if(job == null){
            throw new AnnouncementException("Job not found , you can not apply");
        }
        JobApplicant jobApplicants = new JobApplicant();
        jobApplicants.setEmail(email);
        jobApplicants.setUsername(username);
        jobApplicants.setJob(job);
//        adding a resume
        FilesEntity resume = new FilesEntity();
        resume.setName(file.getOriginalFilename());
        resume.setType(file.getContentType());
        resume.setFileData(FileUtils.compressImage(file.getBytes()));
        resume.setJobApplicant(jobApplicants);
//        filesRepository.save(resume);
//        add into the applicant
        jobApplicants.setFilesEntity(resume);
        return applicantJobRepository.save(jobApplicants);
    }

    @Override
    public List<JobApplicant> getAllJobApplicants(Job job) throws
            AnnouncementException, IOException {
        return null;
    }

    @Override
    public List<JobApplicant> findAllApplicantsByJobId(Long id) throws AnnouncementException, IOException {
        List<JobApplicant> applicants = applicantJobRepository.findByJobId(id);
        return applicants;
    }
}
