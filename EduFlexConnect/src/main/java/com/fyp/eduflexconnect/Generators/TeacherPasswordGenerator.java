package com.fyp.eduflexconnect.Generators;

import com.fyp.eduflexconnect.Services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class TeacherPasswordGenerator
{

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
