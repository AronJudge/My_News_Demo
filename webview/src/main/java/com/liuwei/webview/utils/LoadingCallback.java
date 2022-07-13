package com.liuwei.webview.utils;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.liuwei.webview.R;

public class LoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.liuwei_webview_layout_loading;
    }

    @Override
    public boolean getSuccessVisible() {
        return super.getSuccessVisible();
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
