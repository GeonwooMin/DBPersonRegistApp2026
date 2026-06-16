package com.example.dbpersonregistapp2026jangan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {     // extends 상속 받을 떄 사용
    public DBManager(Context context){  // 생성자 만들어주기  Context은 인자
        super(context,"JAMemberDB",null,1);  // 부모 부를 때 슈퍼 사용 "JAMemberDB"는 DB 이름 지정
    }//생성자

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table member("+
                "name text," +
                "major text," +
                "stdno text," +
                "grade text," +
                "phone text," +
                "area text," +
                "hobby text)");

    } //table 만들기

    @Override
    public void onUpgrade(SQLiteDatabase db,int i, int i1){


    }
}
