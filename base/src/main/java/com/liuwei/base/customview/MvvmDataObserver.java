package com.liuwei.base.customview;

public interface MvvmDataObserver<F> {
    void onSuccess(F t, boolean isFromCache);
    void onFailure(Throwable e);
}
