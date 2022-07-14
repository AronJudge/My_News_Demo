package com.liuwei.news.headlinenews;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.liuwei.base.MVVM.model.IBaseModelListener;
import com.liuwei.base.MVVM.model.PageResult;
import com.liuwei.network.TecentNetworkApi;
import com.liuwei.network.observer.BaseObserver;
import com.liuwei.news.R;
import com.liuwei.news.databinding.FragmentHomeBinding;
import com.liuwei.news.api.NewsApiInterface;
import com.liuwei.news.api.NewsChannelsBean;

import java.util.ArrayList;
import java.util.List;

public class HeadlineNewsFragment extends Fragment implements IBaseModelListener<List<NewsChannelsBean.ChannelList>> {
    public HeadlineNewsFragmentAdapter adapter;
    FragmentHomeBinding viewDataBinding;
    // 注册
    private NewsChannelModel newsChannelModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        adapter = new HeadlineNewsFragmentAdapter(getChildFragmentManager());
        viewDataBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewDataBinding.viewpager.setAdapter(adapter);
        viewDataBinding.tablayout.setupWithViewPager(viewDataBinding.viewpager);
        viewDataBinding.viewpager.setOffscreenPageLimit(1);
        // 注册
        newsChannelModel = new NewsChannelModel(this);
        // 加载数据
        newsChannelModel.load();
        return viewDataBinding.getRoot();
    }

    @Override
    public void onLoadSuccess(List<NewsChannelsBean.ChannelList> channelLists, PageResult... results) {
        adapter.setChannels(channelLists);
    }

    @Override
    public void onLoadFailed(Throwable e) {
        e.printStackTrace();
    }
}
