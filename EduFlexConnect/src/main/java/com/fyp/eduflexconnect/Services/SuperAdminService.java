package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.SuperAdmin;

public interface SuperAdminService {
    public SuperAdmin findSuperAdminProfileByJwt(String jwt) throws UserException;
}
