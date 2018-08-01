package com.example.natan.calcontrol.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AlimentoDao {

    @Query("select * from alimento")
    LiveData<List<AlimentoEntry>> loadAllAlimentos();

    @Insert
    void insertAlimento(AlimentoEntry alimentoEntry);

    @Update
    void updateAlimento(AlimentoEntry alimentoEntry);

    @Delete
    void deleteAlimento(AlimentoEntry alimentoEntry);

    @Query("select * from alimento where id = :id")
    LiveData<AlimentoEntry> loadAlimentoById(int id);

    @Query("select * from alimento where data = :data")
    LiveData<List<AlimentoEntry>> loadAllAlimentosByData(String data);

    @Query("select sum(cal) from alimento where data = :data")
    LiveData<Double> loadAlimentosCalByData(String data);
}
