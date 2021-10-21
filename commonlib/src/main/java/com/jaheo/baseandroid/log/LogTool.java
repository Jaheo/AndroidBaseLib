package com.jaheo.baseandroid.log;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.annotation.experimental.Experimental;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

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
        Logger.addLogAdapter(new AndroidLogAdapter());
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

    public static void json( String content) {
        json( content, false);
    }

    public static void xml(String content) {
        xml(content, false);
    }


    public static void e(String tag, String content, boolean save) {
        if (!isInitial) {
            throw new UnsupportedOperationException("LogTool.initial() first!");
        }
        if (tag == null) {
            Logger.e(TAG, content);
        } else {
            Logger.e(tag, content);
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
            Logger.i(TAG, content);
        } else {
            Logger.i(tag, content);
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
            Logger.w(TAG, content);
        } else {
            Logger.w(tag, content);
        }
        if(save){
            write2File(content);
        }
    }

    public static void json(String json, boolean save) {
        if (!isInitial) {
            throw new UnsupportedOperationException("LogTool.initial() first!");
        }
        Logger.json(json);
        if(save){
            write2File(json);
        }
    }

    public static void xml(String xml, boolean save) {
        if (!isInitial) {
            throw new UnsupportedOperationException("LogTool.initial() first!");
        }
        Logger.xml(xml);
        if(save){
            write2File(xml);
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
