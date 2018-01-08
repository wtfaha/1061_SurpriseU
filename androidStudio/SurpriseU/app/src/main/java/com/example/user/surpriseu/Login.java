package com.example.user.surpriseu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    //存資料
    private SharedPreferences settings;
    private static final String data = "DATA";

    //登入
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    private RequestQueue queue;

    private String userID;
    private String userName;
    private String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 主线程不能添加网络访问 方法一：
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        editTextUsername = (EditText) this.findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) this.findViewById(R.id.editTextPassword);
        buttonLogin = (Button) this.findViewById(R.id.buttonLogin);



        queue = Volley.newRequestQueue(this);       //HTTP Request處理工具，取得volley的request物件
        buttonLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(final View v) {
                String account = String.format(editTextUsername.getText().toString());
                String password = String.format(editTextPassword.getText().toString());

                System.out.println("account : " + account);
                System.out.println("password : " + password);
                System.out.println("url : http://140.121.197.130:8004/SurpriseU/LoginVerificationServlet?state=login&account="+account+"&password="+password+"");

                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://140.121.197.130:8004/SurpriseU/LoginVerificationServlet?state=login&account="+account+"&password="+password+"",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    System.out.println("response : " + response);

                                    JSONObject jsonObject = new JSONObject(response);
                                    result = jsonObject.getString("result");
                                    if(result.equals("登入成功")) {
                                        userID = jsonObject.getString("userID");
                                        userName = jsonObject.getString("userName");
                                        System.out.println("userID : " + userID);
                                        System.out.println("userName : " + userName);
                                    }
                                    System.out.println("result : " + result);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                display(v);
                            }
                        }, new Response.ErrorListener() {
                    // @Override
                    public void onErrorResponse(VolleyError error) {    //錯誤訊息
                    }
                });
                queue.add(stringRequest);   //把request丟進queue(佇列)
            }
        });
    }

    public void display(View v){
        System.out.println("display");

        //連到WebView
        Intent intent = new Intent();
        intent.setClass(Login.this, Homepage.class);    //到homepage頁面
        Bundle bundle = new Bundle(); //不同Activity之間的資料傳遞 (Bundle)
        if(result.equals("登入成功")) {
            saveData(); //存資料
            bundle.putString("userID", userID);
            bundle.putString("userName", userName);
            bundle.putString("result", result);

            intent.putExtras(bundle);
            startActivity(intent);  //開啟homepage活動
        }
        else{
            Snackbar.make(v, "登入失敗", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    //存資料
    public void saveData(){
        settings = getSharedPreferences(data,MODE_PRIVATE); //MODE_PRIVATE 只允許本應用程式使用
        settings.edit()
                .putString("userID", userID)
                .commit();
    }
}
