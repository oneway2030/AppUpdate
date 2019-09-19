package com.vector.appupdatedemo.sample;

import android.app.Activity;
import android.os.Environment;

import com.vector.appupdatedemo.http.OkGoUpdateHttpUtil;
import com.vector.update_app.UpdateAppManager;

public class UpdateManger {

    public static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/io";

    public static void checkVersionUpdate(final Activity context) {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("_api_key", NetUrlConstant.API_KEY);
//        params.put("appKey", NetUrlConstant.APP_KEY);
        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(context)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl("https://www.pgyer.com/apiv2/app/view")
                .setPost(true)
//                .setTopPic(R.drawable.top_3) //顶部图片
                .setThemeColor(0xff39C1E9)
//                .setParams(params)
                .setTargetPath(path)
                .build()
                //检测是否有新版本
                .checkNewApp(new CustomUpdateParser());
    }

}
