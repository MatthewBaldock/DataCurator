package com.example.nbmb.datacurator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nbmb.datacurator.data_alerts.DataUsage;
import com.example.nbmb.datacurator.database.DisableDataHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void disableView(View view)
    {
        Intent intent = new Intent(this, WifiDisable.class);
        startActivity(intent);
    }

    public void dataUsageClicked(View view) {
        Intent intent = new Intent(this, DataAlertActivity.class);
        startActivity(intent);
    }
    public void savedNetworks(View view)
    {
        Intent intent = new Intent(this, SavedNetworks.class);
        startActivity(intent);
    }
    public void detectedNetworks(View view)
    {
        Intent intent = new Intent(this, DetectedNetworks.class);
        startActivity(intent);
    }
}
