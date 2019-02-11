package com.example.eddy.githubusers.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.eddy.githubusers.model.Repository;
import com.example.eddy.githubusers.model.User;
import com.example.eddy.githubusers.persistence.entity.RepositoryEntity;
import com.example.eddy.githubusers.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public final class ParseEntityToModel {


    public static List<User> parseToUser(@NonNull List<UserEntity> userEntity){

        List<User> users = new ArrayList<>();
        for (int i = 0; i < userEntity.size(); ++i){
            User tempUsr = new User();
            tempUsr.setId(userEntity.get(i).id);
            tempUsr.setFullName(userEntity.get(i).fullName);
            tempUsr.setUserName(userEntity.get(i).userName);
            tempUsr.setFollowersCount(userEntity.get(i).followersCount);
            tempUsr.setImgUrl(userEntity.get(i).imgUrl);
            tempUsr.setLink(userEntity.get(i).link);
            tempUsr.setRepos(userEntity.get(i).repos);

            users.add(tempUsr);
        }
        return users;
    }

    public static List<UserEntity> parseToUserEntity(@NonNull List<User> users){

        List<UserEntity> userEntities = new ArrayList<>();
        for (int i = 0; i < users.size(); ++i){
            UserEntity tempUsrEntity = new UserEntity();
            tempUsrEntity.id = users.get(i).getId();
            tempUsrEntity.fullName = users.get(i).getFullName();
            tempUsrEntity.userName = users.get(i).getUserName();
            tempUsrEntity.followersCount = users.get(i).getFollowersCount();
            tempUsrEntity.imgUrl = users.get(i).getImgUrl();
            tempUsrEntity.link = users.get(i).getLink();
            tempUsrEntity.repos = users.get(i).getRepos();
            userEntities.add(tempUsrEntity);
        }

        return userEntities;
    }
    public static UserEntity parseToUserEntity(@NonNull User user){

            UserEntity tempUsrEntity = new UserEntity();
            tempUsrEntity.id = user.getId();
            tempUsrEntity.fullName = user.getFullName();
            tempUsrEntity.userName = user.getUserName();
            tempUsrEntity.followersCount = user.getFollowersCount();
            tempUsrEntity.imgUrl = user.getImgUrl();
            tempUsrEntity.link = user.getLink();
            tempUsrEntity.repos = user.getRepos();

        return tempUsrEntity;
    }
    public static List<Repository> parseToRepository(@NonNull List<RepositoryEntity> repositoryEntity){

        List<Repository> repos = new ArrayList<>();
        for(int i = 0; i < repositoryEntity.size(); ++i){
            Repository tempRepo = new Repository();
            tempRepo.setName(repositoryEntity.get(i).name);
            repos.add(tempRepo);
        }
        return repos;
    }

    public static List<RepositoryEntity> parseToRepositoryEntity(@NonNull List<Repository> repository, String userId){

        List<RepositoryEntity> repoEntity = new ArrayList<>();
        for (int i = 0; i < repository.size(); ++i){
            RepositoryEntity tempEntity = new RepositoryEntity();
            tempEntity.name = repository.get(i).getName();
            tempEntity.userId = userId;
            repoEntity.add(tempEntity);
        }

        return repoEntity;
    }
}
