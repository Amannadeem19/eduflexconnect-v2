package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.AnnouncementRepository;
import com.fyp.eduflexconnect.Repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService{
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public Announcement createAnnouncementForStudent(Announcement announcement,
                                                     Student student,
                                                     Classroom classroom)
            throws AnnouncementException {
        Course course = courseService.GetCourse(classroom.getCourse().getCourse_id());
        String semesterId = semesterService.currentSemester();
        Semester semester = semesterService.GetSemester(semesterId);
        Enrollment enrollment = enrollmentRepository.findByStudentAndSemesterAndCourse(student, semester, course);
        if(enrollment == null){
            throw new AnnouncementException("You can not add announcement , you are not a registered student");
        }
        Announcement new_announcement = new Announcement();
        new_announcement.setContent(announcement.getContent());
        new_announcement.setCreatedAt(LocalDateTime.now());
        new_announcement.setImage(announcement.getImage());
        new_announcement.setVideo(announcement.getVideo());
        new_announcement.setClassroom(classroom);
        new_announcement.setType("std");
        new_announcement.setStudent(student);
        return announcementRepository.save(new_announcement);
    }

    @Override
    public Announcement createAnnouncementForTeacher(Announcement announcement,
                                                     Teacher teacher, Classroom classroom)
            throws AnnouncementException {
        Course course = courseService.GetCourse(classroom.getCourse().getCourse_id());
//        System.out.println(course.getTeacher());
        boolean found = false;
        for(Teacher t : course.getTeacher()){
            if(t.equals(teacher)){
                found  = true;
                break;
            }
        }
        if(found == false){
            throw new AnnouncementException("Teacher is not part of this course");
        }


        Announcement new_announcement = new Announcement();
        new_announcement.setContent(announcement.getContent());
        new_announcement.setCreatedAt(LocalDateTime.now());
        new_announcement.setImage(announcement.getImage());
        new_announcement.setVideo(announcement.getVideo());
        new_announcement.setClassroom(classroom);
        new_announcement.setType("teacher");
        new_announcement.setTeacher(teacher);
        return announcementRepository.save(new_announcement);
    }

    @Override
    public List<Announcement> findAllAnnouncementsInDescendingOrder(Classroom classroom) {
        return announcementRepository.findByClassroom(classroom);
    }

    @Override
    public Announcement findById(Long announce_id) throws AnnouncementException {
        Announcement announcement = announcementRepository.findById(announce_id)
                .orElseThrow(()-> new AnnouncementException("Announcement not found with this Id"));
        return announcement;
    }

    @Override
    public void deleteStudentAnnouncementById(Long announce_id, String stud_id) throws
            AnnouncementException, UserException {
       Announcement announcement = findById(announce_id);
        if(announcement == null){
            throw new AnnouncementException("Announcement not found with this "+announce_id);
        }
        if(!stud_id.equals(announcement.getStudent().getId())){
            throw new UserException("You can not delete this announcement");
        }

        announcementRepository.deleteById(announce_id);
    }
    @Override
    public void deleteTeacherAnnouncementById(Long announce_id, String teacher_id) throws
            AnnouncementException, UserException {
        Announcement announcement = findById(announce_id);
        if(announcement == null){
            throw new AnnouncementException("Announcement not found with this "+announce_id);
        }
        if(!teacher_id.equals(announcement.getTeacher().getUsername())){
            throw new UserException("You can not delete this announcement");
        }

        announcementRepository.deleteById(announce_id);
    }

    @Override
    public Announcement updateStudentAnnouncementById(Announcement req_announcement, Long announce_id,
                                                      String stud_id)
            throws AnnouncementException, UserException {
        Announcement announcement = findById(announce_id);
        if(announcement == null){
            throw new AnnouncementException("Announcement not found with this id " +announce_id);
        }
        if(!stud_id.equals(announcement.getStudent().getId())){
            throw new UserException("Student can not update this announcement");
        }
        if(req_announcement.getContent() != null){
            announcement.setContent(req_announcement.getContent());
        }
        if(req_announcement.getImage() != null){
            announcement.setImage(req_announcement.getImage());
        }
        if(req_announcement.getVideo() != null){
            announcement.setVideo(req_announcement.getVideo());
        }
        announcement.setCreatedAt(LocalDateTime.now());
        return announcement;
    }

    @Override
    public Announcement updateTeacherAnnouncementById(Announcement req_announcement,
                                                      Long announce_id, String teacher_id)
            throws AnnouncementException, UserException {
        Announcement announcement = findById(announce_id);
        if (announcement == null){
            throw new AnnouncementException("Annoucement not found with this id " + announce_id);
        }
        if(!teacher_id.equals(announcement.getTeacher().getUsername())){
            throw new UserException("Teacher can not update this announcement");
        }
        if(req_announcement.getContent() != null){
            announcement.setContent(req_announcement.getContent());
        }
        if(req_announcement.getImage() != null){
            announcement.setImage(req_announcement.getImage());
        }
        if(req_announcement.getVideo() != null){
            announcement.setVideo(req_announcement.getVideo());
        }
        announcement.setCreatedAt(LocalDateTime.now());
        return announcement;
    }
}
