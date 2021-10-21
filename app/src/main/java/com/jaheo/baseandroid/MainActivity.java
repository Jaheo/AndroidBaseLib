package com.jaheo.baseandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jaheo.baseandroid.exception.CrashHandler;
import com.jaheo.baseandroid.log.LogTool;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CrashHandler.getInstance().init(this);
        LogTool.initial(this);

    }
}