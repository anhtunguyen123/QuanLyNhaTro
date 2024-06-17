package com.example.quanlynhatro;


import java.io.Serializable;

public class KhachThue implements Serializable {

    private int id;
    private String name;
    private String gender;
    private String phone;
    private String cmnd;
    private int roomId;

    public KhachThue(int id, String name, String gender, String phone, String cmnd, int roomId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.cmnd = cmnd;
        this.roomId = roomId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
