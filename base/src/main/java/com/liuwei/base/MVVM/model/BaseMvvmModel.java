package com.liuwei.base.MVVM.model;

import com.google.gson.Gson;
import com.liuwei.base.preference.BasicDataPreferenceUtil;

import java.lang.ref.WeakReference;
import java.util.List;

public abstract class BaseMvvmModel<NETWORK_DATA, RESULT_DATA> {

    private boolean mIsPage;
    private final int INIT_PAGE_NUMBER;
    private boolean mIsLoading;

    // 缓存
    // 栏目数据 新闻数据在变
    // 更新频率不一样 ， 栏目数据一周更新一次 就好了 新闻数据实时更新
    private String mCachePreferenceKey;
    // 网络上回来的数据


    protected BaseMvvmModel(boolean isPage, String CachePreferenceKey, int... targetPageNumber) {
        this.mIsPage = isPage;
        this.mCachePreferenceKey = CachePreferenceKey;
        if(isPage && targetPageNumber != null && targetPageNumber.length > 0) {
            INIT_PAGE_NUMBER = targetPageNumber[0];
        } else {
            INIT_PAGE_NUMBER = -1;
        }
    }

    // 如果没有再Loading状态 和 分页的状态下 才刷新
    public void refresh() {
        if (!mIsLoading) {
            if(mIsPage) {
                mPage = INIT_PAGE_NUMBER;
            }
            mIsLoading = true;
            load();
        }
    }

    protected abstract void load();

    public void loadNextPage(){
        if(!mIsLoading) {
            mIsLoading = true;
            load();
        }
    }

    protected WeakReference<IBaseModelListener> mReferenceIBaseModelListener;
    protected int mPage = 1;

    public void register(IBaseModelListener listener) {
        if(listener != null) {
            mReferenceIBaseModelListener = new WeakReference<>(listener);
        }
    }

    protected void notifyResultToListener(NETWORK_DATA network_data, RESULT_DATA data) {
        IBaseModelListener listener = mReferenceIBaseModelListener.get();
        if (listener != null) {
            if (mIsPage) {
                // notify  判断是不是第一页
                listener.onLoadSuccess(this, data,
                        new PageResult(mPage == INIT_PAGE_NUMBER,
                                data == null ? true:((List)data).isEmpty() ,
                                ((List)data).size() > 0));
            } else {
                listener.onLoadSuccess(this, data);
            }

            // 存网络的数据  缓存 序列化 反序列化  存取没问题 但是取有问题 反序列化
            if (mIsPage) {
                if (mCachePreferenceKey != null && mPage == INIT_PAGE_NUMBER) {
                    staveDataToReference(network_data);
                }
            } else {
                if (mCachePreferenceKey != null) {
                    staveDataToReference(network_data);
                }
            }


            // update page number
            if (mIsPage) {
                if (data != null && ((List)data).size() > 0) {
                    mPage++;
                }
            }
        }
        mIsLoading = false;
    }

    protected void  loadFail(final Throwable e) {
        IBaseModelListener listener = mReferenceIBaseModelListener.get();
        if (listener != null) {
            if (mIsPage) {
                listener.onLoadFailed(e, new PageResult(mPage == INIT_PAGE_NUMBER,
                        true,
                        false));
            } else {
                listener.onLoadFailed(e);
            }
        }
        mIsLoading = false;
    }

    // 缓存网络上的数据JSON
    protected void staveDataToReference(NETWORK_DATA data) {
        if (data != null) {
            BaseCachedData<NETWORK_DATA> mCacheData = new BaseCachedData<>();
            mCacheData.data = data;
            mCacheData.updateTimeMills = System.currentTimeMillis();
            BasicDataPreferenceUtil.getInstance().setString(mCachePreferenceKey, new Gson().toJson(mCacheData));
        }
    }

}
