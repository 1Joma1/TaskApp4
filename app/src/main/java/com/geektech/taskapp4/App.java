package com.geektech.taskapp4;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.geektech.taskapp4.room.AppDatabase;

public class App extends Application {

    private AppDatabase appDatabase;
    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return appDatabase;
    }
}
