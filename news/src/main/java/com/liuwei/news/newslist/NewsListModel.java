package com.liuwei.news.newslist;

import android.util.Log;

import com.google.gson.Gson;
import com.liuwei.base.MVVM.model.BaseMvvmModel;
import com.liuwei.base.MVVM.model.IBaseModelListener;
import com.liuwei.base.MVVM.model.PageResult;
import com.liuwei.base.customview.BaseCustomViewModel;
import com.liuwei.common.views.picturetitleview.PictureTitleViewModel;
import com.liuwei.common.views.titleview.TitleViewModel;
import com.liuwei.network.TecentNetworkApi;
import com.liuwei.network.observer.BaseObserver;
import com.liuwei.news.api.NewsApiInterface;
import com.liuwei.news.api.NewsChannelsBean;
import com.liuwei.news.api.NewsListBean;

import java.util.ArrayList;
import java.util.List;

public class NewsListModel extends BaseMvvmModel<NewsListBean, List<BaseCustomViewModel>> {

    private String mChannelID;
    private String mChannelName;


    public NewsListModel(String channelID, String channelName) {
        super(true,channelID + channelName + "_Key",1);
        this.mChannelID = channelID;
        this.mChannelName = channelName;
    }


    @Override
    protected void load() {
        TecentNetworkApi.getService(NewsApiInterface.class)
                .getNewsList(mChannelID, mChannelName, String.valueOf(mPage))
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObserver<NewsListBean>() {
                    @Override
                    public void onSuccess(NewsListBean newsListsBean) {
                        List<BaseCustomViewModel> viewModels = new ArrayList<>();
                        for(NewsListBean.Contentlist contentlist : newsListsBean.showapiResBody.pagebean.contentlist) {
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
                        // 通知 Listener  父类做了
                        // mReferenceIBaseModelListener.get().onLoadSuccess(viewModels,
                            //new PageResult(mPage == 1, viewModels.isEmpty(),viewModels.size() >= 10));
                        notifyResultToListener(newsListsBean,viewModels);
                        // mPage++;
                        // viewModels.addAll(newsListsBean.showapiResBody.pagebean.contentlist);
/*                        mAdapter.setData(viewModels);
                        mPage ++;
                        viewDataBinding.refreshLayout.finishRefresh();
                        viewDataBinding.refreshLayout.finishLoadMore();*/
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        loadFail(e);
                    }
                }));
    }

}
