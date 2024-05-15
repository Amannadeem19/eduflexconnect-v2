package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.ContentException;
import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.Assignment;
import com.fyp.eduflexconnect.Models.Content;
import com.fyp.eduflexconnect.Models.FilesEntity;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Repositories.FilesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FilesEntityServiceImpl implements FilesEntityService {
    @Autowired
    private final FilesRepository filesRepository;

    public FilesEntityServiceImpl(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    @Override
    public List<FilesEntity> findFilesByContentId(Long id) throws ContentException {
        return filesRepository.findByContentId(id);
    }

    @Override
    public FilesEntity findFileById(Long id) throws ContentException {
        FilesEntity filesEntity = filesRepository.findById(id)
                .orElseThrow(()-> new ContentException("File not found"));
        return filesEntity;
    }

    @Override
    public void deleteFilesById(Content content, Teacher teacher) throws ContentException, UserException {
        if (teacher == null){
            throw new UserException("Teacher not found");
        }
        if(!content.getTeacher().getUsername().equals(teacher.getUsername()))
        {
            throw new ContentException(teacher.getUsername() +  " cannot delete this content");
        }
        filesRepository.deleteById(content.getId());

    }

    @Override
    public void deleteContentFileById(Long content_id) throws ContentException, UserException {
        filesRepository.deleteByContentId(content_id);
    }

    @Override
    public FilesEntity findFileByApplicantId(Long applicant_id) {
        return filesRepository.findByJobApplicantId(applicant_id);
    }

    @Override
    public FilesEntity findByAssignId(Assignment assignment) throws CustomException {
        return filesRepository.findByAssignment(assignment);
    }
}
