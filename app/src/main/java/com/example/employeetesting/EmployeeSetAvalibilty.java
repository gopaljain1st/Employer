package com.example.employeetesting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EmployeeSetAvalibilty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_set_avalibilty);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("EmployeeSeatAvailibilty");
    }
}
