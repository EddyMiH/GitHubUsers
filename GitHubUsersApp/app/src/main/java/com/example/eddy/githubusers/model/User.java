package com.example.eddy.githubusers.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String fullName;
    @SerializedName("login")
    private String userName;
    @SerializedName("avatar_url")
    private String imgUrl;
    @SerializedName("followers")
    private int followersCount;
    @SerializedName("repos_url")
    private String repos;
    @SerializedName("html_url")
    private String link;

    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public void setRepos(String repos) {
        this.repos = repos;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public String getRepos() {
        return repos;
    }

    public String getLink() {
        return link;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        @Override
        public User createFromParcel(Parcel source) {
            return User.createFromParcel(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(fullName);
        dest.writeString(userName);
        dest.writeString(imgUrl);
        dest.writeInt(followersCount);
        dest.writeString(repos);
        dest.writeString(link);

    }

    public static User createFromParcel(Parcel source){
        User user = new User();
        user.id = source.readString();
        user.fullName = source.readString();
        user.userName = source.readString();
        user.imgUrl = source.readString();
        user.followersCount = source.readInt();
        user.repos = source.readString();
        user.link = source.readString();

        return user;
    }
}
