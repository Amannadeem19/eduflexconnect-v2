package com.fyp.eduflexconnect.Controllers;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.GeneralPosts;
import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Response.ApiResponse;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.GeneralPostService;
import com.fyp.eduflexconnect.Services.SuperAdminService;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class GeneralPostController {
    @Autowired
    private GeneralPostService generalPostService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SuperAdminService superAdminService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createGeneralPost(@RequestBody GeneralPosts generalPosts,
                                                         @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin") || admin.getRole().equals("Super Admin")){
            GeneralPosts post = generalPostService.createPost(generalPosts, admin);
//            System.out.println(job1);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("created a post successfully");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
        else{
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("You are not authenticated to use this service");
            apiResponse.setStatus(true);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateGeneralPost(@PathVariable Long id,
                                                 @RequestBody GeneralPosts generalPost,
                                                 @RequestHeader("Authorization")String jwt)
            throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);
        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            GeneralPosts post = generalPostService.updatePost(id,generalPost, admin);
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
    public ResponseEntity<ApiResponse> deleteGeneralPost(@PathVariable Long id,
                                                         @RequestHeader("Authorization")String jwt)
            throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);
        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            generalPostService.deletePost(id, admin);
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
    public ResponseEntity<GeneralPosts> getPost(@PathVariable Long id, @RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            GeneralPosts generalPosts = generalPostService.getPostById(id);
            return new ResponseEntity<>(generalPosts, HttpStatus.CREATED);
        }
        else{

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<List<GeneralPosts>> getAllJob(@RequestHeader("Authorization")String jwt) throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);
        if (roles.contains("Role_STUDENT") || roles.contains("Role_TEACHER")){
            List<GeneralPosts> posts = generalPostService.getAllPosts();
            return new ResponseEntity<>(posts, HttpStatus.CREATED);
        }
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin") || admin.getRole().equals("Super Admin")){
            List<GeneralPosts> posts = generalPostService.getAllPosts();
            return new ResponseEntity<>(posts, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<GeneralPosts>> searchPosts(@RequestParam String query,
                                                          @RequestHeader("Authorization")String jwt)
            throws UserException, AnnouncementException {
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        SuperAdmin admin = superAdminService.findSuperAdminProfileByJwt(token);

        if(admin.getRole().equals("manager") || admin.getRole().equals("Admin")){
            List<GeneralPosts> posts = generalPostService.searchPostByQuery(query);
            return new ResponseEntity<>(posts, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
