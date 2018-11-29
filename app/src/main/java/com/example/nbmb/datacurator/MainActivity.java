package com.example.nbmb.datacurator;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nbmb.datacurator.data_alerts.DataAlertTask;
import com.example.nbmb.datacurator.data_alerts.DataUsage;
import com.example.nbmb.datacurator.database.DisableDataHelper;
import com.example.nbmb.datacurator.helpers.PermissionsHelper;
import com.example.nbmb.datacurator.service.NetworkStatusTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.example.nbmb.datacurator.service.ConnectHelper;
import com.example.nbmb.datacurator.service.WifiListAdapter;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static Context context;
    DisableDataHelper helper;
    List<ScanResult> results;
    int wifiCount;
    WifiManager wifiManager;
    String[] list;
    private final static String CHANNEL_ID = "com.example.nbmb.datacurator,network_status_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataCuratorNotification.createNotificationChannel(this.getApplicationContext(), CHANNEL_ID,
                "Network Status", "Constant notificaiton to update user about network status.",
                NotificationManager.IMPORTANCE_LOW);
        setAlert(10, "KB", 1, "minute");
        helper = new DisableDataHelper(this);
        PermissionsHelper.checkPermissions(Manifest.permission.RECEIVE_BOOT_COMPLETED, this);
        /*AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, DataCuratorService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        am.cancel(pi);
        am.set(AlarmManager.RTC_WAKEUP, 10000, pi);
        Log.d("startupService", "alarm created");*/
        /*ComponentName comp = new ComponentName(this.getPackageName(),
                DataCuratorService.class.getName());
        Intent intent = new Intent(this, DataCuratorService.class);
        intent.setComponent(comp);
        DataCuratorService.enqueueWork(this, intent);
        Log.d("startupService", "alarm created");*/
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

    private void setAlert(long dataLimit, String dataUnitText, long timeLimit, String timeUnitText) {
        String title = "Network Status.";
        String text = "Data usage: " + dataLimit + dataUnitText + " in the last " + timeLimit + " " + timeUnitText;

        NotificationCompat.Builder notificationBuilder= DataCuratorNotification.createNotificationBuilder(this.getApplicationContext(),
                title, text);
        notificationBuilder.setShowWhen(false);
        notificationBuilder.setOnlyAlertOnce(true);

        DataCuratorNotification.setTapAction(PendingIntent.getActivity(this, 0,
                new Intent(Settings.ACTION_WIRELESS_SETTINGS), PendingIntent.FLAG_UPDATE_CURRENT),
                notificationBuilder);

        NetworkStatusTask task = new NetworkStatusTask(new DataUsage(this.getApplicationContext()),
                notificationBuilder, NotificationManagerCompat.from(this.getApplicationContext()));

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(task, timeLimit, timeLimit, TimeUnit.MINUTES);
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

    public void checkPermissions(String permission)
    {
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permission)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{permission},
                        100);

            }
        } else {
            Log.d("RESULTS", "ALREADY GRANTED "+permission);
        }
    }
  
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("RESULTS","GRANTED");
                } else {
                    Log.d("RESULTS","DENIED");
                }
                return;
            }
        }
    }

    void scanWifi()
    {
        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);
                scanResults();
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, intentFilter);

        boolean success = wifiManager.startScan();
        if (!success) {
            scanResults();
        }
    }
  
    private void scanResults() {
        results = wifiManager.getScanResults();
        wifiCount = results.size();
        WifiInfo current = wifiManager.getConnectionInfo();
        Log.d("RESULTS", "Results "+results.toString());
        for(int index = 0; index<wifiCount;index++)
        {
            list = helper.getNetworkItem(results.get(index).SSID.replaceAll("\"",""));
            if(list.length>0 )
            {
                if(current.getSSID()!= null && !current.getSSID().equals(results.get(index).SSID))
                {
                    ConnectHelper.connectWifi(this,list[0],list[1],list[2]);
                }
                else if(current.getSSID() == null)
                {
                    ConnectHelper.connectWifi(this,list[0],list[1],list[2]);
                }
            }

        }
    }
}
