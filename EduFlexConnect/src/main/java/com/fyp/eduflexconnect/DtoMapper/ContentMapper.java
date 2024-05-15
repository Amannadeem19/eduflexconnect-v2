package com.fyp.eduflexconnect.DtoMapper;


import com.fyp.eduflexconnect.DTOs.ContentDto;
import com.fyp.eduflexconnect.DTOs.FilesDto;
import com.fyp.eduflexconnect.DTOs.TeacherDto;
import com.fyp.eduflexconnect.Models.Content;
import com.fyp.eduflexconnect.Models.FilesEntity;
import com.fyp.eduflexconnect.Models.Teacher;
import com.fyp.eduflexconnect.Util.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class ContentMapper {


    public static ContentDto toContentDto (Content content, String checked){
                ContentDto contentDto = new ContentDto();
                contentDto.setId(content.getId());
                contentDto.setTitle(content.getTitle());
                TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(content.getTeacher());
                contentDto.setTeacher(teacherDto);
                contentDto.setCreatedAt(content.getCreatedAt());
                List<FilesDto> filesDtos = toFilesDtos(content.getFilesEntityList(),checked);
                contentDto.setFiles(filesDtos);
                contentDto.setTotalFiles(filesDtos.toArray().length);
                return contentDto;
    }
    public static List<ContentDto> toContentDtos(List<Content> contents, String checked){
        List<ContentDto>contentDtos = new ArrayList<>();
        for (Content content : contents){
            ContentDto contentDto = new ContentDto();
            contentDto.setId(content.getId());
            contentDto.setTitle(content.getTitle());
            TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(content.getTeacher());
            contentDto.setTeacher(teacherDto);
            contentDto.setCreatedAt(content.getCreatedAt());
            List<FilesDto> filesDtos = toFilesDtos(content.getFilesEntityList(),checked);
            contentDto.setFiles(filesDtos);
            contentDto.setTotalFiles(filesDtos.toArray().length);
            contentDtos.add(contentDto);
        }
        return contentDtos;
    }
//    public static ContentDto toContentDtoGet (Content content,
//                                              List <FilesEntity> filesEntityList, String checked){
//        ContentDto contentDto = new ContentDto();
//        contentDto.setId(content.getId());
//        contentDto.setTitle(content.getTitle());
//        TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(content.getTeacher());
//        contentDto.setTeacher(teacherDto);
//        contentDto.setCreatedAt(content.getCreatedAt());
//        List<FilesDto> filesDtos = toFilesDtos(filesEntityList, checked);
//        contentDto.setFiles(filesDtos);
//        contentDto.setTotalFiles(filesDtos.toArray().length);
//        return contentDto;
//    }

    public static List<FilesDto> toFilesDtos(List<FilesEntity> files, String checked){
        List<FilesDto> filesDtos = new ArrayList<>();
        for(FilesEntity fileEntity : files){
            FilesDto fileDto = new FilesDto();
            fileDto.setId(fileEntity.getId());
            fileDto.setName(fileEntity.getName());
            fileDto.setType(fileEntity.getType());
            if(checked == "post"){
                //already compress in contentService
                fileDto.setFileData(fileEntity.getFileData());
            }else{
                fileDto.setFileData(FileUtils.decompressImage(fileEntity.getFileData()));
            }
            filesDtos.add(fileDto);
        }
        return filesDtos;
    }
}