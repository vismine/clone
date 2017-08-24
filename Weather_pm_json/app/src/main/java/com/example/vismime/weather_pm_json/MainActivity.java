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
        final TextView TextArea = (TextView)findViewById(R.id.city);
        final TextView TextPm = (TextView)findViewById(R.id.pm);
        final TextView TextTime = (TextView)findViewById(R.id.time);
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
                        show.setText(msg.obj.toString());
                        try {

                            JSONArray jsonObjs = new JSONObject(msg.obj.toString()).getJSONArray("result");
                            Log.i("取到JSONArray",">>>>>>>>>>>>");
                            for(int i = 0; i < jsonObjs.length() ; i++) {
                                JSONObject jsonObj = (JSONObject) jsonObjs.get(i);
                                String city = jsonObj.getString("city");
                                String pm = jsonObj.getString("PM2.5");
                                String time = jsonObj.getString("time");
                                TextArea.setText(city);
                                TextPm.setText(pm);
                                TextTime.setText(time);
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
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