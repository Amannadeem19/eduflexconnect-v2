package com.fyp.eduflexconnect.Generators;



import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Models.TeacherPersonalInfo;
import com.fyp.eduflexconnect.Services.TeacherService;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Random;

@Component
public class TeacherUsernameGenerator implements IdentifierGenerator
{
    @Autowired
    @Lazy
    private TeacherService teacher_service;

    public TeacherUsernameGenerator(@Lazy TeacherService teacher_service)
    {
        this.teacher_service = teacher_service;
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException
    {
        String user_name;
        String first_name = ((Teacher) object).getPersonal_info().getFirst_name();
        String last_name = ((Teacher) object).getPersonal_info().getLast_name();
        do
        {
            user_name = generateUsername(first_name,last_name);
        }while (teacher_service.checkUsernameExistance(user_name));
        return user_name;
    }

    private String generateUsername(String first_name,String last_name)
    {
        int random = generateRandomNumber();
        if(String.valueOf(random).length() == 1)
        {
            return String.format("%s.%s%01d",first_name.toLowerCase(),last_name.toLowerCase(),random);
        }
        else
        {
            return String.format("%s.%s%02d",first_name.toLowerCase(),last_name.toLowerCase(),random);
        }

    }

    private static int generateRandomNumber() {
        // Generate a random number between 0 and 99
        return new Random().nextInt(100);
    }
}
