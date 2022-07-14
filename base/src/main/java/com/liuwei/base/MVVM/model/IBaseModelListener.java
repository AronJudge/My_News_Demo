package com.liuwei.base.MVVM.model;

public interface IBaseModelListener<DATA> {
    void onLoadSuccess(DATA data, PageResult... results);
    void onLoadFailed(Throwable e);

}
