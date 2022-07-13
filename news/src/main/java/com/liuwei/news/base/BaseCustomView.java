package com.liuwei.news.base;

public interface BaseCustomView<D extends BaseCustomViewModel> {
    void setData(D data);
}
