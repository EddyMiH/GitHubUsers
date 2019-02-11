package com.example.eddy.githubusers.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.eddy.githubusers.persistence.entity.RepositoryEntity;

import java.util.List;

@Dao
public interface RepoDao {

    @Query("SELECT * FROM repos")
    List<RepositoryEntity> getRepos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addRepos(List<RepositoryEntity> users);

    @Delete
    void delete(RepositoryEntity... repos);

    @Query("SELECT * FROM repos WHERE user_id=:id")
    List<RepositoryEntity> findReposForUser(String id);
}
