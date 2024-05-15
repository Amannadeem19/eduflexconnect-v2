package com.fyp.CourseRegistration.Services;

import com.fyp.CourseRegistration.DTO.CourseRegistrationDTO;
import com.fyp.CourseRegistration.Models.*;
import com.fyp.CourseRegistration.Requests.OfferedCourseRequest;
import com.fyp.CourseRegistration.Services.Clients.EduFlexClient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@EnableTransactionManagement
public class StudentCourseRegistrationService
{
    @Autowired
    private EduFlexClient eduFlexClient;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ElectiveSectionService electiveSectionService;
    @Autowired
    private ElectiveClassroomService electiveClassroomService;


    public List<OfferedCourseRequest> courseRegistrationPage(String semester_id,String student_id)
    {
        List<OfferedCourseRequest> offeredCourseRequests = new ArrayList<>();
        Student student = studentService.findStudentByUsername(student_id);
        Semester existing_semester = eduFlexClient.getsemester(semester_id);
        if(existing_semester != null)
        {
            if(!existing_semester.isRegistrationOpen())
            {
                return null;
            }
            else
            {
                Section section = studentService.getSectionOfStudent(student_id);

                List<OfferedCourse> offered_courses = eduFlexClient.listOfferedCourses(section.getId(),existing_semester);

                if(!offered_courses.isEmpty())
                {
                    System.out.println("Student Course Registration Page. Available Courses:"+offered_courses.size()+"\n");
                    for(OfferedCourse course : offered_courses)
                    {
                        OfferedCourseRequest offeredCourse = new OfferedCourseRequest();
                        if(enrollmentService.isRegister(course.getCourse().getCourse_code(),student_id,semester_id))
                        {
                            continue;
                        }

                        if(course.getCourse().getType().equals("Core"))
                        {
                            offeredCourse.setCourse(course.getCourse());
                            offeredCourseRequests.add(offeredCourse);
                            System.out.println("Course Name: "+course.getCourse().getCourse_name());
                            System.out.println("Course Code: "+course.getCourse().getCourse_code());
                            System.out.println("Credit Hours: "+course.getCourse().getCredit_hours());
                            System.out.println("Type: "+course.getCourse().getType());
                            System.out.println("....................................................");
                        }
                        else
                        {
                            offeredCourse.setCourse(course.getCourse());
                            List<ElectiveSection> elective_sections = electiveSectionService.getElectiveSections(course.getCourse(),existing_semester);
                            System.out.println("Course Name: "+course.getCourse().getCourse_name());
                            System.out.println("Course Code: "+course.getCourse().getCourse_code());
                            System.out.println("Credit Hours: "+course.getCourse().getCredit_hours());
                            System.out.println("Type: "+course.getCourse().getType());
                            System.out.println("Section(s) available: "+elective_sections.size());
                            for(ElectiveSection electiveSection: elective_sections)
                            {
                                System.out.println("Section Name: "+electiveSection.getName());
                                System.out.println("Available Seats: "+(electiveSection.getNumberOfSeats()-electiveSection.getCurrentEnrollments()));

                            }
                            offeredCourse.setAvailableSections(elective_sections);
                            offeredCourseRequests.add(offeredCourse);
                            System.out.println("....................................................");


                        }
                    }
                }
            }
        }
        return offeredCourseRequests;
    }

    public List<Enrollment> getStudentRegisteredCourses(String semester_id,String student_id)
    {
        Student student = studentService.findStudentByUsername(student_id);
        Semester existing_semester = eduFlexClient.getsemester(semester_id);
        System.out.println("Registered Courses\n");
        List<Enrollment> enrollments = enrollmentService.getStudentCurrentEnrollments(student,existing_semester);
        for(Enrollment e: enrollments)
        {
            System.out.println("Course Name: "+e.getCourse().getCourse_name());
            System.out.println("Course Code: "+e.getCourse().getCourse_code());
            System.out.println("Credit Hours: "+e.getCourse().getCredit_hours());
            System.out.println("Type: "+e.getCourse().getType());
            System.out.println("Section: "+e.getCourseSection());

            System.out.println("....................................................");
        }
        return enrollments;
    }
    @Transactional
    public boolean isSeatAvailable(ElectiveSection electiveSection)
    {
        return electiveSection.getCurrentEnrollments()<=electiveSection.getNumberOfSeats();
    }
    @Transactional
    public void courseRegistration(List<CourseRegistrationDTO> courses, String student_id, String semester_id)
    {
        Student student = studentService.findStudentByUsername(student_id);
        Semester existing_semester = eduFlexClient.getsemester(semester_id);
        Section section = studentService.getSectionOfStudent(student_id);

        for(CourseRegistrationDTO c: courses)
        {
            Course course = eduFlexClient.GetCourse(c.getCourseCode());
            if(course.getType().equals("Core"))
            {
                //Enrollment in Course
                Enrollment enrollment = new Enrollment();
                enrollment.setCourse(course);
                enrollment.setSemester(existing_semester);
                enrollment.setStudent(student);
                enrollment.setCourseSection(section.getId());
                enrollment.setSemester_number(section.getSemester());
                enrollmentService.saveEnrollment(enrollment);


                //Enrollment in classroom in that course
                Classroom classroom = classroomService.findClassroom(course,existing_semester,section);

                List<Student> students = classroom.getStudents();
                students.add(student);
                classroom.setStudents(students);

                List<Classroom> classrooms = student.getClassrooms();
                classrooms.add(classroom);
                student.setClassrooms(classrooms);

                studentService.saveStudent(student);
                classroomService.saveClassroom(classroom);


            }
            else
            {
                ElectiveSection electiveSection = electiveSectionService.getElectiveSection(c.getSection_Name());
                if(!isSeatAvailable(electiveSection))
                {
                    System.out.println("Seats full in Section: "+c.getSection_Name()+" for Code: "+course.getCourse_name());
                    continue;
                }
                //Enrollment in Elective Course
                Enrollment enrollment = new Enrollment();
                enrollment.setCourse(course);
                enrollment.setSemester(existing_semester);
                enrollment.setStudent(student);
                enrollment.setCourseSection(c.getSection_Name());
                enrollment.setSemester_number(section.getSemester());
                enrollmentService.saveEnrollment(enrollment);



                ElectiveClassroom elective_classroom = electiveClassroomService.findElectiveClassroom(course,existing_semester,electiveSection);

                List<Student> students = elective_classroom.getStudents();
                students.add(student);
                elective_classroom.setStudents(students);

                List<ElectiveClassroom> electiveClassrooms = student.getElectiveClassrooms();
                electiveClassrooms.add(elective_classroom);
                student.setElectiveClassrooms(electiveClassrooms);

                electiveSection.setCurrentEnrollments(electiveSection.getCurrentEnrollments()+1);
                electiveSectionService.saveElectiveSection(electiveSection);
                studentService.saveStudent(student);
                electiveClassroomService.saveElectiveClassroom(elective_classroom);


            }
        }


    }
}
