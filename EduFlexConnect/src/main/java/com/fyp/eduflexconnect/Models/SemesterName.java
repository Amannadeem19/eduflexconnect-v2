package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class SemesterName
{
    @Id
    @GeneratedValue(generator = "semester_name_id",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "semester_name_id",sequenceName = "semester_name_id",allocationSize = 1)
    int id;
    @Column(unique = true)
    String name;

    public SemesterName() {
    }

    public SemesterName(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
