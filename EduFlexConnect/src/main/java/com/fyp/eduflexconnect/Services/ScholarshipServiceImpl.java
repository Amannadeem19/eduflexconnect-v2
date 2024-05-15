package com.fyp.eduflexconnect.Services;


import com.fyp.eduflexconnect.Exceptions.AnnouncementException;
import com.fyp.eduflexconnect.Models.Scholarship;
import com.fyp.eduflexconnect.Models.SuperAdmin;
import com.fyp.eduflexconnect.Repositories.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScholarshipServiceImpl implements ScholarshipService{

   @Autowired
   private ScholarshipRepository scholarshipRepository;

    @Override
    public Scholarship createScholarship(Scholarship scholarship, SuperAdmin admin) throws AnnouncementException {
       Scholarship scholarship1 = new Scholarship();
       scholarship1.setProvider(scholarship.getProvider());
       scholarship1.setDeadline(scholarship.getDeadline());
       scholarship1.setTitle(scholarship.getTitle());
        scholarship1.setDescription(scholarship.getDescription());
        scholarship1.setEligibilityCriteria(scholarship.getEligibilityCriteria());
        scholarship1.setAdmin(admin);
        scholarship1.setCreatedAt(LocalDateTime.now());
        return scholarshipRepository.save(scholarship1);
    }

    @Override
    public Scholarship updateScholarship(Long id, Scholarship scholarship, SuperAdmin admin) throws AnnouncementException {
       Scholarship updatedscholarship = getScholarshipById(id);
       if (updatedscholarship == null){
           throw new AnnouncementException("No Scholarship found");
       }
       if(updatedscholarship.getAdmin().getId() != admin.getId()){
           throw new AnnouncementException("You can not update this scholarship");
       }
       if(scholarship.getTitle() != null){
           updatedscholarship.setTitle(scholarship.getTitle());
       }
       if(scholarship.getDescription() != null){
           updatedscholarship.setDescription(scholarship.getDescription());
       }
       if (scholarship.getDeadline() != null){
           updatedscholarship.setDeadline(scholarship.getDeadline());
       }
       if (scholarship.getProvider() != null){
           updatedscholarship.setProvider(scholarship.getProvider());
       }
       if (scholarship.getEligibilityCriteria() != null){
           updatedscholarship.setEligibilityCriteria(scholarship.getEligibilityCriteria());
       }
       updatedscholarship.setCreatedAt(LocalDateTime.now());
        return scholarshipRepository.save(updatedscholarship);
    }

    @Override
    public void deleteScholarship(Long id, SuperAdmin admin) throws AnnouncementException {
            Scholarship scholarship = getScholarshipById(id);
           if(scholarship.getAdmin().getId() != admin.getId()){
               throw  new AnnouncementException("You cannot delete this scholarship");
           }
           scholarshipRepository.deleteById(id);
    }

    @Override
    public Scholarship getScholarshipById(Long id) throws AnnouncementException {
        Scholarship scholarship = scholarshipRepository.findById(id)
                .orElseThrow(()-> new AnnouncementException("No scholarship found with this id" + id));
        return scholarship;
    }

    @Override
    public List<Scholarship> getAllScholarships() {
        return scholarshipRepository.findAll();
    }

    @Override
    public List<Scholarship> SearchScholarshipByQuery(String query) throws AnnouncementException {
        List<Scholarship> scholarships = scholarshipRepository.searchScholarships(query);
        if (scholarships.isEmpty()){
            throw new AnnouncementException("No scholarship found");
        }
        return scholarships;
    }
}
