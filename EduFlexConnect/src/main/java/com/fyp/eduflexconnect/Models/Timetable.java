package com.fyp.eduflexconnect.Models;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Timetable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long U_id;

    private String time1;


    private String place;

    public void setPlace(String place)
    {
        this.place = place;
    }
    public String getTime1() {
        return time1;
    }

    public String getTime2() {
        return time2;
    }

    public String getTime3() {
        return time3;
    }

    public String getTime4() {
        return time4;
    }

    public String getTime5() {
        return time5;
    }

    public String getTime6() {
        return time6;
    }

    public String getTime7() {
        return time7;
    }

    public String getTime8() {
        return time8;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public void setTime4(String time4) {
        this.time4 = time4;
    }

    public void setTime5(String time5) {
        this.time5 = time5;
    }

    public void setTime6(String time6) {
        this.time6 = time6;
    }

    public void setTime7(String time7) {
        this.time7 = time7;
    }

    public void setTime8(String time8) {
        this.time8 = time8;
    }

    public Timetable(String place , String time1, String time2, String time3, String time4, String time5, String time6, String time7, String time8) {
        this.place = place;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
        this.time6 = time6;
        this.time7 = time7;
        this.time8 = time8;
    }

    private String time2;
    private String time3;




    public Timetable() {
    }
    private String time4;
    private String time5;
    private String time6;
    private String time7;
    private String time8;


}
