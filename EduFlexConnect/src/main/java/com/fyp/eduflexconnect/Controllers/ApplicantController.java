package com.fyp.eduflexconnect.Controllers;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.FilesEntity;
import com.fyp.eduflexconnect.Models.Job;
import com.fyp.eduflexconnect.Models.JobApplicant;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Repositories.FilesRepository;
import com.fyp.eduflexconnect.Response.ApiResponse;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.ApplicantService;
import com.fyp.eduflexconnect.Services.FilesEntityService;
import com.fyp.eduflexconnect.Services.JobService;
import com.fyp.eduflexconnect.Services.StudentService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import com.fyp.eduflexconnect.Util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/jobs")
public class ApplicantController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicantService applicantService;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private FilesEntityService filesEntityService;

    // only for students
    @PostMapping("/apply/{id}")
    public ResponseEntity<ApiResponse> applyJob(@PathVariable Long id,
                                                @RequestParam("email") String email,
                                                @RequestParam("document") MultipartFile file,
                                                @RequestHeader("Authorization")String jwt)
            throws UserException, AnnouncementException, IOException
    {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        String username = jwtTokenProvider.getUsernameFromToken(token);
        Student student = studentService.findStudentProfileByJwt(token);
        boolean checkRole = jwtTokenProvider.getAuthorities(token);

        if(checkRole){
            System.out.println("You are student");
            Job job = jobService.getJobById(id);
            JobApplicant jobApplicant1 = applicantService.applyForJob(username,email, file, job);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Applied Successfully");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
        else{
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Not Applied Successfully");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/download/{jobId}")
    public ResponseEntity<byte[]> downloadApplications(@PathVariable Long jobId ) throws AnnouncementException,IOException {
        Job job = jobService.getJobById(jobId);
        List<JobApplicant> applicants = applicantService.findAllApplicantsByJobId(jobId);
        List<FilesEntity> filesEntityList = new ArrayList<>();
        for (JobApplicant applicant:applicants){
            FilesEntity filesEntity = filesEntityService.findFileByApplicantId(applicant.getId());
            filesEntityList.add(filesEntity);
        }
        if (filesEntityList.isEmpty()) {
            // Handle the case where no valid files were found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipFile = new ZipOutputStream(zipOutputStream)) {
            for (FilesEntity filesEntity : filesEntityList) {
                // Add each file to the ZIP archive
                ZipEntry zipEntry = new ZipEntry(filesEntity.getName());
                zipFile.putNextEntry(zipEntry);
                zipFile.write(FileUtils.decompressImage(filesEntity.getFileData()));
                zipFile.closeEntry();
            }
        } catch (IOException e) {
            // Handle the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Set headers for the ZIP response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("files.zip").build());

        // Return the ZIP archive as a byte array
        return new ResponseEntity<>(zipOutputStream.toByteArray(), headers, HttpStatus.OK);


    }
}
