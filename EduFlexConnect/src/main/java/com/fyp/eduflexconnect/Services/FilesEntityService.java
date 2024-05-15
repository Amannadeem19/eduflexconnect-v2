package com.fyp.eduflexconnect.Services;



import com.fyp.eduflexconnect.Exceptions.ContentException;
import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Assignment;
import com.fyp.eduflexconnect.Models.Content;
import com.fyp.eduflexconnect.Models.FilesEntity;
import com.fyp.eduflexconnect.Models.Teacher;

import java.util.List;

public interface FilesEntityService {

    public List<FilesEntity> findFilesByContentId(Long id) throws ContentException;
    public FilesEntity findFileById(Long id) throws ContentException;
    public void deleteFilesById(Content content, Teacher teacher) throws ContentException, UserException;
    public void deleteContentFileById(Long content_id) throws ContentException, UserException;
    // exception nh dali ha abh
    public FilesEntity findFileByApplicantId(Long applicant_id);

    public FilesEntity findByAssignId(Assignment assignment) throws CustomException;
}
