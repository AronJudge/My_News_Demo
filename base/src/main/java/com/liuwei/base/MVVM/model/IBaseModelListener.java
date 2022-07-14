package com.liuwei.base.MVVM.model;

public interface IBaseModelListener<DATA> {
    void onLoadSuccess(BaseMvvmModel baseMvvmModel, DATA data, PageResult... results);
    void onLoadFailed(Throwable e, PageResult... results);

}
