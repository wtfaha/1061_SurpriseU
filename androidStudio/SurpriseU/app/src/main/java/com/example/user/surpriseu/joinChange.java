package com.example.user.surpriseu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class joinChange extends AppCompatActivity {

    private EditText name,address,phone,mailAddress,want1,want2,want3,dontWant1,dontWant2,dontWant3;
    private Button join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_change);
        name=(EditText)findViewById(R.id.nameInput);
        address=(EditText)findViewById(R.id.addressInput);
        phone=(EditText)findViewById(R.id.phoneInput);
        mailAddress=(EditText)findViewById(R.id.mailAddressInput);
        want1=(EditText)findViewById(R.id.wantInput1);
        want2=(EditText)findViewById(R.id.wantInput2);
        want3=(EditText)findViewById(R.id.wantInput3);
        dontWant1=(EditText)findViewById(R.id.dontWantInput1);
        dontWant2=(EditText)findViewById(R.id.dontWantInput2);
        dontWant3=(EditText)findViewById(R.id.dontWantInput3);
        join=(Button)findViewById(R.id.join);
    }
}
