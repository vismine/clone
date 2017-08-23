package com.example.vismime.weather_pm_json;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button get , post;
    EditText show;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get = (Button) findViewById(R.id.get);
        post = (Button) findViewById(R.id.post);
        show = (EditText)findViewById(R.id.show);
        TextView TextArea = (TextView)findViewById(R.id.city);
        String textarea = TextArea.getText().toString();
        TextView TextPm = (TextView)findViewById(R.id.pm);
        String textpm = TextPm.getText().toString();
        TextView TextTime = (TextView)findViewById(R.id.time);
        String texttime = TextTime.getText().toString();
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            String value = appInfo.metaData.getString("97caca3b08291c7159d7a081f6273212");//API_KEY
            final String area = "city="+show.getText().toString()+"&key="+"97caca3b08291c7159d7a081f6273212";
            System.out.println(area);
            Log.d("Tag", " app key : " + value);  // Tag﹕ app key : AIzaSyBhBFOgVQclaa8p1JJeqaZHiCo2nfiyBBo

            //利用Handler更新UI
            final Handler h = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    if(msg.what==0x123){
                        System.out.println(msg);
                        show.setText(msg.obj.toString());
                        JSONObject dataJson = new JSONObject(msg);
                    }
                }
            };

            get.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    new Thread(new AccessNetwork("GET", "http://192.168.1.88:8080/abc/a.jsp", null, h)).start();
                }
            });
            post.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //new Thread(new AccessNetwork("POST", "http://192.168.1.88:8080/abc/login.jsp", "name=crazyit.org&pass=leegang", h)).start();
                    new Thread(new AccessNetwork("POST", "http://web.juhe.cn:8080/environment/air/pm", area, h)).start();
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
class AccessNetwork implements Runnable{
    private String op ;
    private String url;
    private String params;
    private Handler h;

    public AccessNetwork(String op, String url, String params,Handler h) {
        super();
        this.op = op;
        this.url = url;
        this.params = params;
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
