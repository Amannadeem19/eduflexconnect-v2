package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.EnrollmentRepository;
import com.fyp.eduflexconnect.Request.ClassroomRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService
{
    @Autowired
    private EnrollmentRepository enrollment_repo;
    @Autowired
    private SectionService section_service;
    @Autowired
    private OfferedCourseService offered_course_service;
    @Autowired
    private StudentService student_service;
    @Autowired
    private SemesterService semester_service;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private ElectiveClassroomService electiveClassroomService;
    @Autowired
    private ElectiveSectionService electiveSectionService;

    public void enrollStudentOfSemester1(String department, String semester_id)
    {
        Semester existing_semester = semester_service.semester_repo.findById(semester_id).orElse(null);
        if(existing_semester != null)
        {
            if(existing_semester.getStatus().equals("Passed"))
            {
                throw new CustomException("Can not perform this operation on already passed semester");
            }
        }
        List<Section> semester_one_section = section_service.section_repo.findBySemesterAndDepartment(1,department);

        List<OfferedCourse> offered_courses = offered_course_service.findBySemesterandDepartmentandSemesterId(department,1,existing_semester);

        for(Section section: semester_one_section)
        {
            List<Student> student_in_section = student_service.findbySection(section);

            for(Student student: student_in_section)
            {
                for(OfferedCourse course: offered_courses)
                {
                    enrollStudent(student,course.getCourse(),course.getSemester(),1);
                }

            }
        }
    }
    public void enrollStudent(Student student, Course course, Semester semester, int semester_number)
    {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setSemester(semester);
        enrollment.setSemester_number(semester_number);
        enrollment.setCourseSection(student.getSection().getId());
        enrollment_repo.save(enrollment);
    }

    public List<Enrollment> getSectionEnrollments(Course course, Semester semester, String section_id)
    {
        return enrollment_repo.findByCourseAndSemesterAndCourseSection(course,semester,section_id);
    }

    public List<Enrollment> getStudentCurrentEnrollments(Student student, Semester semester)
    {
        return enrollment_repo.findByStudentAndSemester(student,semester);
    }

    public List<Enrollment> getCurrentEnrollments(String student_id,String semester_id) throws UserException
    {
        Student student =student_service.findStudentByUsername(student_id);
        Semester semester = semesterService.GetSemester(semester_id);
        return getStudentCurrentEnrollments(student,semester);
    }

    public ClassroomRequest getCurrentClassrooms(List<Enrollment> enrollments)
    {
        ClassroomRequest classrooms = new ClassroomRequest();
        List<Classroom> coreClassrooms = new ArrayList<>();
        List<ElectiveClassroom> electiveClassrooms = new ArrayList<>();

        for(Enrollment e: enrollments)
        {
            if(e.getCourse().getType().equals("Core"))
            {

                coreClassrooms.add(
                        classroomService.findClassroom(
                                e.getCourse(),
                                e.getSemester(),
                                section_service.GetSection(e.getCourseSection())
                        ));
            }
            else
            {

                electiveClassrooms.add(
                        electiveClassroomService.findElectiveClassroom(
                                e.getCourse(),
                                e.getSemester(),
                                electiveSectionService.getElectiveSection(e.getCourseSection())

                        ));
            }
        }

        classrooms.setClassrooms(coreClassrooms);
        classrooms.setElectiveClassrooms(electiveClassrooms);
        return classrooms;

    }

    public boolean isRegister(String course_code,String student_id, String semester_id)
    {
        return enrollment_repo.isRegister(course_code,student_id,semester_id);

    }


}
