package com.example.user.surpriseu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class changeDetail extends AppCompatActivity {
    //讀資料
    private SharedPreferences settings;
    private static final String data = "DATA";
    private  String userID;

    private TextView nowpeople,price,location,secondHand,process,messegeBoard,mention;
    private Button joinButton;
    private RequestQueue queue;
    private String changeID,typeID,result,nowPeople,maxPeople;
    private ImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_detail);

        //讀資料
        readData();

        nowpeople=(TextView)findViewById(R.id.nowPeople);
        price=(TextView)findViewById(R.id.price);
        location=(TextView)findViewById(R.id.location);
        secondHand=(TextView)findViewById(R.id.secondHand);
        //process=(TextView)findViewById(R.id.process);
        picture=(ImageView)findViewById(R.id.picture);
        messegeBoard=(TextView)findViewById(R.id.messageBoard);
        mention=(TextView)findViewById(R.id.mention);

        Bundle bd=getIntent().getExtras();
        changeID=bd.getString("changeID");

        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://140.121.197.130:8004/SurpriseU/changeDetailServlet?changeID="+changeID+"&state=getDetail",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("changeDetail response : " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            result=jsonObject.getString("result");
                            if(result.equals("success")){
                                price.setText(jsonObject.getString("lowPrice") + "~" + jsonObject.getString("highPrice"));
                                secondHand.setText(jsonObject.getString("secondHand"));
                                location.setText(jsonObject.getString("location"));
                                maxPeople=jsonObject.getString("maxPeople");
                               /* nowPeople=jsonObject.getString("nowPeople");
                                nowpeople.setText("目前人數 :  "+nowPeople+"/"+maxPeople);*/
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            // @Override
            public void onErrorResponse(VolleyError error) {    //錯誤訊息
            }
        });
        queue.add(stringRequest);


        // 主线程不能添加网络访问 方法一：
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        picture.setImageBitmap(ImgUtils.getBitmapFromURL("http://140.121.197.130:8004/SurpriseU/changeDetailServlet?state=getPhoto&changeID="+changeID));
    }

    //讀取資料
    public void readData(){
        settings = getSharedPreferences(data,0);
        userID =  settings.getString("userID", "");
    }
}
