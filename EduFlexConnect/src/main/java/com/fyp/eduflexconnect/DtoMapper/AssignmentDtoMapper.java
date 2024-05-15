package com.fyp.eduflexconnect.DtoMapper;

import com.fyp.eduflexconnect.DTOs.AssignmentDto;
import com.fyp.eduflexconnect.Models.Assignment;
import com.fyp.eduflexconnect.Models.FilesEntity;
import com.fyp.eduflexconnect.Util.FileUtils;

public class AssignmentDtoMapper {

    public static AssignmentDto toAssignmentDto(Assignment assignment){
        AssignmentDto assignmentDto = new AssignmentDto();

        assignmentDto.setId(assignment.getId());
        assignmentDto.setTitle(assignment.getTitle());
        assignmentDto.setDescription(assignment.getDescription());
        assignmentDto.setCreatedAt(assignment.getCreatedAt());
        assignmentDto.setDeadline(assignment.getDeadline());
        assignmentDto.setFiles(assignment.getFilesEntityList());

        return assignmentDto;

//        for(FilesEntity files : assignment.getFilesEntityList()){
//            assignmentDto.getFiles().add(FileUtils.decompressImage(files.getFileData()));
//        }
    }
}
