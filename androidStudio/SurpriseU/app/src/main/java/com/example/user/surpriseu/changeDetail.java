package com.example.user.surpriseu;

import android.os.Bundle;
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
    private TextView nowpeople,price,location,secondHand,process,messegeBoard;
    private Button joinButton;
    private RequestQueue queue;
    private String changeID,type;
    private ImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_detail);
        nowpeople=(TextView)findViewById(R.id.nowPeople);
        price=(TextView)findViewById(R.id.price);
        location=(TextView)findViewById(R.id.location);
        secondHand=(TextView)findViewById(R.id.secondHand);
        process=(TextView)findViewById(R.id.process);
        picture=(ImageView)findViewById(R.id.picture);
        messegeBoard=(TextView)findViewById(R.id.messageBoard);
        Bundle bd=getIntent().getExtras();
        type=bd.getString("type");
        price.setText(bd.getString("lowPrice")+"~"+bd.getString("highPrice"));
        location.setText(bd.getString("location"));
        changeID=bd.getString("changeID");

        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.109:8080/SurpriseU/changeDetailServlet?changeID="+changeID+"&state=getDetail",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response : " + response);

                            JSONObject jsonObject = new JSONObject(response);

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
    }
}
