package com.example.nbmb.datacurator;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.nbmb.datacurator.database.DisableDataHelper;
import com.example.nbmb.datacurator.service.WifiDisableService;

import java.text.DateFormat;
import java.util.Date;

public class WifiDisable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_disable);
    }

    public void toggleDisable(View view)
    {
        DisableDataHelper ddhlp = new DisableDataHelper(this);
        ToggleButton toggle = findViewById(R.id.toggleButton);
        TextView duration = findViewById(R.id.duration);
        String onOff = "OFF";

        if(toggle.isChecked())
        {

            String startTime = (new Date()).toString();
            ddhlp.toggleOn(duration.getText().toString(),startTime);
            onOff = "ON";
            Intent service = new Intent();
            service.putExtra("duration",duration.getText().toString());
            service.putExtra("onoff",onOff);

            WifiDisableService.enqueueWork(this.getApplicationContext(),service);
        }
        else if (!toggle.isChecked())
        {
            onOff = "OFF";
            ddhlp.toggleOff();
        }


    }
    public void quickDisable(View view)
    {

    }
}
