package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.Society;
import com.fyp.eduflexconnect.Models.SuperAdmin;

import java.util.List;

public interface SocietyService {
    public Society createSocietyPost(Society post, SuperAdmin admin) throws AnnouncementException;
    public Society updateSocietyPost(Long id, Society post,  SuperAdmin admin) throws AnnouncementException;
    public void deleteSocietyPost(Long id,  SuperAdmin admin) throws AnnouncementException;
    public Society getSocietyPostById(Long id) throws AnnouncementException;
    public List<Society> getAllSocietyPosts() throws AnnouncementException;

    public List<Society> searchSocietyPostByQuery(String query) throws AnnouncementException;
}
