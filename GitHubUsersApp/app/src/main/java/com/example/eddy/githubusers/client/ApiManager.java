package com.example.eddy.githubusers.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    public static String BASE_URL = "https://api.github.com";
    private static GitHubService mApiClient;

    public static GitHubService getApiClient(){
        if(mApiClient == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApiClient = retrofit.create(GitHubService.class);
        }
        return mApiClient;
    }
}
