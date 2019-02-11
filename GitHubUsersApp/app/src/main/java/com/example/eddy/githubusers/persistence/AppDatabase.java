package com.example.eddy.githubusers.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.eddy.githubusers.persistence.dao.RepoDao;
import com.example.eddy.githubusers.persistence.dao.UserDao;
import com.example.eddy.githubusers.persistence.entity.RepositoryEntity;
import com.example.eddy.githubusers.persistence.entity.UserEntity;

@Database(entities = {UserEntity.class, RepositoryEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract RepoDao repoDao();
}
