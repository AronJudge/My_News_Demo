package com.liuwei.news.newslist;

import androidx.lifecycle.MutableLiveData;

import com.liuwei.base.MVVM.model.BaseMvvmModel;
import com.liuwei.base.MVVM.model.IBaseModelListener;
import com.liuwei.base.MVVM.model.PageResult;
import com.liuwei.base.MVVM.viewmodel.ViewStatus;
import com.liuwei.base.customview.BaseCustomViewModel;
import com.liuwei.news.api.NewsChannelsBean;

import java.util.List;

public class NewsListViewModel implements IBaseModelListener<List<BaseCustomViewModel>> {

    public MutableLiveData<List<BaseCustomViewModel>> dataList = new MutableLiveData<>();
    public MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();
    private NewsListModel mModel;

    public NewsListViewModel (String channelID, String channelName) {
        mModel = new NewsListModel(channelID, channelName);
        mModel.register(this);
        mModel.getCacheDataAndLoad();
    }

    public void refresh() {

    }

    public void loadNextPage() {

    }

    @Override
    public void onLoadSuccess(BaseMvvmModel baseMvvmModel, List<BaseCustomViewModel> baseCustomViewModels, PageResult... results) {
        if (baseMvvmModel instanceof NewsListModel) {
            if (results[0].isFirstPage) {
                dataList.getValue().clear();
            }
            if (results[0].isEmpty) {
                if (results[0].isFirstPage) {
                    viewStatus.postValue(ViewStatus.EMPTY);
                } else {
                    viewStatus.postValue(ViewStatus.NO_MORE_DATA);
                }
            } else {
                if (results[0].isFirstPage) {
                    dataList.postValue(baseCustomViewModels);
                } else {
                    dataList.getValue().addAll(baseCustomViewModels);
                    dataList.postValue(dataList.getValue());
                }
            }
        }


/*        if (results != null && results.length > 0 && results[0].isFirstPage) {
            viewModels.clear();
        }
        viewModels.addAll(baseCustomViewModels);
        mAdapter.setData(viewModels);
        viewDataBinding.refreshLayout.finishRefresh();
        viewDataBinding.refreshLayout.finishLoadMore();*/
    }

    @Override
    public void onLoadFailed(Throwable e, PageResult... results) {
        e.printStackTrace();
    }
    //监听数据的返回
}
