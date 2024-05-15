package com.fyp.eduflexconnect.Controllers;

import com.fyp.eduflexconnect.Models.OfferedCourse;
import com.fyp.eduflexconnect.Models.Semester;
import com.fyp.eduflexconnect.Services.OfferedCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offeredcourses")
public class OfferedCourseController
{
   @Autowired
   private OfferedCourseService offeredCourseService;

   @PostMapping("/get/{sectionId}")
   public List<OfferedCourse> listOfferedCourses(@PathVariable String sectionId,@RequestBody Semester semester)
   {
       return offeredCourseService.listOfferedCourses(sectionId,semester);
   }

}
