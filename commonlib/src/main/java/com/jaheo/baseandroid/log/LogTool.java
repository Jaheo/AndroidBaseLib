package com.jaheo.baseandroid.log;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.experimental.Experimental;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogTool {

    private static final String TAG = LogTool.class.getSimpleName();

    private static ThreadLocal<SimpleDateFormat> dataFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        }
    };

    private static boolean isInitial = false;
    private static String logFileDir;

    public static void initial(Context context) {
        logFileDir = context.getFilesDir() + File.separator + "LogMsg" + File.separator;
        // Logger.addLogAdapter(new AndroidLogAdapter());
        isInitial = true;
    }

    public static void e(String tag, String content) {
        e(tag, content, false);
    }

    public static void i(String tag, String content) {
        i(tag, content, false);
    }

    public static void w(String tag, String content) {
        i(tag, content, false);
    }

    public static void e(String tag, String content, boolean save) {
        if (!isInitial) {
            throw new UnsupportedOperationException("LogTool.initial() first!");
        }
        if (tag == null) {
            Log.e(TAG, content);
        } else {
            Log.e(tag, content);
        }
        if(save){
            write2File(content);
        }
    }

    public static void i(String tag, String content, boolean save) {
        if (!isInitial) {
            throw new UnsupportedOperationException("LogTool.initial() first!");
        }
        if (tag == null) {
            Log.i(TAG, content);
        } else {
            Log.i(tag, content);
        }
        if(save){
            write2File(content);
        }
    }

    public static void w(String tag, String content, boolean save) {
        if (!isInitial) {
            throw new UnsupportedOperationException("LogTool.initial() first!");
        }
        if (tag == null) {
            Log.w(TAG, content);
        } else {
            Log.w(tag, content);
        }
        if(save){
            write2File(content);
        }
    }


    private static void write2File(String str) {
        SimpleDateFormat dateFormat = dataFormat.get();
        if (dateFormat == null) {
            e(TAG, "时间解析异常-----");
            return;
        }
        FileWriter fileWriter = null;
        try {
            File logFile = new File(logFileDir, dateFormat.format(new Date()));
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            fileWriter = new FileWriter(logFile);
            fileWriter.write(str);
            fileWriter.flush();
        } catch (IOException e) {
            e(TAG, "IO Exception ----" + e.getMessage());
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }


    }
}
