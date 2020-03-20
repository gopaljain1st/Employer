package com.example.employeetesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ApplyForJob extends AppCompatActivity {

EditText et_UserName,etLangauge,etPhoneNumber,etGooglePayNumber,etLocation;
Button btnApplyForJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_job);

        et_UserName=findViewById(R.id.et_user_name);
        etLangauge=findViewById(R.id.et_langauge);
        etPhoneNumber=findViewById(R.id.et_phone_number);
        etGooglePayNumber=findViewById(R.id.et_tez_number);
        etLocation=findViewById(R.id.et_location);



    }
}
