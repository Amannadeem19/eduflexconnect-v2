package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Generators.StudentDataGenerator;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.StudentRepository;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService
{
    @Autowired
    private StudentRepository student_repo;
    @Autowired
    private RegistrationService registration_service;
    @Autowired
    private SectionService section_service;
    @Autowired
    private StudentDataGenerator student_data_generator;
    //Added
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //END

    public Student GenerateStudentData(int arn,String section_id)
    {
        if(registration_service.checkArnExistance(arn))
        {
            System.out.println("Already Student");
            return null;
        }
        //Creating new Entities to save new student data
        Student new_student = new Student();
        StudentPersonalInfo personalInfo = new StudentPersonalInfo();
        StudentContactInfo contactInfo = new StudentContactInfo();

        Registrations registration = registration_service.GetRegistrationByArn(arn);
        String student_id;
        String degree;
        String batch;
        Section section;
        String campus;
        String status;
        String password;
        do
        {
            student_id = student_data_generator.IdGenerator(registration);

        }while(student_repo.existsById(student_id));

        degree = registration.getDegree();
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        batch = String.valueOf(currentYear);
        section = section_service.GetSection(section_id);
        campus = registration.getCampus();
        status = "Current";

        do
        {
            password = student_data_generator.generatePassword();

        }while(student_repo.existsByLoginPassword(password));
        System.out.println(student_repo.existsByLoginPassword(password));

        // Saving the student entity
        new_student.setId(student_id);
        new_student.setDegree(degree);
        new_student.setBatch(batch);
        new_student.setSection(section);
        new_student.setCampus(campus);
        new_student.setStatus(status);
        new_student.setLogin_password(password);

        // Saving the contactInfo

        contactInfo.setStreet_address(registration.getStreetAddress());
        contactInfo.setCity(registration.getCity());
        contactInfo.setHome_phone(registration.getHomePhone());
        contactInfo.setFather_cnic(registration.getFatherCnic());
        contactInfo.setFather_name(registration.getFatherName());
        new_student.setContact_info(contactInfo);
        contactInfo.setStudent(new_student);

        // saving the personalInfo

        personalInfo.setFull_name(registration.getFullName());
        personalInfo.setGender(registration.getGender());
        personalInfo.setDob(registration.getDob());
        personalInfo.setCnic(registration.getCnic());
        personalInfo.setEmail(registration.getEmail());
        personalInfo.setMobile(registration.getMobile());
        new_student.setPersonal_info(personalInfo);
        personalInfo.setStudent(new_student);


        student_repo.save(new_student);
        System.out.println(student_repo.existsByLoginPassword(password));
        return new_student;

    }

    public List<Student> GetAllStudent()
    {
        return student_repo.findAll();
    }

    //ADDED

    public Student findStudentByUsername(String id) throws UserException {
        Student student = student_repo.findById(id).orElse(null);
        if(student == null){
            throw new UserException("User does not found with id " +  id);
        }
        return student;
    }
    public Student findStudentProfileByJwt(String jwt) throws UserException {
        String id = jwtTokenProvider.getUsernameFromToken(jwt);
        Student student = student_repo.findById(id).orElse(null   );
        if(student == null){
            throw new UserException("User does not found with id" + id);
        }
        return student;
    }


    public Student updateStudent(Student req_student,String id ) throws UserException{
        Student student = student_repo.findById(id).orElse(null);
        if (student == null)
        {
            throw new UserException("Student not found with this id: "+ id);
        }
        if(!req_student.getId().equals(id))
        {
            throw new UserException("this is not your profile");
        }
        if(req_student.getPersonal_info().getFull_name() != null)
        {
            student.getPersonal_info().setFull_name(req_student.getPersonal_info().getFull_name());
        }
        if (req_student.getPersonal_info().getDob() != null)
        {
            student.getPersonal_info().setDob(req_student.getPersonal_info().getDob());
        }
        if (req_student.getImage() != null)
        {
            student.setImage(req_student.getImage());
        }

//        YE KARNA HE
//        if (req_student.getPassword() != null) {
//            student.setPassword(req_student.getPassword());
//        }
        return student_repo.save(student);
    }

    public List<Student> findbySection(Section section)
    {
        return student_repo.findBySection(section);
    }




    public List<Student> searchStudents(String query)
    {
        return student_repo.searchStudents(query);

    }

    public Section getSectionOfStudent(String id)
    {

        return student_repo.findSection(id);
    }





    //END
}
