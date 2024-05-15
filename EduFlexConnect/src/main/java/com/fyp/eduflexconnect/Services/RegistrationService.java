package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Models.RegistrationPreference;
import com.fyp.eduflexconnect.Models.Registrations;
import com.fyp.eduflexconnect.Repositories.RegistrationPreferenceRepository;
import com.fyp.eduflexconnect.Repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.spi.RegisterableService;
import java.util.List;

@Service
public class RegistrationService
{

    @Autowired
    private RegistrationRepository register_repo;
    @Autowired
    private RegistrationPreferenceRepository register_pref_repo;


    public Registrations addRegistration(Registrations register)
    {
        RegistrationPreference newPref = register.getRegistrationReference();
        register.setRegistrationReference(newPref);
        return register_repo.save(register);
    }

    public boolean checkArnExistance(int arn)
    {
        return register_repo.existsById(arn);
    }

    public List<Registrations> listAllRegistration()
    {

        return register_repo.findAll();
    }

    public Registrations GetRegistrationByArn(int arn)
    {
        return register_repo.findById(arn).orElse(null);
    }



}
