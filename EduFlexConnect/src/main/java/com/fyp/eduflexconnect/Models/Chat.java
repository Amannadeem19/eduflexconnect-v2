package com.fyp.eduflexconnect.Models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Chat {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private  Long id;
        private String chat_name, chat_image;
        private boolean isGroup;
        @JoinColumn(name = "class_id")
        @OneToOne
        private Classroom classroom;

        @ManyToOne
        private Teacher createdBy;
        @ManyToMany
        private Set<Student> students = new HashSet<>();
        @OneToMany
        private List<Message> messages = new ArrayList<>();

}
