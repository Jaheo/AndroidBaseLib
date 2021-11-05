package com.jaheo.baseandroid.log;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
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
            return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        }
    };

    private static boolean isInitial = false;
    private static String logFileDir;

    public static void initial(Context context) {
        logFileDir = context.getFilesDir() + File.separator + "LogMsg" + File.separator;
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
        String moreMessage = getMoreMessage(content);
        if (tag == null) {
            Log.e(TAG, moreMessage);
        } else {
            Log.e(tag, moreMessage);
        }
        if (save) {
            write2File(moreMessage);
        }
    }

    public static void i(String tag, String content, boolean save) {
        if (!isInitial) {
            throw new UnsupportedOperationException("LogTool.initial() first!");
        }
        String moreMessage = getMoreMessage(content);
        if (tag == null) {
            Log.i(TAG, moreMessage);
        } else {
            Log.i(tag, moreMessage);
        }
        if (save) {
            write2File(moreMessage);
        }
    }

    public static void w(String tag, String content, boolean save) {
        if (!isInitial) {
            throw new UnsupportedOperationException("LogTool.initial() first!");
        }
        String moreMessage = getMoreMessage(content);
        if (tag == null) {
            Log.w(TAG, moreMessage);
        } else {
            Log.w(tag, moreMessage);
        }
        if (save) {
            write2File(moreMessage);
        }
    }

    /**
     * 参考 orhanobut.logger ， 输出详细信息
     *
     * @param content 原始内容
     * @return 详细信息
     */
    private static String getMoreMessage(String content) {
        StringBuilder builder = new StringBuilder();
        builder.append("----------------START---------------");
        builder.append("\r\n");
        builder.append("Thread:");
        builder.append(Thread.currentThread().getName());
        // 当前线程的栈内元素
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(trace);
        int methodCount = 2;
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }
        for (int i = methodCount; i > 0; --i) {
            int stackIndex = i + stackOffset;
            if (stackIndex < trace.length) {
                builder.append("\r\n");
                builder.append(trace[stackIndex].getClassName()).append(".")
                        .append(trace[stackIndex].getMethodName()).append(" ")
                        .append(" (").append(trace[stackIndex].getFileName()).append(":")
                        .append(trace[stackIndex].getLineNumber()).append(")");
            }
        }
        builder.append("\r\n");
        builder.append("message:").append(content);
        builder.append("\r\n");
        builder.append("----------------END---------------");
        return builder.toString();
    }


    private static int getStackOffset(@NonNull StackTraceElement[] trace) {
        // 方法 按照调用顺序入栈，理论上在栈内，位于本类的所有方法下，第一个方法就是调用LogTool的方法
        // 本类的调用比较简单 e->e->getMoreMessage->getStackOffset，所以遍历五次，可以找到调用者方法
        for (int i = 5; i < trace.length; ++i) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LogTool.class.getName())) {
                --i;
                return i;
            }
        }
        return -1;
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
