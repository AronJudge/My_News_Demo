package com.liuwei.arch_demo.application;

import android.app.Application;

import com.liuwei.base.preference.PreferencesUtil;
import com.liuwei.network.base.NetworkApi;


public class ArchDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkApi.init(new NetworkRequestInfo(this));
        PreferencesUtil.init(this);
    }
}
