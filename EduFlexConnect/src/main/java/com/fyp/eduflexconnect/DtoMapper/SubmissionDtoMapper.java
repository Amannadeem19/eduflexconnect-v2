package com.fyp.eduflexconnect.DtoMapper;

import com.fyp.eduflexconnect.DTOs.StudentDto;
import com.fyp.eduflexconnect.DTOs.SubmissionDto;
import com.fyp.eduflexconnect.Models.Submission;
import com.fyp.eduflexconnect.Util.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class SubmissionDtoMapper {
    public static SubmissionDto toSubmitDto(Submission submission){
        SubmissionDto submissionDto = new SubmissionDto();
        submissionDto.setAssignId(submission.getAssignment().getId());
        submissionDto.setId(submission.getId());
        submissionDto.setFile_name(submission.getName());
        submissionDto.setFile_type(submission.getType());
        submissionDto.setFileData(FileUtils.decompressImage(submission.getFileData()));
        submissionDto.setSubmissionTime(submission.getSubmissionTime());
        submissionDto.setDeadline(submission.getDeadline());
        StudentDto studentDto = StudentDtoMapper.toStudentDto(submission.getStudent());
        submissionDto.setStudent(studentDto);
        submissionDto.setStatus(submission.getStatus());
        return submissionDto;

    }
    public static List<SubmissionDto> toSubmissionDtos(List<Submission> submissions){
        List<SubmissionDto> submissionDtos = new ArrayList<>();
        for (Submission submission : submissions){
            SubmissionDto submissionDto = new SubmissionDto();
            submissionDto.setAssignId(submission.getAssignment().getId());
            submissionDto.setId(submission.getId());
            submissionDto.setFile_name(submission.getName());
            submissionDto.setFile_type(submission.getType());
            submissionDto.setFileData(FileUtils.decompressImage(submission.getFileData()));
            submissionDto.setSubmissionTime(submission.getSubmissionTime());
            submissionDto.setDeadline(submission.getDeadline());
            StudentDto studentDto = StudentDtoMapper.toStudentDto(submission.getStudent());
            submissionDto.setStudent(studentDto);
            submissionDto.setStatus(submission.getStatus());
            submissionDtos.add(submissionDto);
        }
        return submissionDtos;

    }
}
