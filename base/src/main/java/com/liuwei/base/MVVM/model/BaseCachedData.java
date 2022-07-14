package com.liuwei.base.MVVM.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseCachedData<DATA> {
    // 不能混淆
    @SerializedName("updateTimeMills")
    @Expose
    public long updateTimeMills;
    @SerializedName("data")
    @Expose
    public DATA data;
}
