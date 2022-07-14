package com.liuwei.news.headlinenews;

import androidx.lifecycle.MutableLiveData;

import com.liuwei.base.MVVM.model.BaseMvvmModel;
import com.liuwei.base.MVVM.model.IBaseModelListener;
import com.liuwei.base.MVVM.model.PageResult;
import com.liuwei.news.api.NewsChannelsBean;

import java.util.ArrayList;
import java.util.List;

public class HeadlineNewsViewModel implements IBaseModelListener<List<NewsChannelsBean.ChannelList>> {

    protected NewsChannelModel model;
    public MutableLiveData<List<NewsChannelsBean.ChannelList>> dataList = new MutableLiveData<>();

    public HeadlineNewsViewModel() {
        dataList.setValue(new ArrayList<>());
        model = new NewsChannelModel();
        model.register(this);
        model.getCacheDataAndLoad();
    }
    @Override
    public void onLoadSuccess(BaseMvvmModel baseMvvmModel, List<NewsChannelsBean.ChannelList> channelLists, PageResult... results) {
        if (model instanceof NewsChannelModel) {
            dataList.postValue(channelLists);
        }
    }


    @Override
    public void onLoadFailed(Throwable e, PageResult... results) {
        e.printStackTrace();
    }
    }
