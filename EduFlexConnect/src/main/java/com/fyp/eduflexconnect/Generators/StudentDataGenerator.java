package com.fyp.eduflexconnect.Generators;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fyp.eduflexconnect.Models.Registrations;
import com.fyp.eduflexconnect.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class StudentDataGenerator
{
    @Autowired
    DepartmentService depart_service;

    public String IdGenerator(Registrations registration)
    {
        String d_code;
        String campus_alpha;
        d_code = depart_service.GetDepartmentCodeByName(registration.getDegree());
        campus_alpha = registration.getCampus().substring(0,1).toUpperCase();

        return generateStudentId(campus_alpha, d_code);
    }

    private String generateStudentId(String campusCode, String departmentCode)
    {
        // Get the last two digits of the current year
        int currentYearLastTwoDigits = Integer.parseInt(new SimpleDateFormat("yy").format(new Date()));

        // Generate 4 random numbers
        String randomNumbers = generateRandomNumbers();

        // Create the student ID
        return String.format("%02d%s%s%s", currentYearLastTwoDigits, campusCode, departmentCode, randomNumbers);
    }

    private String generateRandomNumbers()
    {
        Random random = new Random();
        int randomValue = random.nextInt(10000);  // Generates a random number between 0 and 9999
        return String.format("%04d", randomValue);  // Formats the random number to have at least 4 digits
    }

    public String generatePassword()
    {
        final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWER = "abcdefghijklmnopqrstuvwxyz";
        final String DIGITS = "0123456789";
        final String SPECIAL_CHARS = "!@#$%^&*()-_=+";

        SecureRandom random = new SecureRandom();

        String allChars = UPPER + LOWER + DIGITS + SPECIAL_CHARS;
        StringBuilder password = new StringBuilder();

        // Ensure at least one special character
        password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        // Generate the rest of the password
        for (int i = 1; i < 10; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return password.toString();
    }
}
