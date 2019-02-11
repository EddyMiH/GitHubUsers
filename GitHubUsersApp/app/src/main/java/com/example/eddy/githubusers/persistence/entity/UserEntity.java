package com.example.eddy.githubusers.persistence.entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey //(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "name")
    public String fullName;

    @ColumnInfo(name = "login")
    public String userName;

    @ColumnInfo(name = "avatar_url")
    public String imgUrl;

    @ColumnInfo(name = "followers")
    public Integer followersCount;

    @ColumnInfo(name = "repos_url")
    public String repos;

    @ColumnInfo(name = "html_url")
    public String link;


}
