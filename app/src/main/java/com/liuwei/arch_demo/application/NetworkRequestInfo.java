package com.liuwei.arch_demo.application;


import android.app.Application;

import com.liuwei.arch_demo.BuildConfig;
import com.liuwei.network.base.INetworkRequiredInfo;


public class NetworkRequestInfo implements INetworkRequiredInfo {
    private Application mApplication;
    public NetworkRequestInfo(Application application){
        this.mApplication = application;
    }

    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public Application getApplicationContext() {
        return mApplication;
    }

}
