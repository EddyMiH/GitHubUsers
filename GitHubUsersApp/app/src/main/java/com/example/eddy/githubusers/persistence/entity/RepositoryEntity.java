package com.example.eddy.githubusers.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.eddy.githubusers.model.User;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "repos", foreignKeys = @ForeignKey(entity = UserEntity.class,
                                                        parentColumns = "id",
                                                        childColumns = "user_id",
                                                        onDelete = CASCADE))
public class RepositoryEntity {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "user_id")
    public String userId;
}
