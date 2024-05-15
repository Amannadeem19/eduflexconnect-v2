package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.ContentException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Classroom;
import com.fyp.eduflexconnect.Models.Content;
import com.fyp.eduflexconnect.Models.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContentService {

    public Content uploadContent(MultipartFile[] files, String title, Teacher teacher, Classroom classroom)
            throws IOException , ContentException;
    public Content getContentById(Long id) throws ContentException;

    public List<Content> getAllClassroomContent(Classroom classroom, Teacher teacher) throws ContentException;
    public void deleteContentById(Content content, Teacher teacher) throws ContentException, UserException;
    public Content updateContent(MultipartFile[] files,String title,Content content,Teacher teacher) throws ContentException,
            UserException,IOException;

//
}
