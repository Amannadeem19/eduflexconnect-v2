package com.fyp.eduflexconnect.Services;

import com.fyp.eduflexconnect.DTOs.SubmissionDto;
import com.fyp.eduflexconnect.DtoMapper.SubmissionDtoMapper;
import com.fyp.eduflexconnect.Exceptions.CustomException;
import com.fyp.eduflexconnect.Models.*;
import com.fyp.eduflexconnect.Repositories.*;
import com.fyp.eduflexconnect.Request.AssignmentRequest;
import com.fyp.eduflexconnect.Util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService{

    @Autowired
    private CourseService courseService;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private SemesterService semesterService;
    @Override
    public Assignment createAssignment(AssignmentRequest assignmentRequest,MultipartFile[]files,
                                       int courseId, Teacher teacher)
            throws CustomException, IOException {
        Course course = courseService.GetCourse(courseId);
        if(!course.getTeacher().contains(teacher)){
            throw new CustomException("You are not part of this course");
        }

        Assignment assignment = new Assignment();
        assignment.setCreatedBy(teacher);
        assignment.setTitle(assignmentRequest.getTitle());
        assignment.setDescription(assignmentRequest.getDescription());
        assignment.setCourse(course);
        assignment.setCreatedAt(LocalDateTime.now());
        assignment.setDeadline(assignmentRequest.getDeadline());
        for (MultipartFile file: files){
            FilesEntity filesEntity = new FilesEntity();
            filesEntity.setName(file.getOriginalFilename());
            filesEntity.setType(file.getContentType());
            filesEntity.setFileData(FileUtils.compressImage(file.getBytes()));
            filesEntity.setAssignment(assignment);
            assignment.getFilesEntityList().add(filesEntity);
        }



        return assignmentRepository.save(assignment);
    }

    @Override
    public Submission uploadAssignment(Long assignId,
                                       int courseId,
                                       Student student,
                                       MultipartFile[] files)
            throws CustomException, IOException{
        Course course = courseService.GetCourse(courseId);
        String currSemester = semesterService.currentSemester();
        Semester semester = semesterService.GetSemester(currSemester);
        Enrollment enrolled = enrollmentRepository.findByStudentAndSemesterAndCourse(student, semester,course);
        if(enrolled == null){
            throw new CustomException("you are not enrolled in this course");
        }
        Assignment assignment = assignmentRepository.findById(assignId).orElseThrow(() -> new CustomException("Assignment not found with this id"));
        Submission new_submission = null;
//        check if student already submitted his/her assignment
        for (Submission s : assignment.getSubmissions()){
            if(s.getStudent().equals(student)){
                throw new CustomException(student.getId() + " , you already submitted assignment");
            }
        }
        for(MultipartFile file : files){
         Submission submission = new Submission();
         submission.setSubmissionTime(LocalDateTime.now());
            submission.setName(file.getOriginalFilename());
            submission.setType(file.getContentType());
            submission.setDeadline(assignment.getDeadline());
            submission.setFileData(FileUtils.compressImage(file.getBytes()));
            submission.setStudent(student);
            submission.setAssignment(assignment);
            if(LocalDateTime.now().isAfter(assignment.getDeadline())){
                submission.setStatus("turned in late");
            }else if (LocalDateTime.now().isBefore(assignment.getDeadline()) ||
                    (LocalDateTime.now().isEqual(assignment.getDeadline()))){
                submission.setStatus("turned in time");
            }
            Submission submission1 = submissionRepository.save(submission);
            new_submission = submission1;
            assignment.getSubmissions().add(submission1);
//            assignment.getStudents().add(student);

        }
        Assignment assignment1 = assignmentRepository.save(assignment);
        return new_submission;
    }
    // find all submissions

    @Override
    public List<Submission> getAssignmentsubmissions(Teacher teacher, long assign_id)
            throws IOException, CustomException {

        Assignment assignment = assignmentRepository.findById(assign_id).orElseThrow(()-> new CustomException("assignemnt not found"));
        if(!assignment.getCreatedBy().equals(teacher)){
            throw new CustomException("Invalid teacher");
        }
        List<Submission> submissions = submissionRepository.findByAssignment(assignment);
        return  submissions;

    }

    @Override
    public FilesEntity findByAssignmentFileById(Long assignId) throws CustomException {
        Assignment assignment = assignmentRepository.findById(assignId).orElseThrow(() -> new CustomException("Assignment not found"));
        return filesRepository.findByAssignment(assignment);
    }
}
