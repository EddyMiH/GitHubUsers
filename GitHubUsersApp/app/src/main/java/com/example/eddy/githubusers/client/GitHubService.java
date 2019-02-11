package com.example.eddy.githubusers.client;

import com.example.eddy.githubusers.model.Repository;
import com.example.eddy.githubusers.model.User;
import com.example.eddy.githubusers.model.UserNameAndFollowers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("/users")
    Call<List<User>> getUsers(@Query("since") int offset, @Query("per_page") int limit);

    @GET("/users/{username}/repos")
    Call<List<Repository>> getRepos2(@Path("username") String login);

    @GET("/users/{username}")
    Call<UserNameAndFollowers> getUserName(@Path("username") String login);

    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String login);
}
