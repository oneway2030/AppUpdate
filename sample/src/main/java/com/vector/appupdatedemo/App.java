package com.vector.appupdatedemo;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by Vector
 * on 2017/7/17 0017.
 */

public class App extends Application {
    public static boolean isTipUpdate = false;  //是否提示版本更新
    public static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        OkHttpUtils.getInstance()
                .init(this)
                .debug(true, "okHttp")
                .timeout(20 * 1000);


        OkGo.getInstance().init(this);
    }
}
