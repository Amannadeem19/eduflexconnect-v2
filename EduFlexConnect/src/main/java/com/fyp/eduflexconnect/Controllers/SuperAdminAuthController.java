package com.fyp.eduflexconnect.Controllers;


import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Repositories.SuperAdminRepository;
import com.fyp.eduflexconnect.Response.AuthResponse;
import com.fyp.eduflexconnect.SecurityConfig.ApplicationUserRoles;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.SuperAdminDetailsServiceImpl;
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
@RequestMapping("/api/admin")
public class SuperAdminAuthController {

        @Autowired
        private SuperAdminRepository superAdminRepository;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private JwtTokenProvider jwtTokenProvider;
        @Autowired
        private SuperAdminDetailsServiceImpl superAdminDetailsService;
        @PostMapping("/signup")
        public ResponseEntity<AuthResponse> createSuperAdmin(@RequestBody SuperAdmin superAdmin,
                                                             @RequestHeader("Authorization")String jwt)
                throws UserException {
            List<SimpleGrantedAuthority> adminAuths = new ArrayList<>();

            SuperAdmin check_admin = superAdminRepository.findByUsername(superAdmin.getUsername());
            if (superAdmin.getRole() == null){
                throw new UserException("Admin not created any role , no role is assigned");
            }
            if (check_admin != null){
                throw new UserException("SuperAdmin already exist with this username " + superAdmin.getUsername());
            }

           SuperAdmin superAdmin1 = new SuperAdmin();
            superAdmin1.setUsername(superAdmin.getUsername());
            superAdmin1.setFullName(superAdmin.getFullName());
            superAdmin1.setImage(superAdmin.getImage());
            superAdmin1.setRole(superAdmin.getRole());
            superAdmin1.setPassword(passwordEncoder.encode( superAdmin.getPassword() ));

            SuperAdmin superAdmin2 = superAdminRepository.save(superAdmin1);
            if(superAdmin2.getRole().equals("manager")){
                Set<SimpleGrantedAuthority> authSet = ApplicationUserRoles.MANAGER_ACADEMICS.getGrantedAuthorities();
                List<SimpleGrantedAuthority> adminAuth2 = new ArrayList<>(authSet);
                adminAuths.addAll(adminAuth2);

            }
            if(superAdmin2.getRole().equals("society")){
                Set<SimpleGrantedAuthority> authSet = ApplicationUserRoles.SOCIETY_WALE.getGrantedAuthorities();
                List<SimpleGrantedAuthority> adminAuth2 = new ArrayList<>(authSet);
                adminAuths.addAll(adminAuth2);
            }
            System.out.println("auths" + adminAuths);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    superAdmin2.getUsername(),
                    superAdmin2.getPassword(),
                    adminAuths
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token =  jwtTokenProvider.generateToken(authentication);
            AuthResponse res = new AuthResponse(token, true);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        @PostMapping("/signin")
        public ResponseEntity<AuthResponse> logIn(@RequestBody SuperAdmin superAdmin) {
            String username = superAdmin.getUsername();
            String password = superAdmin.getPassword();
            Authentication authentication = authenticate(username, password);
            String token = jwtTokenProvider.generateToken(authentication);
            System.out.println(token);
            AuthResponse res = new AuthResponse(token, true);
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

        }
        private Authentication authenticate (String username, String password) {
            UserDetails userDetails = superAdminDetailsService.loadUserByUsername(username);
            if (userDetails == null) {
                throw new BadCredentialsException("Invalid username");
            }
            if (!password.equals(userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid Password");
            }
            return new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
        }


}
