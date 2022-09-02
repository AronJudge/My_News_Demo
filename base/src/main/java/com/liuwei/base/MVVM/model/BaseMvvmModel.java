package com.liuwei.base.MVVM.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.liuwei.base.customview.MvvmDataObserver;
import com.liuwei.base.preference.BasicDataPreferenceUtil;
import com.liuwei.base.utils.GenericUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseMvvmModel<NETWORK_DATA, RESULT_DATA> implements MvvmDataObserver<NETWORK_DATA> {

    private CompositeDisposable compositeDisposable;
    private boolean mIsPage;
    private final int INIT_PAGE_NUMBER;
    private boolean mIsLoading;
    private String mApbPremedData;

    // 缓存
    // 栏目数据 新闻数据在变
    // 更新频率不一样 ， 栏目数据一周更新一次 就好了 新闻数据实时更新
    private String mCachePreferenceKey;
    // 网络上回来的数据


    protected BaseMvvmModel(boolean isPage, String CachePreferenceKey, String apkPreData, int... targetPageNumber) {
        this.mApbPremedData = apkPreData;
        this.mIsPage = isPage;
        // NEW_CHANNEL_KEY
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
            getCacheDataAndLoad();
        }
    }

    // 需不需要刷新
    protected boolean isNeedToUpdate(long cacheTimeSlot) {
        return true;
    }

    // 缓存读取
    public void getCacheDataAndLoad() {
        if (!mIsLoading) {
            mIsLoading = true;
            if (mCachePreferenceKey != null) {
                String saveDataString = BasicDataPreferenceUtil.getInstance().getString(mCachePreferenceKey);
                if (saveDataString != null) {
                    // 反序列化数据
                    try {
                        // 拿到泛型的Type
                        NETWORK_DATA saveData = new
                                Gson().fromJson(new JSONObject(saveDataString).getString("data"),(Class<NETWORK_DATA>) GenericUtils.getGenericType(this));
                                // 拿到泛型的Type
                        if (saveData != null) {
                            onSuccess(saveData, true);
                        }
                        long TimeSlot = Long.parseLong(new JSONObject(saveDataString).getString("updateTimeMills"));
                        if (isNeedToUpdate(TimeSlot)) {
                            load();
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 预制的数据
                if (mApbPremedData != null) {
                    NETWORK_DATA data = new Gson().fromJson(mApbPremedData, (Class<NETWORK_DATA>) GenericUtils.getGenericType(this));
                    if (data!= null) {
                        onSuccess(data, true);
                    }
                }
            }
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

    protected void notifyResultToListener(NETWORK_DATA network_data, RESULT_DATA data, boolean isCache) {
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
                if (mCachePreferenceKey != null && mPage == INIT_PAGE_NUMBER && !isCache) {
                    staveDataToReference(network_data);
                }
            } else {
                if (mCachePreferenceKey != null && !isCache) {
                    staveDataToReference(network_data);
                }
            }


            // update page number
            if (mIsPage && !isCache) {
                if (data != null && ((List)data).size() > 0) {
                    mPage++;
                }
            }
        }
        if (!isCache) {
            mIsLoading = false;
        }
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




    public void cancel() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void addDisposable(Disposable d) {
        if (d== null) {
            return;
        }
        if(compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }
}
