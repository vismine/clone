package com.example.vismime.weather_pm_json;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Describe:
 * Created by 35277 on 2017/8/24.
 */
public class AccessNetwork implements Runnable{
    private String op ;
    private String url;
    private String params;
    private Handler h;

    public AccessNetwork(String op, String url, String params,Handler h) {
        super();
        //请求方式
        this.op = op;
        //地址
        this.url = url;
        //请求参数
        this.params = params;
        //handler
        this.h = h;
    }

    @Override
    public void run() {
        Message m = new Message();
        m.what = 0x123;
        if(op.equals("GET")){
            Log.i("iiiiiii","发送GET请求");
            //m.obj = GetPostUtil.sendGet(url, params);
            Log.i("iiiiiii",">>>>>>>>>>>>"+m.obj);
        }
        if(op.equals("POST")){
            Log.i("iiiiiii","发送POST请求");
            m.obj = GetPostUtil.sendPost(url, params);
            Log.i("gggggggg",">>>>>>>>>>>>"+m.obj);
        }
        h.sendMessage(m);
    }
}
