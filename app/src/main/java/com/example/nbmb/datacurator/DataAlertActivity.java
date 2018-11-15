package com.example.nbmb.datacurator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import data_alerts.DataUsage;

public class DataAlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_alert);

    }

    @Override
    protected void onStart() {
        super.onStart();
        DataUsage da = new DataUsage();
        Log.d("datausage", "Data usage: " + da.getDataUsage() + " bytes");
    }
}
