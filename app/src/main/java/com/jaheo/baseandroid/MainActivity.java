package com.jaheo.baseandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.jaheo.baseandroid.exception.CrashHandler;
import com.jaheo.baseandroid.log.LogTool;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogTool.e("tag","111111111111111");
    }

    public void testClick(View view) {
        int va = 0;
        int vb = 10/va;
    }
}