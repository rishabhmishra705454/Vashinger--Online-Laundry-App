package com.rishabh.washer.model;

public class UserDetailModel {

    private String phoneNumber  , email  , fullName , uid;

    public UserDetailModel(String phoneNumber, String email, String fullName, String uid) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.fullName = fullName;
        this.uid = uid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
