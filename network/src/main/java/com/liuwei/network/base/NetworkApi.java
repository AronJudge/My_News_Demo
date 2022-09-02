package com.liuwei.network.base;

import com.liuwei.network.commoninterceptor.CommonRequestInterceptor;
import com.liuwei.network.commoninterceptor.CommonResponseInterceptor;
import com.liuwei.network.environment.EnvironmentActivity;
import com.liuwei.network.environment.IEnvironment;
import com.liuwei.network.errorhandler.HttpErrorHandler;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
public abstract class NetworkApi implements IEnvironment {
    private static INetworkRequiredInfo iNetworkRequiredInfo;
    private static HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;
    private static boolean mIsFormal = true;

    public NetworkApi() {
        if (!mIsFormal) {
            mBaseUrl = getTest();
        }
        mBaseUrl = getFormal();
    }

    public static void init(INetworkRequiredInfo networkRequiredInfo) {
        iNetworkRequiredInfo = networkRequiredInfo;
        mIsFormal = EnvironmentActivity.isOfficialEnvironment(networkRequiredInfo.getApplicationContext());
    }

    protected Retrofit getRetrofit(Class service) {
        if (retrofitHashMap.get(mBaseUrl + service.getName()) != null) {
            return retrofitHashMap.get(mBaseUrl + service.getName());
        }
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        // baseUrl总是以/结束，@URL不要以/开头
        retrofitBuilder.baseUrl(mBaseUrl);
        // 使用OkHttp Client
        retrofitBuilder.client(getOkHttpClient());
        // 集成Gson转换器
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        // 集成RxJava处理
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = retrofitBuilder.build();
        retrofitHashMap.put(mBaseUrl + service.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            if (getInterceptor() != null) {
                okHttpClientBuilder.addInterceptor(getInterceptor());
            }
            int cacheSize = 100 * 1024 * 1024; // 10MB
            okHttpClientBuilder.cache(new Cache(iNetworkRequiredInfo.getApplicationContext().getCacheDir(), cacheSize));
            okHttpClientBuilder.addInterceptor(new CommonRequestInterceptor(iNetworkRequiredInfo));
            okHttpClientBuilder.addInterceptor(new CommonResponseInterceptor());
            if (iNetworkRequiredInfo != null &&(iNetworkRequiredInfo.isDebug())) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
            }
            mOkHttpClient = okHttpClientBuilder.build();
        }
        return mOkHttpClient;
    }


    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = (Observable<T>)upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(getAppErrorHandler())
                        .onErrorResumeNext(new HttpErrorHandler<T>());
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    /**
     *     @Override
     *     protected Interceptor getInterceptor() {
     *         return new Interceptor() {
     *             @Override
     *             public Response intercept(Chain chain) throws IOException {
     *                 String timeStr = TecentUtil.getTimeStr();
     *                 Request.Builder builder = chain.request().newBuilder();
     *                 builder.addHeader("Source", "source");
     *                 builder.addHeader("Authorization", TecentUtil.getAuthorization(timeStr));
     *                 builder.addHeader("Date", timeStr);
     *                 return chain.proceed(builder.build());
     *             }
     *         };
     *     }
     *
     *     protected <T> Function<T, T> getAppErrorHandler() {
     *         return new Function<T, T>() {
     *             @Override
     *             public T apply(T response) throws Exception {
     *                 //response中code码不会0 出现错误
     *                 if (response instanceof TecentBaseResponse && ((TecentBaseResponse) response).showapiResCode != 0) {
     *                     ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
     *                     exception.code = ((TecentBaseResponse) response).showapiResCode;
     *                     exception.message = ((TecentBaseResponse) response).showapiResError != null ? ((TecentBaseResponse) response).showapiResError : "";
     *                     throw exception;
     *                 }
     *                 return response;
     *             }
     *         };
     *     }
     */


    protected abstract Interceptor getInterceptor();

    protected abstract <T> Function<T, T> getAppErrorHandler();
}
