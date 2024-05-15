package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Models.Registrations;
import com.fyp.eduflexconnect.Services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fyp/registration")
public class RegistrationController
{

    @Autowired
    private RegistrationService registarion_service;

    @PostMapping("/addregistration")
    public Registrations newRegistration(@RequestBody Registrations register)
    {
        return registarion_service.addRegistration(register);
    }
    @GetMapping("/listregistration")
    public List<Registrations> ListRegistrations()
    {

        return registarion_service.listAllRegistration();
    }
}
