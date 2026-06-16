package com.example.dbpersonregistapp2026;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
    public DBManager(Context context){
        super (context, "JAMemberDB", null, 1);
    } //생성자

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table member("+
                            "name text,"+
                            "major text,"+
                            "stdno text,"+
                            "grade text,"+
                            "phone text,"+
                            "area text,"+
                            "hobby text)");
    } //table 만들기

    @Override
    public void onUpgrade(SQLiteDatabase db,int i,int i1){

    }

}
