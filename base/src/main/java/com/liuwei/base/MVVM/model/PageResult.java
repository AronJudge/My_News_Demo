package com.liuwei.base.MVVM.model;

public class PageResult {

    public PageResult(boolean isFirstPage, boolean isEmpty, boolean hasNext) {
        this.isFirstPage = isFirstPage;
        this.isEmpty = isEmpty;
        this.hasNext = hasNext;
    }

    public boolean isFirstPage;
    public boolean isEmpty;
    public boolean hasNext;
}
