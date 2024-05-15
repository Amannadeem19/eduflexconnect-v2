package com.fyp.eduflexconnect.Util;


import com.fyp.eduflexconnect.Models.Student;

public class StudentUtil {
    public final static boolean isReqUser(Student reqStud, Student student){
        return reqStud.getId().equals(student.getId());
    }
}
