package com.dygames.dailyvocabulary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DYGames on 2017. 3. 4..
 */



public class myDBHelper extends SQLiteOpenHelper
{
    public myDBHelper(Context context)
    {
        super(context,"groupDB",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE groupTBL ( gWord CHAR(20) PRIMARY KEY, gLevel INTEGER);");
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS groupTBL");
        onCreate(db);
    }
}
