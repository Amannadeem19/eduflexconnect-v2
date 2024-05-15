package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Repositories.TeacherRepository;
import com.fyp.eduflexconnect.Response.AuthResponse;
import com.fyp.eduflexconnect.SecurityConfig.ApplicationUserRoles;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.TeacherService;
import com.fyp.eduflexconnect.Services.TeacherUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/teacher")
public class TeacherAuthController {
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private TeacherUserDetailsServiceImpl teacherUserDetailsService;
    @Autowired
    private TeacherService teacher_service;

    public TeacherAuthController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
//    username,fullName, age, qualification, experience, contact_no, password;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createTeacher(@RequestBody Teacher teacher) throws UserException
    {
        Teacher new_teacher = teacher_service.AddTeacher(teacher);

        Set<SimpleGrantedAuthority> authSet = ApplicationUserRoles.TEACHER.getGrantedAuthorities();
        List<SimpleGrantedAuthority> teacherAuths = new ArrayList<>(authSet);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new_teacher.getUsername(),
                new_teacher.getLogin_password(),
                teacherAuths
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =  jwtTokenProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(token, true);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> logIn(@RequestBody Teacher teacher) {
        System.out.println(teacher);
        String username = teacher.getUsername();
        String password = teacher.getLogin_password();
        Authentication authentication = authenticate(username, password);
        String token = jwtTokenProvider.generateToken(authentication);
        System.out.println(token);
        AuthResponse res = new AuthResponse(token, true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }
    private Authentication authenticate (String username, String password)
    {

        UserDetails userDetails = teacherUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!password.equals(userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}
