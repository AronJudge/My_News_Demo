package com.liuwei.news.newslist;

import android.util.Log;

import com.google.gson.Gson;
import com.liuwei.base.MVVM.model.IBaseModelListener;
import com.liuwei.base.MVVM.model.PageResult;
import com.liuwei.base.customview.BaseCustomViewModel;
import com.liuwei.common.views.picturetitleview.PictureTitleViewModel;
import com.liuwei.common.views.titleview.TitleViewModel;
import com.liuwei.network.TecentNetworkApi;
import com.liuwei.network.observer.BaseObserver;
import com.liuwei.news.api.NewsApiInterface;
import com.liuwei.news.api.NewsListBean;

import java.util.ArrayList;
import java.util.List;

public class NewsListModel {

    private IBaseModelListener<List<BaseCustomViewModel>> mIBaseModelListener;
    private String mChannelID;
    private String mChannelName;
    private int mPage = 1;

    public NewsListModel(IBaseModelListener iBaseModelListener, String channelID, String channelName) {
        this.mIBaseModelListener = iBaseModelListener;
        this.mChannelID = channelID;
        this.mChannelName = channelName;
    }

    public void refresh() {
        mPage = 1;
        loadNextPage();
    }

    protected void loadNextPage() {
        TecentNetworkApi.getService(NewsApiInterface.class)
                .getNewsList(mChannelID, mChannelName, String.valueOf(mPage))
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObserver<NewsListBean>() {
                    @Override
                    public void onSuccess(NewsListBean newsChannelsBean) {
                        List<BaseCustomViewModel> viewModels = new ArrayList<>();
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
                        // 通知 Listener
                        mIBaseModelListener.onLoadSuccess(viewModels,
                            new PageResult(mPage == 1, viewModels.isEmpty(),viewModels.size() >= 10));
                        mPage++;
                        // viewModels.addAll(newsChannelsBean.showapiResBody.pagebean.contentlist);
/*                        mAdapter.setData(viewModels);
                        mPage ++;
                        viewDataBinding.refreshLayout.finishRefresh();
                        viewDataBinding.refreshLayout.finishLoadMore();*/
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mIBaseModelListener.onLoadFailed(e);
                    }
                }));
    }

}
