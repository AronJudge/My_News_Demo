package com.liuwei.news.headlinenews;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.liuwei.base.MVVM.model.BaseMvvmModel;
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

public class HeadlineNewsFragment extends Fragment {
    public HeadlineNewsFragmentAdapter adapter;
    FragmentHomeBinding viewDataBinding;
    // 注册
    private HeadlineNewsViewModel headlineNewsViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        adapter = new HeadlineNewsFragmentAdapter(getChildFragmentManager());
        viewDataBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewDataBinding.viewpager.setAdapter(adapter);
        viewDataBinding.tablayout.setupWithViewPager(viewDataBinding.viewpager);
        viewDataBinding.viewpager.setOffscreenPageLimit(1);
        // 注册
        headlineNewsViewModel = new HeadlineNewsViewModel();
        headlineNewsViewModel.dataList.observe(this, new Observer<List<NewsChannelsBean.ChannelList>>() {
            @Override
            public void onChanged(List<NewsChannelsBean.ChannelList> channelLists) {
                adapter.setChannels(channelLists);
            }
        });
        return viewDataBinding.getRoot();
    }

/*    @Override
    public void onLoadSuccess(BaseMvvmModel baseMvvmModel, List<NewsChannelsBean.ChannelList> channelLists, PageResult... results) {
        adapter.setChannels(channelLists);
    }

    @Override
    public void onLoadFailed(Throwable e, PageResult... results) {
        e.printStackTrace();
    }*/

}
