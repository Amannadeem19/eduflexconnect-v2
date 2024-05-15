package com.fyp.eduflexconnect.Util;


import com.fyp.eduflexconnect.Models.Teacher;

public class TeacherUtil {

    public final static boolean isReqUser(Teacher reqTeacher, Teacher teacher){
        return reqTeacher.getUsername().equals(teacher.getUsername());
    }
}
