package com.fyp.eduflexconnect.Repositories;


import com.fyp.eduflexconnect.Models.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
    public SuperAdmin findByUsername(String username);
}
