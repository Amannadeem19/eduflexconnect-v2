package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Announcement;
import com.fyp.eduflexconnect.Models.Classroom;
import com.fyp.eduflexconnect.Models.Student;
import com.fyp.eduflexconnect.Models.Teacher;

import java.util.List;

public interface AnnouncementService {
    public Announcement createAnnouncementForStudent(Announcement announcement, Student student, Classroom classroom) throws AnnouncementException;
    public Announcement createAnnouncementForTeacher(Announcement announcement, Teacher teacher,
            Classroom classroom) throws AnnouncementException;

    public List<Announcement> findAllAnnouncementsInDescendingOrder(Classroom classroom);
    public Announcement findById(Long announce_id) throws AnnouncementException;
    public void deleteStudentAnnouncementById(Long announce_id, String stud_id) throws
            AnnouncementException, UserException;
    public void deleteTeacherAnnouncementById(Long announce_id, String teacher_id) throws
            AnnouncementException, UserException;

    public Announcement updateStudentAnnouncementById(Announcement req_announcement, Long announce_id, String stud_id) throws
            AnnouncementException, UserException;
    public Announcement updateTeacherAnnouncementById(Announcement req_announcement, Long announce_id, String teacher_id) throws
            AnnouncementException, UserException;


}
