package com.openclassrooms.mareu.models;

public class Reunion {
    private int id;
    private String name;
    private String room;
    private int hours;
    private int minutes;
    private String emails;


    public Reunion(int id, String name, String room, int hours, int minutes, String emails) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.hours = hours;
        this.minutes = minutes;
        this.emails = emails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}