package com.liuwei.network.observer;

import com.liuwei.base.MVVM.model.BaseMvvmModel;
import com.liuwei.base.customview.MvvmDataObserver;
import com.liuwei.network.errorhandler.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseObserver<T> implements Observer<T> {

    MvvmDataObserver<T> mMvvmDataObserver;
    BaseMvvmModel mBaseMvvmModel;
    public BaseObserver(BaseMvvmModel baseMvvmModel, MvvmDataObserver mvvmDataObserver) {
        this.mMvvmDataObserver = mvvmDataObserver;
        this.mBaseMvvmModel = baseMvvmModel;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mBaseMvvmModel != null) {
            mBaseMvvmModel.addDisposable(d);
        }
    }

    @Override
    public void onNext(T t) {
        mMvvmDataObserver.onSuccess(t,false);
    }

    @Override
    public void onError(Throwable e) {
        mMvvmDataObserver.onFailure(e);
    }

    @Override
    public void onComplete() {

    }

}
