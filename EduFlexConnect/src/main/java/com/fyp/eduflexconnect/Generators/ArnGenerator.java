package com.fyp.eduflexconnect.Generators;

import com.fyp.eduflexconnect.Services.RegistrationService;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
public class ArnGenerator implements IdentifierGenerator
{
    @Autowired
    @Lazy
    private final RegistrationService register_service;

    public ArnGenerator(@Lazy RegistrationService registerService)
    {

        register_service = registerService;
    }


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException
    {
        String arn;
        do
        {
            arn=generateArn();
        }while(register_service.checkArnExistance(Integer.parseInt(arn)));


        return Integer.valueOf(arn);
    }

    public String generateArn()
    {
        String applyingYear = extractYear(LocalDate.now());

        // Extract the last two digits from the applying year
        String lastTwoDigitsOfYear = applyingYear.substring(2);

        // Generate ARN using the format "YYXXXXX where YY is extracted last digits"
        String arn = lastTwoDigitsOfYear + generateRandomDigits(5);

        return arn;
    }

    private String generateRandomDigits(int length) {
        // Generate a random string of digits
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    private static String extractYear(LocalDate date) {
        // Create a DateTimeFormatter for the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY");

        // Format the year using the formatter
        return date.format(formatter);
    }
}
