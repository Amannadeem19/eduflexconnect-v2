package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Generators.TeacherPasswordGenerator;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Models.TeacherContactInfo;
import com.fyp.eduflexconnect.Models.TeacherPersonalInfo;
import com.fyp.eduflexconnect.Repositories.TeacherRepository;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService
{
    @Autowired
    private TeacherRepository teacher_repo;

    @Autowired
    private TeacherPasswordGenerator password_generator;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public boolean checkUsernameExistance(String username)
    {
        return teacher_repo.existsById(username);
    }
    public boolean checkPasswordExistance(String password)
    {
        return teacher_repo.existsByLoginPassword(password);
    }



    public Teacher AddTeacher(Teacher teacher) throws UserException
    {
        // ISKO TEST KARNA HE
        boolean check_teacher = teacher_repo.existsByCnic(teacher.getPersonal_info().getCnic());
        if (check_teacher)
        {
            throw new UserException("Teacher already exist with this username " + teacher.getUsername());
        }

        String password;
        do
        {
            password = password_generator.generatePassword();
        }while(checkPasswordExistance(password));
        teacher.setLogin_password(password);



        TeacherPersonalInfo personal_info = teacher.getPersonal_info();
        teacher.setPersonal_info(personal_info);

        TeacherContactInfo contact_info = teacher.getContact_info();
        teacher.setContact_info(contact_info);




        System.out.println(checkPasswordExistance(password));


        Teacher new_teacher = teacher_repo.save(teacher);
        System.out.println(checkPasswordExistance(password));
        return new_teacher;
    }

    public List<Teacher> getAllTeachers()
    {
        return teacher_repo.findAll();
    }

    public Teacher getTeacher(String username)
    {
        return teacher_repo.findById(username).orElse(null);
    }

    //ADDED

    public Teacher findTeacherProfileByJwt(String jwt) throws UserException {
        String username = jwtTokenProvider.getUsernameFromToken(jwt);
        Teacher teacher = teacher_repo.findByUsername(username);
        if(teacher == null){
            throw new UserException("User not found");
        }
        return teacher;
    }


    public Teacher findTeacherByUsername(String username) throws UserException
    {
        Teacher teacher = teacher_repo.findByUsername(username);
        if(teacher == null)
        {
            throw new UserException("Teacher not found with username: "+username);
        }
        return teacher;
    }

//    public void getTeacherCurrentCourses()
//    {
//
//    }
}
