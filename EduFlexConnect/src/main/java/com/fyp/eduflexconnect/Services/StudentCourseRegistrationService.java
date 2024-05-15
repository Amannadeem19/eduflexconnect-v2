package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.DTOs.CourseRegistrationDTO;
import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Service
@Transactional
@EnableTransactionManagement
public class StudentCourseRegistrationService
{
    @Autowired
    private StudentRepository student_repo;

    @Autowired
    private EnrollmentRepository enrollment_repo;
    @Autowired
    private SemesterRepository semester_repo;
    @Autowired
    private OfferedCourseService offered_course_service;
    @Autowired
    private ElectiveSectionService elective_section_service;
    @Autowired
    private CourseService course_service;
    @Autowired
    private ClassroomRepository class_repo;
    @Autowired
    private ElectiveSectionRepository elective_section_repo;
    @Autowired
    private ElectiveClassroomRepository elective_class_repo;

    public Enrollment findEnrollment(Student student, String semesterId, int courseId){
        Semester semester = semester_repo.findById(semesterId).orElseThrow(() -> new CustomException("no semester found with this id"));
        Course course = course_service.GetCourse(courseId);
        Enrollment enrollment = enrollment_repo.findByStudentAndSemesterAndCourse(student, semester,course);
        if (enrollment == null){
            return null;
        }
        return enrollment;

    }
    public String courseRegistrationPage(String semester_id,String student_id)
    {
        Student student = student_repo.findById(student_id).orElse(null);
        Semester existing_semester = semester_repo.findById(semester_id).orElse(null);
        if(existing_semester != null)
        {
            if(!existing_semester.isRegistrationOpen())
            {
                return "Registration period not active.you can see register courses on Transcript page.";
            }
            else
            {
                Section section = student_repo.findSection(student_id);
                List<OfferedCourse> offered_courses = offered_course_service.listOfferedCourses(section.getId(),existing_semester);

                if(!offered_courses.isEmpty())
                {
                    System.out.println("Student Course Registration Page. Available Courses:"+offered_courses.size()+"\n");
                    for(OfferedCourse course : offered_courses)
                    {
                        if(enrollment_repo.isRegister(course.getCourse().getCourse_code(),student_id,semester_id))
                        {
                            continue;
                        }

                        if(course.getCourse().getType().equals("Core"))
                        {
                            System.out.println("Course Name: "+course.getCourse().getCourse_name());
                            System.out.println("Course Code: "+course.getCourse().getCourse_code());
                            System.out.println("Credit Hours: "+course.getCourse().getCredit_hours());
                            System.out.println("Type: "+course.getCourse().getType());
                            System.out.println("....................................................");
                        }
                        else
                        {
                            List<ElectiveSection> elective_sections = elective_section_service.getElectiveSections(course.getCourse(),existing_semester);
                            System.out.println("Course Name: "+course.getCourse().getCourse_name());
                            System.out.println("Course Code: "+course.getCourse().getCourse_code());
                            System.out.println("Credit Hours: "+course.getCourse().getCredit_hours());
                            System.out.println("Type: "+course.getCourse().getType());
                            System.out.println("Section(s) available: "+elective_sections.size());
                            for(ElectiveSection electiveSection: elective_sections)
                            {
                                System.out.println("Section Name: "+electiveSection.getName());
                                System.out.println("Available Seats: "+electiveSection.getNumberOfSeats());
                            }
                            System.out.println("....................................................");


                        }
                    }
                }

                System.out.println("Registered Courses\n");
                List<Enrollment> enrollments = enrollment_repo.findByStudentAndSemester(student,existing_semester);
                for(Enrollment e: enrollments)
                {
                    System.out.println("Course Name: "+e.getCourse().getCourse_name());
                    System.out.println("Course Code: "+e.getCourse().getCourse_code());
                    System.out.println("Credit Hours: "+e.getCourse().getCredit_hours());
                    System.out.println("Type: "+e.getCourse().getType());
                    System.out.println("Section: "+e.getCourseSection());

                    System.out.println("....................................................");
                }
                return "Course Registration Page";
            }
        }
        return "invalid semester";
    }

    @Transactional
    public boolean isSeatAvailable(ElectiveSection electiveSection)
    {
        return electiveSection.getCurrentEnrollments()<=electiveSection.getNumberOfSeats();
    }
    @Transactional
    public void courseRegistration(List<CourseRegistrationDTO> courses, String student_id, String semester_id)
    {
        Student student = student_repo.findById(student_id).orElse(null);
        Semester semester = semester_repo.findById(semester_id).orElse(null);
        Section section = student_repo.findSection(student_id);

        for(CourseRegistrationDTO c: courses)
        {
            Course course = course_service.GetCourse(c.getCourseCode());
            if(course.getType().equals("Core"))
            {
                //Enrollment in Course
                Enrollment enrollment = new Enrollment();
                enrollment.setCourse(course);
                enrollment.setSemester(semester);
                enrollment.setStudent(student);
                enrollment.setCourseSection(section.getId());
                enrollment.setSemester_number(section.getSemester());
                enrollment_repo.save(enrollment);


                //Enrollment in classroom in that course
                Classroom classroom = class_repo.findByCourseAndSemesterAndSection(course,semester,section);

                List<Student> students = classroom.getStudents();
                students.add(student);
                classroom.setStudents(students);

                List<Classroom> classrooms = student.getClassrooms();
                classrooms.add(classroom);
                student.setClassrooms(classrooms);

                student_repo.save(student);
                class_repo.save(classroom);

            }
            else
            {
                ElectiveSection electiveSection = elective_section_repo.findByName(c.getSection_Name());
                if(!isSeatAvailable(electiveSection))
                {
                    System.out.println("Seats full in Section: "+c.getSection_Name()+" for Code: "+course.getCourse_name());
                    continue;
                }
                //Enrollment in Elective Course
                Enrollment enrollment = new Enrollment();
                enrollment.setCourse(course);
                enrollment.setSemester(semester);
                enrollment.setStudent(student);
                enrollment.setCourseSection(c.getSection_Name());
                enrollment.setSemester_number(section.getSemester());
                enrollment_repo.save(enrollment);



                ElectiveClassroom elective_classroom = elective_class_repo.findByCourseAndSemesterAndElectiveSection(course,semester,electiveSection);

                List<Student> students = elective_classroom.getStudents();
                students.add(student);
                elective_classroom.setStudents(students);

                List<ElectiveClassroom> electiveClassrooms = student.getElectiveClassrooms();
                electiveClassrooms.add(elective_classroom);
                student.setElectiveClassrooms(electiveClassrooms);

                electiveSection.setCurrentEnrollments(electiveSection.getCurrentEnrollments()+1);
                elective_section_repo.save(electiveSection);
                student_repo.save(student);
                elective_class_repo.save(elective_classroom);


            }
        }


    }
}
