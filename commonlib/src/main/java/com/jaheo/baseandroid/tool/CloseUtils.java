package com.jaheo.baseandroid.tool;

import androidx.annotation.Nullable;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {

    /**
     * 关闭数据源
     * @param closeable 数据源
     */
    public static void closeSource(@Nullable Closeable closeable){
        if(closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
