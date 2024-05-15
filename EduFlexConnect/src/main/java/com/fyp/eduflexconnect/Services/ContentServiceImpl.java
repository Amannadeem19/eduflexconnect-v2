package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Exceptions.ContentException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.ContentRepository;
import com.fyp.eduflexconnect.Repositories.EnrollmentRepository;
import com.fyp.eduflexconnect.Repositories.FilesRepository;
import com.fyp.eduflexconnect.Repositories.SemesterRepository;
import com.fyp.eduflexconnect.Util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private FilesEntityService filesEntityService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    private SemesterService semesterService;
    @Autowired
    private EnrollmentRepository enrollmentRepository;


    @Override
    public Content uploadContent(MultipartFile[] files, String title, Teacher teacher,
                                 Classroom classroom) throws IOException, ContentException {

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
            throw new ContentException("Teacher is not part of this course");
        }
            Content content = new Content();

            content.setTitle(title);
            content.setCreatedAt(LocalDateTime.now());
            content.setTeacher(teacher);
            content.setClassroom(classroom);
            for(MultipartFile file : files){
                FilesEntity fileEntity = new FilesEntity();
                fileEntity.setName(file.getOriginalFilename());
                fileEntity.setType(file.getContentType());
                fileEntity.setFileData(FileUtils.compressImage(file.getBytes()));
                fileEntity.setContent(content);
                content.getFilesEntityList().add(fileEntity);
            }

            return contentRepository.save(content);
    }

    @Override
    public List<Content> getAllClassroomContent(Classroom classroom,Teacher teacher) throws ContentException {
        Course course = courseService.GetCourse(classroom.getCourse().getCourse_id());
        boolean found = false;
        for(Teacher t : course.getTeacher()){
            if(t.equals(teacher)){
                found  = true;
                break;
            }
        }
        if(found == false){
            throw new ContentException("Teacher is not part of this course");
        }
        return contentRepository.findByClassroom(classroom);
    }
//    overloading
    public List<Content> getAllClassroomContent(Classroom classroom,Student student) throws ContentException {
        Course course = courseService.GetCourse(classroom.getCourse().getCourse_id());
        String semesterId = semesterRepository.currentSemesterId();
        Semester semester = semesterService.GetSemester(semesterId);
        Enrollment enrollment = enrollmentRepository.findByStudentAndSemesterAndCourse(student, semester, course);
        if (enrollment == null){
            throw new ContentException("You are not a part of this course.");
        }

        return contentRepository.findByClassroom(classroom);
    }

    @Override
    public Content getContentById(Long content_id) throws ContentException {
        Content content = contentRepository.findById(content_id)
                .orElseThrow(() ->  new ContentException("Content not found with this id" + content_id));
        return content;
    }
    @Override
    public void deleteContentById(Content content, Teacher teacher) throws ContentException, UserException {
        if (teacher == null){
            throw new UserException("Teacher not found");
        }
        if(!content.getTeacher().getUsername().equals(teacher.getUsername()) )
        {
            throw new ContentException(teacher.getPersonal_info().getFirst_name() +  " cannot delete this content");
        }
        contentRepository.deleteById(content.getId());
    }
    @Override
    public Content updateContent(MultipartFile[] files, String title, Content content, Teacher teacher)
            throws ContentException, UserException,IOException {
//            Content content = getContentById(content_id);
            if(content == null){
                throw new ContentException("Content not found");
            }
            if(!content.getTeacher().getUsername().equals(teacher.getUsername()))
            {
                throw new ContentException("You can not update this content");
            }
            if(!title.isEmpty()){
                content.setTitle(title);
            }
            content.setCreatedAt(LocalDateTime.now());
        if (files.length > 0) {

                List<FilesEntity> oldFiles = content.getFilesEntityList();
                if (oldFiles != null && !oldFiles.isEmpty()) {
                    filesRepository.deleteAll(oldFiles);
                    content.setFilesEntityList(new ArrayList<>());
                }

                List<FilesEntity> filesEntityList = new ArrayList<>();

                for (MultipartFile file : files) {
                    FilesEntity fileEntity = new FilesEntity();
                    fileEntity.setName(file.getOriginalFilename());
                    fileEntity.setType(file.getContentType());
                    fileEntity.setFileData(FileUtils.compressImage(file.getBytes()));
                    fileEntity.setContent(content);
                    filesEntityList.add(fileEntity);
                }
                content.getFilesEntityList().addAll(filesEntityList);
            }

        return contentRepository.save(content);
    }
}
