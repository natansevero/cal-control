package com.example.natan.calcontrol.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "deficit")
public class DeficitEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idServico;
    private String meta;
    private double calculo;

    @Ignore
    public DeficitEntry(int idServico, String meta, double calculo) {
        this.idServico = idServico;
        this.meta = meta;
        this.calculo = calculo;
    }

    public DeficitEntry(int id, int idServico, String meta, double calculo) {
        this.id = id;
        this.idServico = idServico;
        this.meta = meta;
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

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public double getCalculo() {
        return calculo;
    }

    public void setCalculo(double calculo) {
        this.calculo = calculo;
    }
}
