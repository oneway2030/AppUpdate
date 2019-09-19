package com.vector.appupdatedemo.sample;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vector.appupdatedemo.App;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

public class CustomUpdateParser extends UpdateCallback {
    /**
     * 解析json,自定义协议
     *
     * @param json 服务器返回的json
     * @return UpdateAppBean
     */
    @Override
    protected UpdateAppBean parseJson(String json) {
        UpdateAppBean updateAppBean = new UpdateAppBean();
        UpdateResult result = null;
        try {
            result = new Gson().fromJson(json, UpdateResult.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (result != null) {
            if (result.getCode() == 0) {
                UpdateResult.DataBean data = result.getData();
                if (data != null) {
                    boolean isUpdate = checkNeedUpdate(data.getBuildVersionNo());
                    if (isUpdate && App.isTipUpdate) {
                        Toast.makeText(App.getContext(), "您已是最新版本", Toast.LENGTH_SHORT);
                    }
                    updateAppBean.setUpdate(isUpdate ? "Yes" : "No")
                            //（必须）新版本号，
                            .setNewVersion(data.getBuildVersion())
                            //（必须）下载地址
                            .setApkFileUrl("https://www.pgyer.com/apiv2/app/install?appKey=8261722e8fe2f8e962020eeb6be07788&_api_key=f0cb8428ea078acc126aafe14d963e77")
//                                    .setUpdateDefDialogTitle(String.format("AppUpdate 是否升级到%s版本？", newVersion))
                            .setUpdateLog(data.getBuildUpdateDescription())
//                                    .setUpdateLog("今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说\r\n")
                            //大小，不设置不显示大小，可以不设置
                            .setTargetSize(data.getBuildFileSize() / 1048576 + "M"); //  size/1024/1024  =  M
                    //是否强制更新，可以不设置
//                            .setConstraint(true);
                    //设置md5，可以不设置
//                            .setNewMd5("");

                }
            }
        }
        return updateAppBean;
    }

    @Override
    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
        updateAppManager.showDialogFragment();
    }

    /**
     * 网络请求之前
     */
    @Override
    public void onBefore() {
    }

    /**
     * 网路请求之后
     */
    @Override
    public void onAfter() {
    }

    /**
     * 没有新版本
     */
    @Override
    public void noNewApp(String error) {
        if (App.isTipUpdate) {
            Toast.makeText(App.getContext(), "您已是最新版本", Toast.LENGTH_SHORT);
        }
    }

    public boolean checkNeedUpdate(int remoteVersionCode) {
        try {
            return remoteVersionCode > getVersionCode(App.getContext());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取App的VersionCode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int appVersion = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            appVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }
}
