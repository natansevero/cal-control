package com.example.natan.calcontrol.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.natan.calcontrol.database.AppDatabase;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final String currentData;

    public MainViewModelFactory(AppDatabase mDb, String currentData) {
        this.mDb = mDb;
        this.currentData = currentData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(mDb, currentData);
    }
}
