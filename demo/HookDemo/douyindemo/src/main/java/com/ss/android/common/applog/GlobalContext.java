package com.ss.android.common.applog;

import android.content.Context;

/**
 * @author Administrator
 * @date 2018/6/10
 */
public class GlobalContext {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }
}