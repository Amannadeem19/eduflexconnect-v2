package com.fyp.eduflexconnect.Services;




import com.fyp.eduflexconnect.Config.TimetableConfig;
import com.fyp.eduflexconnect.Models.Timetable;
import com.fyp.eduflexconnect.Repositories.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class TimetableService
{

    @Autowired
    private TimetableRepository timetable2_repo ;

    public void deleteTimeTable()
    {
        List<Timetable> timetables = timetable2_repo.findAll();
        if(!timetables.isEmpty()){
            timetable2_repo.deleteAll();
        }

    }


    public void saveTeacher(MultipartFile file , String teacherName) throws IOException {
        try{
            List<Timetable> timetables = TimetableConfig.convertExcelToListTeacher(file.getInputStream(),teacherName);
            this.timetable2_repo.saveAll(timetables);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveStudent(MultipartFile file , String studentsection) throws IOException {
        try{
            List<Timetable> timetables = TimetableConfig.convertExcelToListStudent(file.getInputStream(),studentsection);
            this.timetable2_repo.saveAll(timetables);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public List<Timetable> Timetable(){

//        System.out.println(timetable2Repo.findAll());
        System.out.println("hello2");
        return this.timetable2_repo.findAll();
    }
}

