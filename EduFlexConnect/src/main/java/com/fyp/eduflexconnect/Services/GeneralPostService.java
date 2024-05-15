package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.GeneralPosts;
import com.fyp.eduflexconnect.Models.SuperAdmin;

import java.util.List;

public interface GeneralPostService {

    public GeneralPosts createPost(GeneralPosts post, SuperAdmin admin) throws AnnouncementException;
    public GeneralPosts updatePost(Long id, GeneralPosts post,  SuperAdmin admin) throws AnnouncementException;
    public void deletePost(Long id,  SuperAdmin admin) throws AnnouncementException;
    public GeneralPosts getPostById(Long id) throws AnnouncementException;
    public List<GeneralPosts> getAllPosts() throws AnnouncementException;

    public List<GeneralPosts> searchPostByQuery(String query) throws AnnouncementException;
}
