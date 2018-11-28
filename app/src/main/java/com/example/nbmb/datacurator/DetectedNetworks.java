package com.example.nbmb.datacurator;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nbmb.datacurator.database.DisableDataHelper;
import com.example.nbmb.datacurator.service.WifiListAdapter;

import java.util.List;

public class DetectedNetworks extends AppCompatActivity {
    static WifiManager manager;
    WifiListAdapter adapter;
    static Context context;
    List<ScanResult> results;
    int wifiCount;
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detected_networks);
        context = getApplicationContext();
       // manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager  = (WifiManager)
                context.getSystemService(Context.WIFI_SERVICE);
        wifiCount = 0;

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        checkPermissions(Manifest.permission.CHANGE_WIFI_STATE);
        checkPermissions(Manifest.permission.ACCESS_WIFI_STATE);
        checkPermissions(Manifest.permission.ACCESS_COARSE_LOCATION);
        setList();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("RESULTS","GRANTED");
                } else {
                    Log.d("RESULTS","DENIED");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
public void checkPermissions(String permission)
{
    if (ContextCompat.checkSelfPermission(this,
            permission)
            != PackageManager.PERMISSION_GRANTED) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                permission)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    100);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    } else {
        Log.d("RESULTS", "ALREADY GRANTED "+permission);
    }
}
    public void setList(){

        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    scanSuccess();
                } else {
                    // scan failure handling
                    scanFailure();
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, intentFilter);

        boolean success = wifiManager.startScan();
        if (!success) {
            // scan failure handling
            scanFailure();
        }



      /*  int isLocation =0;
        try{
           isLocation =  Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
        }
        catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("RESULTS","Is location on: "+isLocation + " OFf Int"+Settings.Secure.LOCATION_MODE_OFF );
        if(isLocation == Settings.Secure.LOCATION_MODE_OFF)
        {
            Intent intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

       registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                results = manager.getScanResults();
                wifiCount=results.size();

                Log.d("RESULTS", results.toString());
                Log.d("RESULTS", "Size: "+results.size());
            }
        },new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        manager.startScan();

            try {
                wifiCount = wifiCount - 1;
                    Log.d("RESULTS", results.toString());
                while (wifiCount >= 0) {
                    Log.d("RESULTS", results.get(wifiCount).SSID);
                    wifiCount=wifiCount -1;
                }
            }
            catch (Exception e){
                Log.d("Wifi", e.getMessage());
            }


        ListView list = (ListView) findViewById(R.id.networkList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /**   AlertDialog.Builder builder;// = new AlertDialog.Builder//);getActivity());

                 builder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                 // User clicked OK button
                 }
                 });
                 builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                 // User cancelled the dialog
                 }
                 });
                 AlertDialog dialog = builder.create();

            }
        });**/
    }
    private void scanSuccess() {
        List<ScanResult> results = wifiManager.getScanResults();
        Log.d("RESULTS","ANDROID: "+results.size());
    }

    private void scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        List<ScanResult> results = wifiManager.getScanResults();

        Log.d("RESULTS","ANDROID: "+results.size());
        //Log.d("RESULTS","ANDROID: "+results[0].SSID);
    }
}
