package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Repositories.StudentRepository;
import com.fyp.eduflexconnect.SecurityConfig.ApplicationUserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Student student = studentRepository.findById(id).orElse(null);
        if(student == null){
            throw new UsernameNotFoundException("User does not found with this ID" + id);

        }
        Set<SimpleGrantedAuthority> authSet = ApplicationUserRoles.STUDENT.getGrantedAuthorities();
        List<SimpleGrantedAuthority> studentAuths = new ArrayList<>(authSet);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(student.getId())
                .password(student.getLogin_password())
                .authorities(studentAuths)
                .build();
        return userDetails;

    }
}

