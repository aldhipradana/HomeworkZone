package com.example.homeworkzone;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "homework.db" ;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "create table user (id integer primary key autoincrement," +
                " nama text NOT NULL," +
                " username text NOT NULL," +
                " password text NOT NULL," +
                " timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);";

        Log.d("SqlLog", "OnCreate: " +sql);
        db.execSQL(sql );

        sql = "create table matakuliah (id integer primary key autoincrement," +
                                                " nama_matakuliah text NOT NULL," +
                                                " nama_dosen text null," +
                                                " semester text null," +
                                                " sks integer null," +
                                                " timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);";

        Log.d("SqlLog", "OnCreate: " +sql);
        db.execSQL(sql );

        sql = "create table tugas (id integer primary key autoincrement," +
                " nama_matakuliah text NOT NULL," +
                " deskripsi text null," +
                " tenggat text null," +
                " timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);";

        Log.d("SqlLog", "OnCreate: " +sql);
        db.execSQL(sql );

        String insertSql = "INSERT INTO user (nama, username, password)" +
                                           "VALUES ('Admin', 'admin', 'admin');";
        db.execSQL(insertSql);

        insertSql = "INSERT INTO user (nama, username, password)" +
                "VALUES ('Aldhi', 'aldhi', 'aldhi');";
        db.execSQL(insertSql);

        insertSql = "INSERT INTO matakuliah (nama_matakuliah, nama_dosen, semester, sks)" +
                                          "VALUES ('Mobile Programming', 'I Made Dwi Putra Asana', 'Semester 4', 4);";
        db.execSQL(insertSql);

        insertSql = "INSERT INTO tugas (nama_matakuliah, deskripsi, tenggat)" +
                                     "VALUES ('Mobile Programming', 'Ujian Akhir Semester', ' ');";
        db.execSQL(insertSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean loginsession( String username, String password){

        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username=? and password=?", new String[]{username, password});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

}
