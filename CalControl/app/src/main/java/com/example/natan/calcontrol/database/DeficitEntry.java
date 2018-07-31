package com.example.natan.calcontrol.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "deficit")
public class DeficitEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idServico;
    private double calculo;

    @Ignore
    public DeficitEntry(int idServico, double calculo) {
        this.idServico = idServico;
        this.calculo = calculo;
    }

    public DeficitEntry(int id, int idServico, double calculo) {
        this.id = id;
        this.idServico = idServico;
        this.calculo = calculo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public double getCalculo() {
        return calculo;
    }

    public void setCalculo(double calculo) {
        this.calculo = calculo;
    }
}
