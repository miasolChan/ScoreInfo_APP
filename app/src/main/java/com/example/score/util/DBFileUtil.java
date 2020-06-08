package com.example.score.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBFileUtil {
    private static String mWorkPath = null;
    private static String mRootPath = null;
    private static Boolean mGetSDpath = false;
    private final static String DB_PATH_NAME = "database"+File.separator;
    public static long copyTime = 0;

    private static Context mContext;

    public static String getRootPath() {
        if (!mGetSDpath || mRootPath == null) {
            mGetSDpath = true;
            boolean sdCardExist = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
            if (sdCardExist) {
                File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
                mRootPath = sdDir.toString();
            } else {
                mRootPath = mContext.getFilesDir().toString();
            }
        }
        if (!mRootPath.endsWith("/"))
            mRootPath += "/";
        return mRootPath;
    }

    /**
     * 设置工作目录
     *
     * @param context app context,不然会造成内存泄漏
     * @param path
     */
    public static void setWorkPath(Context context, String path) {
        mContext = context;
        if (null != getRootPath()) {
            mWorkPath = mRootPath + path;
        }
        if (!mWorkPath.endsWith("/")) {
            mWorkPath += "/";
        }

        File file = new File(mWorkPath);
        if (!file.exists()){
            boolean b = file.mkdirs();
        }
    }

    public static String getDBpath() {
        File file = new File(mWorkPath+ DB_PATH_NAME);
        if (!file.exists())
        {
            file.mkdirs();
            System.out.println("创建文件夹"+file.mkdirs());
        }
        return mWorkPath + DB_PATH_NAME;
    }

    public static void copyAccessDB(Context context) {
        try {
            String[] dbNames = context.getAssets().list("db");
            for (String dbName : dbNames) {
                long startTime = System.currentTimeMillis();
                String filePath = DBFileUtil.getDBpath() + dbName;
                //文件存在创建文件
                File dbFile = new File(filePath);
                if (!dbFile.exists()) {
                    FileOutputStream fos = null;
                    try {
                        dbFile.createNewFile();
                    }catch (Exception e){
                        System.out.println("创建文件出错");
                        e.printStackTrace();
                    }
                    InputStream is = context.getAssets().open("db/" + dbName);
                    fos = new FileOutputStream(dbFile);

                    byte[] buffer = new byte[2048];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                    long endTime = System.currentTimeMillis();
                    long useTime = endTime - startTime;
                    copyTime += useTime;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
