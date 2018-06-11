package com.example.nastala.ovidoschallenge.Classes;

public class UserCompany {
    private String name, catchPhrase, bs;

    public UserCompany(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }

    public String getName() {
        return name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public String getBs() {
        return bs;
    }
}
