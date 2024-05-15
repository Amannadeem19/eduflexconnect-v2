package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Repositories.SuperAdminRepository;
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
public class SuperAdminDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SuperAdminRepository superAdminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SuperAdmin superAdmin = superAdminRepository.findByUsername(username);
        List<SimpleGrantedAuthority> adminAuths = new ArrayList<>();
        if (superAdmin == null){
            throw new UsernameNotFoundException("Admin not found with this username "+ username);
        }
        if(superAdmin.getRole().equals("manager")){
            Set<SimpleGrantedAuthority> authSet = ApplicationUserRoles.MANAGER_ACADEMICS.getGrantedAuthorities();
            List<SimpleGrantedAuthority> adminAuth2 = new ArrayList<>(authSet);
            adminAuths.addAll( adminAuth2);

        }
        if(superAdmin.getRole().equals("Admin")){
            Set<SimpleGrantedAuthority> authSet = ApplicationUserRoles.ADMIN.getGrantedAuthorities();
            List<SimpleGrantedAuthority> adminAuth2 = new ArrayList<>(authSet);
            adminAuths.addAll( adminAuth2);
        }
        if(superAdmin.getRole().equals("society")){
            Set<SimpleGrantedAuthority> authSet = ApplicationUserRoles.SOCIETY_WALE.getGrantedAuthorities();
            List<SimpleGrantedAuthority> adminAuth2 = new ArrayList<>(authSet);
            adminAuths.addAll( adminAuth2);
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(superAdmin.getUsername())
                .password(superAdmin.getPassword())
                .authorities(adminAuths)
                .build();
        return userDetails;

    }
}
