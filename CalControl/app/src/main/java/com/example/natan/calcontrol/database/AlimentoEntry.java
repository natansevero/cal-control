package com.example.natan.calcontrol.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.io.Serializable;

@Entity(tableName = "alimento")
public class AlimentoEntry implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] foto;
    private String desc;
    private double cal;
    private String data;

    @Ignore
    public AlimentoEntry(byte[] foto, String desc, double cal, String data) {
        this.foto = foto;
        this.desc = desc;
        this.cal = cal;
        this.data = data;
    }

    public AlimentoEntry(int id, byte[] foto, String desc, double cal, String data) {
        this.id = id;
        this.foto = foto;
        this.desc = desc;
        this.cal = cal;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getCal() {
        return cal;
    }

    public void setCal(double cal) {
        this.cal = cal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
