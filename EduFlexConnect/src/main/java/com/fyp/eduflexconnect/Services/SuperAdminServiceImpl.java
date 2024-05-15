package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Repositories.SuperAdminRepository;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperAdminServiceImpl implements SuperAdminService{
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Override
    public SuperAdmin findSuperAdminProfileByJwt(String jwt) throws UserException {
        String username = jwtTokenProvider.getUsernameFromToken(jwt);
        SuperAdmin admin = superAdminRepository.findByUsername(username);
        if(admin == null){
            throw new UserException("Admin not found");
        }
        return admin;
    }
}
