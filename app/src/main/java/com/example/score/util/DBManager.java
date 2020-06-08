package com.example.score.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

public class DBManager {
    private Context mContext;
    private SQLiteDatabase mDB;
    private String dbName = "score.db";
    private String dbPath = DBFileUtil.getDBpath() + File.separator+ dbName;
    private static DBManager instance = null;

    public DBManager(String dbName) {
        this.dbName = dbName;
    }

    public SQLiteDatabase getmDB() {
        return mDB;
    }

        public DBManager() {

    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    /**
     * 打开数据库
     */
    public void openDB() {
        if (isSDCard()) {
            System.out.println("数据库位置"+dbPath);
            if (mDB == null || !mDB.isOpen())
                mDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }
    private boolean isSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


}
