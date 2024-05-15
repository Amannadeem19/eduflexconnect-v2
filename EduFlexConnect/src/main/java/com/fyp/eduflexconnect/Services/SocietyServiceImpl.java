package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.Society;
import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Repositories.SocietyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SocietyServiceImpl implements SocietyService{
    @Autowired
    private SocietyRepository societyRepository;
    @Override
    public Society createSocietyPost(Society post, SuperAdmin admin) throws AnnouncementException {
        Society society = new Society();
        society.setSocietyName(post.getSocietyName());
        society.setTitle(post.getTitle());
        society.setContent(post.getContent());
        society.setImage(post.getImage());
        society.setVideo(post.getVideo());
        society.setType(post.getType());
        society.setAdmin(admin);
        society.setCreatedAt(LocalDateTime.now());
        return societyRepository.save(society);
    }

    @Override
    public Society updateSocietyPost(Long id, Society post, SuperAdmin admin) throws AnnouncementException {
        Society updateSociety = getSocietyPostById(id);
        if(updateSociety.getAdmin().getId() != admin.getId()){
            throw new AnnouncementException("You can not update this society post");
        }
        if(post.getSocietyName()!=null){
            updateSociety.setSocietyName(post.getSocietyName());
        }
        if (post.getTitle() != null){
            updateSociety.setTitle(post.getTitle());
        }
        if (post.getContent() != null){
            updateSociety.setContent(post.getContent());
        }
        if(post.getImage() != null){
            updateSociety.setImage(post.getImage());
        }
        if(post.getVideo() != null){
            updateSociety.setVideo(post.getVideo());
        }
        if (post.getType() != null){
            updateSociety.setType(post.getType());
        }
        updateSociety.setAdmin(admin);
        updateSociety.setCreatedAt(LocalDateTime.now());
        return societyRepository.save(updateSociety);
    }

    @Override
    public void deleteSocietyPost(Long id, SuperAdmin admin) throws AnnouncementException {
        Society society = getSocietyPostById(id);
        if(society.getAdmin().getId() != admin.getId()){
            throw new AnnouncementException("You can not delete this post");
        }
        societyRepository.deleteById(id);
    }

    @Override
    public Society getSocietyPostById(Long id) throws AnnouncementException {
        Society society = societyRepository.findById(id)
                .orElseThrow(()->  new AnnouncementException("Society post not found" + id));

        return society;
    }

    @Override
    public List<Society> getAllSocietyPosts() throws AnnouncementException {
       List<Society> societies = societyRepository.findAll();
        return societies;
    }

    @Override
    public List<Society> searchSocietyPostByQuery(String query) throws AnnouncementException {
        List<Society> societies = societyRepository.searchSocietyPosts(query);
        return societies;
    }
}
