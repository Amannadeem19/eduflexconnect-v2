package com.fyp.CourseRegistration.SecurityConfig;

public enum ApplicationUserPermissions {
    STUDENT_READ("student : read"),
    STUDENT_WRITE("student: write"),
    TEACHER_READ("teacher : read"),
    TEACHER_WRITE("teacher : write"),
    ANNOUNCEMENT_READ("announcement : read"),
    ANNOUNCEMENT_WRITE("announcement : write");
    private final String permission;

    ApplicationUserPermissions(String permission){
        this.permission = permission;
    }
    public String getPermission(){
        return permission;
    }


}
