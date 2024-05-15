package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.Scholarship;
import com.fyp.eduflexconnect.Models.SuperAdmin;

import java.util.List;

public interface ScholarshipService {

    public Scholarship createScholarship(Scholarship scholarship, SuperAdmin admin) throws AnnouncementException;
    public Scholarship updateScholarship(Long id, Scholarship scholarship, SuperAdmin admin) throws AnnouncementException;
    public void deleteScholarship(Long id, SuperAdmin admin) throws AnnouncementException;
    public Scholarship getScholarshipById(Long id) throws AnnouncementException;
    public List<Scholarship> getAllScholarships();
    public List<Scholarship> SearchScholarshipByQuery(String query) throws AnnouncementException;
}
