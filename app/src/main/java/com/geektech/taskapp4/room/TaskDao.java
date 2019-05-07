package com.geektech.taskapp4.room;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task ORDER BY time")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM task WHERE id = :id")
    Task getById(long id);

    @Insert
    void insert(Task employee);

    @Update
    void update(Task employee);

    @Delete
    void delete(Task employee);
}