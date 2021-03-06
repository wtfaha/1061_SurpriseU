package com.example.user.surpriseu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AttackActivity extends AppCompatActivity {

    //讀資料
    private SharedPreferences settings;
    private static final String data = "DATA";
    private  String userID;

    private EditText nameInput,lowPriceInput,highPriceInput,maxPeopleInput;
    private Spinner typeInput,locationInput;
    private CheckBox agree;
    private RadioGroup secondHandInput;
    private Button submit;
    private String result,changeID,typeID;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);

        //讀資料
        readData();

        nameInput=(EditText)findViewById(R.id.nameInput);
        lowPriceInput=(EditText)findViewById(R.id.lowPrice);
        highPriceInput=(EditText)findViewById(R.id.highPrice);
        typeInput=(Spinner)findViewById(R.id.typeInput);
        locationInput=(Spinner)findViewById(R.id.locationInput);
        secondHandInput=(RadioGroup)findViewById(R.id.secondHandInput);
        maxPeopleInput=(EditText)findViewById(R.id.maxPeopleInput);
        agree=(CheckBox)findViewById(R.id.agree);
        submit=(Button)findViewById(R.id.submit);


        queue = Volley.newRequestQueue(this);       //HTTP Request處理工具，取得volley的request物件
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agree.isChecked() == true) {
                    String name=nameInput.getText().toString();
                    String type=typeInput.getSelectedItem().toString();
                    String lowPrice=lowPriceInput.getText().toString();
                    String highPrice=highPriceInput.getText().toString();
                    String location=locationInput.getSelectedItem().toString();
                    String secondHand="";
                    switch (secondHandInput.getCheckedRadioButtonId()) {
                        case R.id.secondYes:
                            secondHand="y";
                        case R.id.secondNo:
                            secondHand="n";
                    }
                    String maxPeople=maxPeopleInput.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://140.121.197.130:8004/SurpriseU/attackServlet?organiser="+userID+"&name="+name+"&type="+type+"&lowPrice="+lowPrice+"&highPrice="+highPrice+"&location="+location+"&secondHand="+secondHand+"&maxPeople="+maxPeople+"",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        System.out.println("response : " + response);

                                        JSONObject jsonObject = new JSONObject(response);
                                        result = jsonObject.getString("result");
                                        changeID=jsonObject.getString("changeID");
                                        typeID=jsonObject.getString("typeID");
                                        if(result.equals("success")) {

                                            Bundle bd = new Bundle();
                                            bd.putString("name", nameInput.getText().toString());
                                            bd.putString("typeID", typeID);
                                            bd.putString("price", lowPriceInput.getText().toString() + "~" + highPriceInput.getText().toString());
                                            bd.putString("location", locationInput.getSelectedItem().toString());
                                            bd.putString("maxPeople",maxPeopleInput.getText().toString());
                                            bd.putString("changeID",changeID);
                                            switch (secondHandInput.getCheckedRadioButtonId()) {
                                                case R.id.secondYes:
                                                    bd.putString("secondHand", "是");
                                                case R.id.secondNo:
                                                    bd.putString("secondHand", "否");
                                            }
                                            display(bd);
                                        }else {
                                            System.out.println("result : " + result);
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
                    queue.add(stringRequest);   //把request丟進queue(佇列)
                }else{
                    new AlertDialog.Builder(AttackActivity.this)
                            .setTitle("提示")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("請閱讀並同意使用者服務條款")
                            .setPositiveButton("關閉視窗",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }

            }
        });
    }

    public void display(Bundle bd){
        Intent intent = new Intent();
        intent.setClass(AttackActivity.this, changeDetail.class);
        intent.putExtras(bd);
        startActivity(intent);
    }

    //讀取資料
    public void readData(){
        settings = getSharedPreferences(data,0);
        userID =  settings.getString("userID", "");
    }
}
