package com.dygames.dailyvocabulary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DYGames on 2017. 3. 4..
 */
public class AppData {
    private static AppData ourInstance = new AppData();

    public static AppData getInstance() {
        return ourInstance;
    }

    public Map<String, Integer> setTextMap = new HashMap<>();
    public myDBHelper myhelper;


    private AppData() {

    }
}
