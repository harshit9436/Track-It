package com.example.trackit.Util;

import java.util.Date;

public class Exercise_Set {
    private String reps;
    private String weight;
    private String userID;
    private String username;
    private String exercise;
    private Date current_date;
    private int set_number;

    public Exercise_Set(String reps, String weight, String userID, String username, String exercise, Date current_date, int set_number) {
        this.reps = reps;
        this.weight = weight;
        this.userID = userID;
        this.username = username;
        this.exercise = exercise;
        this.current_date = current_date;
        this.set_number = set_number;
    }

    public Exercise_Set() {
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Date getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(Date current_date) {
        this.current_date = current_date;
    }

    public int getSet_number() {
        return set_number;
    }

    public void setSet_number(int set_number) {
        this.set_number = set_number;
    }
}