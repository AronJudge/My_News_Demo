package com.liuwei.news.newslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.liuwei.base.MVVM.model.BaseMvvmModel;
import com.liuwei.base.MVVM.model.IBaseModelListener;
import com.liuwei.base.MVVM.model.PageResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.liuwei.news.R;
import com.liuwei.news.databinding.FragmentNewsBinding;
import com.liuwei.base.customview.BaseCustomViewModel;

import java.util.ArrayList;
import java.util.List;


public class NewsListFragment extends Fragment implements IBaseModelListener<List<BaseCustomViewModel>> {

    private NewsListRecyclerViewAdapter mAdapter;
    private FragmentNewsBinding viewDataBinding;
    private NewsListModel mNewsListModel;

    protected final static String BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id";
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name";

    public static NewsListFragment newInstance(String channelId, String channelName) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channelId);
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        mAdapter = new NewsListRecyclerViewAdapter();
        viewDataBinding.listview.setHasFixedSize(true);
        viewDataBinding.listview.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.listview.setAdapter(mAdapter);
        mNewsListModel = new NewsListModel(
                getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_ID),
                getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME));
        mNewsListModel.register(this);
        mNewsListModel.load();

        viewDataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mNewsListModel.refresh();
            }
        });

        viewDataBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mNewsListModel.loadNextPage();
            }
        });
        return viewDataBinding.getRoot();
    }
    private List<BaseCustomViewModel> viewModels = new ArrayList<>();

    @Override
    public void onLoadSuccess(BaseMvvmModel baseMvvmModel, List<BaseCustomViewModel> baseCustomViewModels, PageResult... results) {
        if (results != null && results.length > 0 && results[0].isFirstPage) {
            viewModels.clear();
        }
        viewModels.addAll(baseCustomViewModels);
        mAdapter.setData(viewModels);
        viewDataBinding.refreshLayout.finishRefresh();
        viewDataBinding.refreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadFailed(Throwable e, PageResult... results) {
        e.printStackTrace();
    }

/*    // 与数据相关的操纵都需要再model中做
    protected void load() {
        TecentNetworkApi.getService(NewsApiInterface.class)
                .getNewsList(getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_ID),
                        getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME), String.valueOf(mPage))
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObserver<NewsListBean>() {
                    @Override
                    public void onSuccess(NewsListBean newsChannelsBean) {
                        if(mPage == 0) {
                            viewModels.clear();
                        }
                        for(NewsListBean.Contentlist contentlist : newsChannelsBean.showapiResBody.pagebean.contentlist) {
                            if (contentlist.imageurls != null && contentlist.imageurls.size() > 0) {
                                PictureTitleViewModel pictureTitleViewModel = new PictureTitleViewModel();
                                pictureTitleViewModel.pictureUrl = contentlist.imageurls.get(0).url;
                                pictureTitleViewModel.jumpUrl = contentlist.link;
                                pictureTitleViewModel.title = contentlist.title;
                                viewModels.add(pictureTitleViewModel);
                            } else {
                                TitleViewModel titleViewModel = new TitleViewModel();
                                titleViewModel.jumpUrl = contentlist.link;
                                titleViewModel.title = contentlist.title;
                                viewModels.add(titleViewModel);
                            }
                        }
                        // viewModels.addAll(newsChannelsBean.showapiResBody.pagebean.contentlist);
                        mAdapter.setData(viewModels);
                        mPage ++;
                        viewDataBinding.refreshLayout.finishRefresh();
                        viewDataBinding.refreshLayout.finishLoadMore();
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }*/
}
