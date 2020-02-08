package com.example.listatarea_03_02;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDatosHelper extends SQLiteOpenHelper {

    public final static String TABLA ="bdTarea1";
    public final static String CAMPO1="nombre";
    public final static String CAMPO2="lugar";
    public final static String CAMPO3="descripcion";
    public final static String CAMPO4="importancia";



    public BaseDatosHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLA+" (rowid integer primary key autoincrement, "+CAMPO1+" varchar(40) not null,"
                +CAMPO2+" varchar(40) not null,"
                +CAMPO3+" text not null,"
                +CAMPO4+" int not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
