package com.fyp.eduflexconnect.Controllers;


import com.fyp.eduflexconnect.DTOs.ContentDto;
import com.fyp.eduflexconnect.DTOs.TeacherDto;
import com.fyp.eduflexconnect.DtoMapper.ContentMapper;
import com.fyp.eduflexconnect.DtoMapper.TeacherDtoMapper;
import com.fyp.eduflexconnect.Exceptions.ContentException;
import com.fyp.eduflexconnect.Exceptions.UserException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.ClassroomRepository;
import com.fyp.eduflexconnect.Repositories.FilesRepository;
import com.fyp.eduflexconnect.Response.ApiResponse;
import com.fyp.eduflexconnect.SecurityConfig.JwtTokenProvider;
import com.fyp.eduflexconnect.Services.*;
import com.fyp.eduflexconnect.Util.COMMONFuncs;
import com.fyp.eduflexconnect.Util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    
    
    @Autowired
    private ContentService contentService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private FilesEntityService filesEntityService;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassroomRepository classroomRepository;

    @PostMapping("/upload/{classroomId}")
    public ResponseEntity<ContentDto> uploadContent(@PathVariable("classroomId")String classroomId,
                                                    @RequestParam("title") String title,
                                                    @RequestParam("document")MultipartFile[] files,
                                                    @RequestHeader("Authorization")String jwt)
            throws IOException, ContentException, UserException {
        Content content1 = null;
        ContentDto contentDto = null;
        String token = COMMONFuncs.separateBearerFromToken(jwt);

        List<String> roles = jwtTokenProvider.findRole(token);
        Classroom classroom = classroomService.findClassroomById(classroomId);

        if(roles.contains("Role_TEACHER")){
                Teacher teacher = teacherService.findTeacherProfileByJwt(token);
                TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(teacher);
                content1 = contentService.uploadContent(files, title, teacher, classroom);
             contentDto = ContentMapper.toContentDto(content1, "post");
            contentDto.setUpdated(false);
            return new ResponseEntity<>(contentDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }
    @GetMapping("/contents/{classroomId}")
    public ResponseEntity<List<ContentDto>> getAllClassroomContentHandler(@PathVariable("classroomId")String classroomId,
                                                 @RequestHeader("Authorization")String jwt)
            throws IOException, ContentException, UserException{

        String token = COMMONFuncs.separateBearerFromToken(jwt);
        List<String> roles = jwtTokenProvider.findRole(token);

        Classroom classroom = classroomService.findClassroomById(classroomId);
        if(roles.contains("Role_TEACHER")){
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            List<Content> contents = contentService.getAllClassroomContent(classroom, teacher);
            List<ContentDto>contentDtos = ContentMapper.toContentDtos(contents, "get");
            return new ResponseEntity<>(contentDtos, HttpStatus.OK);
        }else if(roles.contains("Role_STUDENT")){
            Student student = studentService.findStudentProfileByJwt(token);
            ContentServiceImpl contentServiceImpl= (ContentServiceImpl) contentService;
            List<Content> contents = contentServiceImpl.getAllClassroomContent(classroom, student);
            List<ContentDto>contentDtos = ContentMapper.toContentDtos(contents, "get");
            return new ResponseEntity<>(contentDtos, HttpStatus.OK);
        }
        throw new ContentException("Your role is not correct");
    }
//    @GetMapping("/{contentId}")
//    public ResponseEntity<ContentDto> getContent(@PathVariable("contentId")Long content_id,
//                                                    @RequestHeader("Authorization")String jwt)
//            throws IOException, ContentException, UserException{
//        Content content1 = null;
//        ContentDto contentDto = null;
//        String token = COMMONFuncs.separateBearerFromToken(jwt);
////        boolean role = jwtTokenProvider.getAuthorities(token);
//
////        if(!role){
////            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
////            TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(teacher);
//            Content content = contentService.getContentById(content_id);
//            TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(content.getTeacher());
//            List<FilesEntity> files = filesEntityService.findFilesByContentId(content.getId());
//            contentDto = ContentMapper.toContentDtoGet(content,teacherDto,files, "get");
//
////        }
//        return new ResponseEntity<>(contentDto, HttpStatus.OK);
//    }
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("fileId") Long fileId ) throws ContentException{

        FilesEntity filesEntity= filesEntityService.findFileById(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(filesEntity.getType()));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("document.pdf").build());
        return new ResponseEntity<>(FileUtils.decompressImage(filesEntity.getFileData()), headers, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{content_id}")
    public ResponseEntity<ApiResponse> deleteContentByID(@PathVariable("content_id")Long content_id,
                                                         @RequestHeader("Authorization")String jwt) throws UserException, ContentException{
            String token = COMMONFuncs.separateBearerFromToken(jwt);
            boolean role = jwtTokenProvider.getAuthorities(token);
            ApiResponse apiResponse = new ApiResponse();
            if(!role){
                Teacher teacher = teacherService.findTeacherProfileByJwt(token);
                Content content =  contentService.getContentById(content_id);
                filesEntityService.deleteFilesById(content, teacher);
                contentService.deleteContentById(content, teacher);

                apiResponse.setMessage("Content id " + content.getId() + " is deleted successfully");
                apiResponse.setStatus(true);

            }else{

                apiResponse.setMessage("Cannot delete this content because you are student");
                apiResponse.setStatus(false);
            }
            return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
    @PutMapping("/update/{content_id}")
    public ResponseEntity<ContentDto> updateContent(@PathVariable("content_id")Long content_id,@RequestParam("title") String title,
                                                    @RequestParam("document")MultipartFile[] files,
                                                    @RequestHeader("Authorization")String jwt)
            throws UserException, ContentException, IOException{
        Content content1 = null;
        ContentDto contentDto = null;
        String token = COMMONFuncs.separateBearerFromToken(jwt);
        boolean role = jwtTokenProvider.getAuthorities(token);

        if(!role){
            Teacher teacher = teacherService.findTeacherProfileByJwt(token);
            TeacherDto teacherDto = TeacherDtoMapper.toTeacherDto(teacher);
            Content content =  contentService.getContentById(content_id);
            content1 = contentService.updateContent(files, title, content, teacher);
            contentDto = ContentMapper.toContentDto(content1, "post");
            contentDto.setUpdated(true);
            return new ResponseEntity<>(contentDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }

}
