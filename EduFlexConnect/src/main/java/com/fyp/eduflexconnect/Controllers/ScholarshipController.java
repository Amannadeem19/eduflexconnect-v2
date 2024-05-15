package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Job;
import com.fyp.eduflexconnect.Models.Scholarship;
import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Response.ApiResponse;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.ScholarshipService;
import com.fyp.eduflexconnect.Services.SuperAdminService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarship")
public class ScholarshipController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private SuperAdminService superAdminService;
    @Autowired
    private ScholarshipService scholarshipService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createScholarship(@RequestBody Scholarship scholarship,
                                                         @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin") || admin.getRole().equals("Super Admin")){
            Scholarship scholarship1 = scholarshipService.createScholarship(scholarship, admin);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("created a scholarship successfully");
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
    public ResponseEntity<ApiResponse> updateScholarship(@PathVariable Long id, @RequestBody  Scholarship scholarship, @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            Scholarship scholarship1 = scholarshipService.updateScholarship(id,scholarship, admin);
//            System.out.println(job1);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("updated a scholarship successfully");
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
    public ResponseEntity<ApiResponse> deleteScholarship(@PathVariable Long id, @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            scholarshipService.deleteScholarship(id, admin);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("deleted scholarship successfully");
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
    public ResponseEntity<Scholarship> getScholarship(@PathVariable Long id, @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            Scholarship scholarship = scholarshipService.getScholarshipById(id);
            return new ResponseEntity<>(scholarship, HttpStatus.CREATED);
        }
        else{

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<List<Scholarship>> getAllScholarship(@RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
        if(roles.contains("Role_STUDENT")|| roles.contains("Role_TEACHER")){
            List<Scholarship> scholarships = scholarshipService.getAllScholarships();
            return new ResponseEntity<>(scholarships, HttpStatus.OK);
        }
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if( admin.getRole().equals("Super Admin")|| admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            List<Scholarship> scholarships = scholarshipService.getAllScholarships();
            return new ResponseEntity<>(scholarships, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Scholarship>> searchScholarship(@RequestParam String query,@RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            List<Scholarship> scholarships = scholarshipService.SearchScholarshipByQuery(query);
            return new ResponseEntity<>(scholarships, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
