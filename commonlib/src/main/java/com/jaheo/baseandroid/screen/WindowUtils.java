package com.jaheo.baseandroid.screen;

import android.content.Context;
import android.content.res.TypedArray;

public class WindowUtils {

    /**
     * 简介： 获取屏幕状态栏高度
     * 创建：
     *
     * @author majiahao
     * @Date 2021/11/10 15:44
     * 修改： ~
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 简介： 获取屏幕标题栏高度
     * 创建：
     *    @author majiahao
     *    @Date 2021/11/10 15:45
     * 修改： ~
     */
    public static int getActionBarHeight(Context context) {
        TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        float h = actionbarSizeTypedArray.getDimension(0, 0);
        actionbarSizeTypedArray.recycle();
        return (int) h;
    }

}
