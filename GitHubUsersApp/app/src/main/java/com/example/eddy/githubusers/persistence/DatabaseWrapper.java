package com.example.eddy.githubusers.persistence;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseWrapper {

    private static AppDatabase UsersDbInstance = null;

    public static void createUsers(Context context){
        if(UsersDbInstance == null)
            UsersDbInstance = Room.databaseBuilder(context, AppDatabase.class, "users").allowMainThreadQueries().build();
    }
    public static AppDatabase getAppDatabase(){
        if (UsersDbInstance == null){
            throw new IllegalStateException("Database is not created");
        }
        return UsersDbInstance;
    }

}
