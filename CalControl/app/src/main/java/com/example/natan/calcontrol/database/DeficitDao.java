package com.example.natan.calcontrol.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface DeficitDao {

    @Insert
    void insertDificit(DeficitEntry deficitEntry);

}
