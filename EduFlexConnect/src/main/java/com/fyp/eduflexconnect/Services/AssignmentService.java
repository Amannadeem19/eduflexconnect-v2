package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Request.AssignmentRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface AssignmentService {
    public Assignment createAssignment(AssignmentRequest assignmentRequest, MultipartFile[]files,
                                       int course, Teacher teacher) throws CustomException, IOException;
    public Submission uploadAssignment(Long assignId, int courseId, Student student, MultipartFile[] files)
            throws CustomException, IOException;
    public List<Submission> getAssignmentsubmissions(Teacher teacher, long assign_id) throws IOException , CustomException;
    public FilesEntity findByAssignmentFileById(Long assignId) throws CustomException;
}
