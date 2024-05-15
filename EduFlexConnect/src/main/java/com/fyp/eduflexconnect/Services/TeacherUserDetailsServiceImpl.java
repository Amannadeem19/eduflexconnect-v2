package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Repositories.TeacherRepository;
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
public class TeacherUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByUsername(username);
        if(teacher == null){
            throw new UsernameNotFoundException("User not found with this username" + username);

        }
        Set<SimpleGrantedAuthority> authSet = ApplicationUserRoles.TEACHER.getGrantedAuthorities();
        List<SimpleGrantedAuthority> studentAuths = new ArrayList<>(authSet);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(teacher.getUsername())
                .password(teacher.getLogin_password())
                .authorities(studentAuths)
                .build();
        return userDetails;

    }
}
