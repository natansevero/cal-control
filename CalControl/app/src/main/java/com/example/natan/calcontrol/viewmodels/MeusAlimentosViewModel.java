package com.example.natan.calcontrol.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.natan.calcontrol.MeusAlimentosActivity;
import com.example.natan.calcontrol.database.AlimentoEntry;
import com.example.natan.calcontrol.database.AppDatabase;

import java.util.List;

public class MeusAlimentosViewModel extends AndroidViewModel {

    private LiveData<List<AlimentoEntry>> alimentos;

    public MeusAlimentosViewModel(Application application){
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        alimentos = appDatabase.alimentoDao().loadAllAlimentos();
    }

    public LiveData<List<AlimentoEntry>> getAlimentos() {
        return alimentos;
    }

}
