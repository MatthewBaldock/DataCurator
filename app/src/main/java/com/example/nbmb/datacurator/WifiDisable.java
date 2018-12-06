package com.example.nbmb.datacurator;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.nbmb.datacurator.database.DisableDataHelper;
import com.example.nbmb.datacurator.service.WifiDisableService;


import java.util.Date;

public class WifiDisable extends AppCompatActivity {
    WifiDisableService wds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_disable);
        wds = new WifiDisableService();
        buttonState();
    }

    public void toggleDisable(View view)
    {
        DisableDataHelper ddhlp = new DisableDataHelper(this);

        ToggleButton toggle = findViewById(R.id.toggleButton);
        TextView duration = findViewById(R.id.duration);
        Spinner type = findViewById(R.id.durationType);
        String period = type.getSelectedItem().toString();
        String onOff = "OFF";
        Intent service = new Intent();
        if(toggle.isChecked())
        {

            String startTime = (new Date()).toString();
            ddhlp.toggleOn(duration.getText().toString(),startTime);
            onOff = "ON";
        }
        else if (!toggle.isChecked())
        {
            onOff = "OFF";
            ddhlp.toggleOff();

        }

        service.putExtra("duration",Integer.parseInt(duration.getText().toString()));
        service.putExtra("onoff",onOff);
        service.putExtra("period",period);
        wds.onHandleWork(this.getApplicationContext(),service);
        Log.d("TASK", "Returned");
    }
    public void buttonState()
    {
        ToggleButton toggle = findViewById(R.id.toggleButton);
        TextView duration = findViewById(R.id.duration);
        DisableDataHelper ddhlp = new DisableDataHelper(this);
        String durVal = Integer.toString(ddhlp.getDuration());
        String onOff = ddhlp.getStatus();
        if(onOff.equals("ON"))
        {
            toggle.setChecked(true);
        }
        else if(onOff.equals("OFF"))
        {
            toggle.setChecked(false);
        }
        duration.setText(durVal);
        duration.invalidate();
        toggle.invalidate();
    }
    public void quickDisable(View view)
    {

    }
    @Override
    public void onResume(){
        super.onResume();
        buttonState();
    }
}
