package com.example.nbmb.datacurator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.nbmb.datacurator.database.DisableDataHelper;

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
        if(toggle.isChecked())
        {

            String startTime = (new Date()).toString();
            ddhlp.toggleOn(duration.getText().toString(),startTime);
        }
        else if (!toggle.isChecked())
        {
            ddhlp.toggleOff();
        }
        Log.d("TOGGLEBUTTON", "toggleDisable: "+toggle.isActivated());
    }
    public void quickDisable(View view)
    {

    }
}
