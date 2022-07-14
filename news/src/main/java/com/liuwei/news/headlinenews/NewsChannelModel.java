package com.liuwei.news.headlinenews;

import android.util.Log;

import com.google.gson.Gson;
import com.liuwei.base.MVVM.model.BaseMvvmModel;
import com.liuwei.network.TecentNetworkApi;
import com.liuwei.network.observer.BaseObserver;
import com.liuwei.news.api.NewsApiInterface;
import com.liuwei.news.api.NewsChannelsBean;

import java.util.List;


// 进行网络请求
public class NewsChannelModel extends BaseMvvmModel<NewsChannelsBean, List<NewsChannelsBean.ChannelList>> {

    public NewsChannelModel(){
        super(false, "NEW_CHANNEL_KEY");
    }

/*    private IBaseModelListener<List<NewsChannelsBean.ChannelList>> mIBaseModelListener;

    public NewsChannelModel(IBaseModelListener iBaseModelListener) {
        this.mIBaseModelListener = iBaseModelListener;
    }*/
    @Override
    public void load() {
        TecentNetworkApi.getService(NewsApiInterface.class)
                .getNewsChannels()
                .compose(TecentNetworkApi.getInstance().applySchedulers(new BaseObserver<NewsChannelsBean>() {
                    @Override
                    public void onSuccess(NewsChannelsBean newsChannelsBean) {
                        Log.e("MainActivity", new Gson().toJson(newsChannelsBean));
                        notifyResultToListener(newsChannelsBean ,newsChannelsBean.showapiResBody.channelList);
                        // mReferenceIBaseModelListener.get().onLoadSuccess(newsChannelsBean.showapiResBody.channelList);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        loadFail(e);
                    }
                }));
    }

}
