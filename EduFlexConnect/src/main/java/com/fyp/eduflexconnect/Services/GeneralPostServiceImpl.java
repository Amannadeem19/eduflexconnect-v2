package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.GeneralPosts;
import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Repositories.GeneralPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GeneralPostServiceImpl implements GeneralPostService {
    @Autowired
    private GeneralPostRepository generalPostRepository;
    @Override
    public GeneralPosts createPost(GeneralPosts post, SuperAdmin admin) throws AnnouncementException {
        GeneralPosts newPost = new GeneralPosts();
        newPost.setTitle(post.getTitle());
        newPost.setType(post.getType());
        newPost.setImage(post.getImage());
        newPost.setContent(post.getContent());
        newPost.setVideo(post.getVideo());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setAdmin(admin);
        return generalPostRepository.save(newPost);
    }

    @Override
    public GeneralPosts updatePost(Long id, GeneralPosts post, SuperAdmin admin) throws AnnouncementException {
        GeneralPosts generalPosts = getPostById(id);
       if(generalPosts.getAdmin().getId() != admin.getId()){
           throw new AnnouncementException("You can notupdated this post");
       }

        if(post.getType() != null){
            generalPosts.setType(post.getType());
        }
        if(post.getImage() != null){
            generalPosts.setImage(post.getImage());
        }
        if(post.getVideo()!=null){
            generalPosts.setTitle(post.getTitle());
        }
        if(post.getContent() != null){
            generalPosts.setContent(post.getContent());
        }
        if(post.getVideo() != null){
            generalPosts.setVideo(post.getVideo());

        }
        generalPosts.setCreatedAt(LocalDateTime.now());
        return generalPostRepository.save(generalPosts);
    }

    @Override
    public void deletePost(Long id, SuperAdmin admin) throws AnnouncementException {
        GeneralPosts generalPosts = getPostById(id);
        if(generalPosts.getAdmin().getId() != admin.getId()){
            throw new AnnouncementException("You can not delete this post");

        }
        generalPostRepository.deleteById(id);

    }

    @Override
    public GeneralPosts getPostById(Long id) throws AnnouncementException {
        GeneralPosts generalPosts = generalPostRepository.findById(id)
                .orElseThrow(()->  new AnnouncementException("Post not found with this id"+ id));
        return generalPosts;
    }

    @Override
    public List<GeneralPosts> getAllPosts() throws AnnouncementException {
        List<GeneralPosts> posts = generalPostRepository.findAll();
        return posts;
    }

    @Override
    public List<GeneralPosts> searchPostByQuery(String query) throws AnnouncementException {
//        title or type
        List<GeneralPosts> posts = generalPostRepository.searchPosts(query);
        return posts;
    }
}
