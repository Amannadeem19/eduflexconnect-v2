package com.fyp.eduflexconnect.Controllers;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Job;
import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Response.ApiResponse;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.JobService;
import com.fyp.eduflexconnect.Services.SuperAdminService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobController {
        @Autowired
        private JwtTokenProvider jwtTokenProvider;
        @Autowired
        private JobService jobService;
        @Autowired
        private SuperAdminService superAdminService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createJob(@RequestBody Job job, @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            Job job1 = jobService.createJob(job, admin);
//            System.out.println(job1);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("created a job successfully");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
        else{
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("not created");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateJob(@PathVariable Long id,@RequestBody Job job, @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            Job job1 = jobService.updateJob(id,job, admin);
//            System.out.println(job1);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("updated a job successfully");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
        else{
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("not updated");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long id, @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
           jobService.deleteJob(id, admin);
//            System.out.println(job1);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("deleted job successfully");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
        else{
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("Not deleted");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Long id, @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            Job job = jobService.getJobById(id);
            return new ResponseEntity<>(job, HttpStatus.CREATED);
        }
        else{

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<List<Job>> getAllJob(@RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
        if(roles.contains("Role_STUDENT") || roles.contains("Role_TEACHER")){
            List<Job> jobs = jobService.getAllJobs();
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        }
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin") || admin.getRole().equals("Super Admin")){
            List<Job> jobs = jobService.getAllJobs();
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(@RequestParam String query,@RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            List<Job> jobs = jobService.SearchJobByQuery(query);
            return new ResponseEntity<>(jobs, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
