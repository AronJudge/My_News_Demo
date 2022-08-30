package com.liuwei.arch_demo.application;

import android.app.Application;
import android.util.Log;

import com.liuwei.base.preference.PreferencesUtil;
import com.liuwei.network.base.NetworkApi;


public class ArchDemoApplication extends Application {

    private static String TAG = "LifeCycleApplication";

    @Override
    public void onCreate() {
        // 程序创建的时候执行
        Log.d(TAG, "onCreate");
        super.onCreate();
        NetworkApi.init(new NetworkRequestInfo(this));
        PreferencesUtil.init(this);
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.d(TAG, "onTerminate");
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        Log.d(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行（回收内存）
        // HOME键退出应用程序、长按MENU键，打开Recent TASK都会执行
        Log.d(TAG, "onTrimMemory");
        super.onTrimMemory(level);
    }
}
