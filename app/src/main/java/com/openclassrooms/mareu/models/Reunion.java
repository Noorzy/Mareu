package com.openclassrooms.mareu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Reunion implements Parcelable {
    private int id;
    private String name;
    private String room;
    private String date;
    private String time;
    private String emails;


    public Reunion(int id, String name, String room, String date, String time, String emails) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.date = date;
        this.time = time;
        this.emails = emails;
    }

    protected Reunion(Parcel in) {
        name = in.readString();
        room = in.readString();
        date = in.readString();
        time = in.readString();
        emails = in.readString();
    }

    public static final Creator<Reunion> CREATOR = new Creator<Reunion>() {
        @Override
        public Reunion createFromParcel(Parcel in) {
            return new Reunion(in);
        }

        @Override
        public Reunion[] newArray(int size) {
            return new Reunion[size];
        }
    };

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(room);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(emails);

    }
}