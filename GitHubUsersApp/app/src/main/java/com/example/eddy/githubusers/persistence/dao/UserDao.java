package com.example.eddy.githubusers.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.eddy.githubusers.persistence.entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<UserEntity> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUsers(List<UserEntity> users);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addOneUser(UserEntity user);

}
