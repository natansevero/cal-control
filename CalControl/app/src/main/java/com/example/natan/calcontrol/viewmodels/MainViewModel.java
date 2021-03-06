package com.example.natan.calcontrol.viewmodels;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.natan.calcontrol.database.AlimentoEntry;
import com.example.natan.calcontrol.database.AppDatabase;

import java.util.List;

public class MainViewModel extends ViewModel {

    private LiveData<List<AlimentoEntry>> alimentosByData;
    private LiveData<Double> calsDia;

    public MainViewModel(AppDatabase database, String currentData) {
        alimentosByData = database.alimentoDao().loadAllAlimentosByData(currentData);
        calsDia = database.alimentoDao().loadAlimentosCalByData(currentData);
    }

    public LiveData<List<AlimentoEntry>> getAlimentosByData() {
        return alimentosByData;
    }

    public LiveData<Double> getCalsDia() {
        return calsDia;
    }
}
