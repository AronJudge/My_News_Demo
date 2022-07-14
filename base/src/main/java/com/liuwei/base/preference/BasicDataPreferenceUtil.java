package com.liuwei.base.preference;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class BasicDataPreferenceUtil extends BasePreferences {
    private static final String BASIC_DATA_PREFERENCE_FILE_NAME = "network_api_module_basic_data_preference";
    private static com.liuwei.base.preference.BasicDataPreferenceUtil sInstance;

    public static com.liuwei.base.preference.BasicDataPreferenceUtil getInstance() {
        if (sInstance == null) {
            synchronized (com.liuwei.base.preference.BasicDataPreferenceUtil.class) {
                if (sInstance == null) {
                    sInstance = new com.liuwei.base.preference.BasicDataPreferenceUtil();
                }
            }
        }
        return sInstance;
    }

    @Override
    protected String getSPFileName() {
        return BASIC_DATA_PREFERENCE_FILE_NAME;
    }
}
