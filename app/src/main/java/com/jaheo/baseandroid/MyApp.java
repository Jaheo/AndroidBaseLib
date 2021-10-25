package com.jaheo.baseandroid;

import android.app.Application;

import com.jaheo.baseandroid.exception.CrashHandler;
import com.jaheo.baseandroid.log.LogTool;

public class MyApp  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        LogTool.initial(this);
    }
}
