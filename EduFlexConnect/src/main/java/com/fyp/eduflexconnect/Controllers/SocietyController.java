package com.fyp.eduflexconnect.Controllers;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Job;
import com.fyp.eduflexconnect.Models.Society;
import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Response.ApiResponse;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.SocietyService;
import com.fyp.eduflexconnect.Services.SuperAdminService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/society")
public class SocietyController {
    @Autowired
    private SocietyService societyService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SuperAdminService superAdminService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createSocietyPost(@RequestBody Society society, @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("society")){
            Society society1 = societyService.createSocietyPost(society, admin);
//            System.out.println(job1);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("created successfully");
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
    public ResponseEntity<ApiResponse> updateSocietyPost(@PathVariable Long id,
                                                         @RequestBody Society post,
                                                         @RequestHeader("Authorization")String jwt)
            throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);
        if(admin.getRole().equals("society")){
            Society society = societyService.updateSocietyPost(id,post, admin);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("updated post successfully");
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
    public ResponseEntity<ApiResponse> deleteSocietyPost(@PathVariable Long id,
                                                         @RequestHeader("Authorization")String jwt)
            throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);
        if(admin.getRole().equals("society")){
            societyService.deleteSocietyPost(id, admin);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("deleted successfully");
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
    public ResponseEntity<Society> getSocietyPost(@PathVariable Long id, @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("society")){
            Society society = societyService.getSocietyPostById(id);
            return new ResponseEntity<>(society, HttpStatus.CREATED);
        }
        else{

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<List<Society>> getAllSocietyPosts(@RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
        if(roles.contains("Role_STUDENT") || roles.contains("Role_TEACHER")){
            List<Society> posts = societyService.getAllSocietyPosts();
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("society") || admin.getRole().equals("Super Admin") ||  admin.getRole().equals("Admin")){
            List<Society> posts = societyService.getAllSocietyPosts();
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Society>> searchSocietyPosts(@RequestParam String query,
                                                          @RequestHeader("Authorization")String jwt)
            throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("society")){
            List<Society> posts = societyService.searchSocietyPostByQuery(query);
            return new ResponseEntity<>(posts, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
