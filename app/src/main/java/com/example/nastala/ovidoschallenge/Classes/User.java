package com.example.nastala.ovidoschallenge.Classes;

public class User {
    private int id;
    private String name, username, email, phone, website;
    private UserAddress address;
    private UserCompany company;

    public User(int id, String name, String username, String email, UserAddress address, String phone, String website, UserCompany company){
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public UserAddress getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public UserCompany getCompany() {
        return company;
    }
}
