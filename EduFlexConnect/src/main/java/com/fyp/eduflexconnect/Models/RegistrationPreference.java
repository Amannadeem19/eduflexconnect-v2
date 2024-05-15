package com.fyp.eduflexconnect.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class RegistrationPreference
{
    @Id
    private int arn;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "arn")
    @JsonBackReference
    private Registrations registration;

    private String preference1;
    private String preference2;
    private String preference3;

    public RegistrationPreference(Registrations registration, String preference1, String preference2, String preference3) {
        this.registration = registration;
        this.arn = registration.getArn();
        this.preference1 = preference1;
        this.preference2 = preference2;
        this.preference3 = preference3;
    }

    public RegistrationPreference() {

    }

    public void setArn(int arn) {
        this.arn = arn;
    }

    public void setPreference1(String preference1) {
        this.preference1 = preference1;
    }

    public void setPreference2(String preference2) {
        this.preference2 = preference2;
    }

    public void setPreference3(String preference3) {
        this.preference3 = preference3;
    }

    public void setRegistration(Registrations registration) {
        this.registration = registration;
    }
}
