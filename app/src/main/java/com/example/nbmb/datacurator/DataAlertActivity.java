package com.example.nbmb.datacurator;

import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.nbmb.datacurator.data_alerts.DataAlertTask;
import com.example.nbmb.datacurator.data_alerts.DataUsage;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DataAlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_alert);
        DataCuratorNotification.createNotificationChannel(this.getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
        DataUsage da = new DataUsage(this.getApplicationContext());
        Log.d("datausage", "Data usage: " + da.getDataUsage() + " bytes");
    }

    public void initializeClicked(View v) {
        EditText dataLimitEdit = findViewById(R.id.numBytesEditText);
        EditText timeLimitEdit = findViewById(R.id.numTimeEditText);

        if(dataLimitEdit.getText().toString() == "" || timeLimitEdit.getText().toString() == ""){
            Toast.makeText(this, "Please enter a data limit and time period.", Toast.LENGTH_SHORT).show();
            return;
        }

        Spinner dataUnitSpinner = findViewById(R.id.dataSizeSpinner);
        Spinner timeUnitSpinner = findViewById(R.id.timeUnitSpinner);
        Switch enableSwitch = findViewById(R.id.enableAlertSwitch);

        long dataLimit = Long.parseLong(dataLimitEdit.getText().toString());
        long timeLimit = Long.parseLong(timeLimitEdit.getText().toString());
        String timeUnitText = timeUnitSpinner.getSelectedItem().toString();
        String dataUnitText = dataUnitSpinner.getSelectedItem().toString();
        boolean enabled = enableSwitch.isChecked();

        TimeUnit timeUnit = getTimeUnitFromString(timeUnitText);
        long dataUnit = getDataUnitFromString(dataUnitText);

        Log.d("alertInitialized", "Alert: " + dataLimit + dataUnitText + " per "
                + timeLimit + " " + timeUnitText + " isEnabled:" + enabled);
        Log.d("alertInitialized", "Alert: " + dataUnit  + " " + timeUnit.toString());

        if(enabled)
            setAlert(dataLimit, dataUnitText, timeLimit, timeUnitText);
    }

    private void setAlert(long dataLimit, String dataUnitText, long timeLimit, String timeUnitText) {
        String title = "Data alert!";
        String text = "Data usage: " + dataLimit + dataUnitText + " in the last " + timeLimit + " " + timeUnitText;

        NotificationCompat.Builder notificationBuilder= DataCuratorNotification.createNotificationBuilder(this.getApplicationContext(),
                title, text);

        DataCuratorNotification.setTapAction(PendingIntent.getActivity(this, 0,
                new Intent(Settings.ACTION_WIRELESS_SETTINGS), PendingIntent.FLAG_UPDATE_CURRENT),
                notificationBuilder);

        DataAlertTask task = new DataAlertTask(dataLimit, timeLimit,
                getDataUnitFromString(dataUnitText), getTimeUnitFromString(timeUnitText),
                dataUnitText, timeUnitText, new DataUsage(this.getApplicationContext()),
                notificationBuilder, NotificationManagerCompat.from(this.getApplicationContext()));

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(task, timeLimit, timeLimit, getTimeUnitFromString(timeUnitText));
    }

    public TimeUnit getTimeUnitFromString(String selection) {
        switch(selection) {
            case "Seconds":
                return TimeUnit.SECONDS;
            case "Minutes":
                return TimeUnit.MINUTES;
            case "Hours":
                return TimeUnit.HOURS;
            case "Days":
                return TimeUnit.DAYS;
            default:
                return TimeUnit.MINUTES;
        }
    }

    public long getDataUnitFromString(String selection) {
        switch(selection) {
            case "B":
                return 1;
            case "KB":
                return 1000;
            case "MB":
                return 1000000;
            case "GB":
                return 1000000000;
            default:
                return 1000000;
        }
    }
}
