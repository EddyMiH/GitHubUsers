package com.example.eddy.githubusers.model;

import com.google.gson.annotations.SerializedName;

public class UserName {

    @SerializedName("name")
    private String name;
    @SerializedName("followers")
    private int followers;

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowers() {
        return followers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
