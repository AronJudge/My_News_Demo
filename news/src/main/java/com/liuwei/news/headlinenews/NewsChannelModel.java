package com.liuwei.news.headlinenews;


import com.liuwei.base.MVVM.model.BaseMvvmModel;
import com.liuwei.network.TecentNetworkApi;
import com.liuwei.network.observer.BaseObserver;
import com.liuwei.news.api.NewsApiInterface;
import com.liuwei.news.api.NewsChannelsBean;

import java.util.List;


// 进行网络请求
public class NewsChannelModel extends BaseMvvmModel<NewsChannelsBean, List<NewsChannelsBean.ChannelList>> {

    public static final String PREDEFINED_CHANNELS
            = "{\"showapi_res_body\":{\"channelList\":[{\"channelId\":\"5572a108b3cdc86cf39001cd\"," +
            "\"name\":\"国内焦点\"},{\"channelId\":\"5572a108b3cdc86cf39001ce\",\"name\":\"国际焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001cf\",\"name\":\"军事焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001d0\",\"name\":\"财经焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001d1\",\"name\":\"互联网焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001d2\",\"name\":\"房产焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001d3\",\"name\":\"汽车焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001d4\",\"name\":\"体育焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001d5\",\"name\":\"娱乐焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001d6\",\"name\":\"游戏焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001d7\",\"name\":\"教育焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001d8\",\"name\":\"女人焦点\"}," +
            "{\"channelId\":\"5572a108b3cdc86cf39001d9\",\"name\":\"科技焦点\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001da\",\"name\":\"社会焦点\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001db\",\"name\":\"国内最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001dc\",\"name\":\"台湾最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001dd\",\"name\":\"港澳最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001de\",\"name\":\"国际最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001df\",\"name\":\"军事最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001e0\",\"name\":\"财经最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001e1\",\"name\":\"理财最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001e2\",\"name\":\"宏观经济最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001e3\",\"name\":\"互联网最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001e4\",\"name\":\"房产最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001e5\",\"name\":\"汽车最新\"}," +
            "{\"channelId\":\"5572a109b3cdc86cf39001e6\",\"name\":\"体育最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001e7\",\"name\":\"国际足球最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001e8\",\"name\":\"国内足球最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001e9\",\"name\":\"CBA最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001ea\",\"name\":\"综合体育最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001eb\",\"name\":\"娱乐最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001ec\",\"name\":\"电影最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001ed\",\"name\":\"电视最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001ee\",\"name\":\"游戏最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001ef\",\"name\":\"教育最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001f0\",\"name\":\"女人最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001f1\",\"name\":\"美容护肤最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001f2\",\"name\":\"情感两性最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001f3\",\"name\":\"健康养生最新\"}," +
            "{\"channelId\":\"5572a10ab3cdc86cf39001f4\",\"name\":\"科技最新\"}," +
            "{\"channelId\":\"5572a10bb3cdc86cf39001f5\",\"name\":\"数码最新\"}," +
            "{\"channelId\":\"5572a10bb3cdc86cf39001f6\",\"name\":\"电脑最新\"}," +
            "{\"channelId\":\"5572a10bb3cdc86cf39001f7\",\"name\":\"科普最新\"}," +
            "{\"channelId\":\"5572a10bb3cdc86cf39001f8\",\"name\":\"社会最新\"}," +
            "{\"channelId\":\"57463656a44a13cf\",\"name\":\"旅游最新\"}," +
            "{\"channelId\":\"9a15d3d4b5cab17f34e6d7976b1b1f30\",\"name\":\"电商最新\"}," +
            "{\"channelId\":\"5b4fe11ec1239b0805568da6e90fd875\",\"name\":\"物流最新\"}," +
            "{\"channelId\":\"3f4794412eaa17ed\",\"name\":\"创业最新\"}]" +
            ",\"ret_code\":0,\"totalNum\":48},\"showapi_res_code\":0,\"showapi_res_error\":\"\"}";

    public NewsChannelModel(){
        super(false, "NEW_CHANNEL_KEY", PREDEFINED_CHANNELS);
    }

/*    private IBaseModelListener<List<NewsChannelsBean.ChannelList>> mIBaseModelListener;

    public NewsChannelModel(IBaseModelListener iBaseModelListener) {
        this.mIBaseModelListener = iBaseModelListener;
    }*/
    @Override
    public void load() {
        TecentNetworkApi.getService(NewsApiInterface.class)
                .getNewsChannels()
                .compose(TecentNetworkApi.getInstance()
                        .applySchedulers(new BaseObserver<NewsChannelsBean>(this, this)));

/*                    @Override
                    public void onSuccess(NewsChannelsBean newsChannelsBean) {
                        Log.e("MainActivity", new Gson().toJson(newsChannelsBean));
                        notifyResultToListener(newsChannelsBean ,newsChannelsBean.showapiResBody.channelList);
                        // mReferenceIBaseModelListener.get().onLoadSuccess(newsChannelsBean.showapiResBody.channelList);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        loadFail(e);
                    }
                }));*/

    }


    // 只有来自网络的数据需要再次 存储 从缓存来的不能再存储
    @Override
    public void onSuccess(NewsChannelsBean newsChannelsBean, boolean isFromCache) {
        notifyResultToListener(newsChannelsBean ,newsChannelsBean.showapiResBody.channelList, isFromCache);
    }

    @Override
    public void onFailure(Throwable e) {

    }
}
