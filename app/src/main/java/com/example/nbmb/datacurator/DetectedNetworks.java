package com.example.nbmb.datacurator;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.app.AlertDialog.Builder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nbmb.datacurator.database.DisableDataHelper;
import com.example.nbmb.datacurator.helpers.PermissionsHelper;
import com.example.nbmb.datacurator.service.ConnectHelper;
import com.example.nbmb.datacurator.service.WifiListAdapter;

import java.util.List;

public class DetectedNetworks extends AppCompatActivity {
    WifiListAdapter adapter;
    static Context context;
    DisableDataHelper helper;
    List<ScanResult> results;
    int wifiCount;
    int wifiIndex;
    WifiManager wifiManager;
    String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detected_networks);
        context = getApplicationContext();
        helper = new DisableDataHelper(context);
        wifiManager  = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiCount = 0;
        wifiIndex = 0;
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        PermissionsHelper.checkPermissions(Manifest.permission.CHANGE_WIFI_STATE, this);
        PermissionsHelper.checkPermissions(Manifest.permission.ACCESS_WIFI_STATE, this);
        PermissionsHelper.checkPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, this);
        scanWifi();

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
                if (success) {
                    scanSuccess();
                } else {
                    scanFailure();
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, intentFilter);

        boolean success = wifiManager.startScan();
        if (!success) {
            scanFailure();
        }
    }

    public void setList(){

        if(list.length>0) {
            adapter = new WifiListAdapter(context,list );
            ListView listView = (ListView) findViewById(R.id.networkList);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    wifiIndex = i;
                    Builder builder = new Builder(DetectedNetworks.this);
                    Builder passBuild = new Builder(DetectedNetworks.this);
                    TextView text = new TextView(DetectedNetworks.this);
                    EditText password = new EditText(DetectedNetworks.this);

                    text.setTextSize(20);
                    text.setText("Connect to or Save "+list[wifiIndex].toString()+" Network");
                    builder.setView(text);
                    passBuild.setTitle("Password");
                    passBuild.setView(password);
                    builder.setNeutralButton("Connect",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Builder passBuild = new Builder(DetectedNetworks.this);

                            final EditText password = new EditText(DetectedNetworks.this);
                            passBuild.setTitle("Password");
                            passBuild.setView(password);
                            passBuild.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    dialog.cancel();

                                }
                            });
                            passBuild.setPositiveButton("Ok",new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    ConnectHelper.connectWifi(context,list[wifiIndex].toString(),password.getText().toString(),results.get(wifiIndex).capabilities.toString());

                                }
                            });
                            AlertDialog passDialog = passBuild.create();
                            passDialog.show();
                        }
                    });
                     builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
                     {
                         public void onClick(DialogInterface dialog, int id)
                         {
                             Builder passBuild = new Builder(DetectedNetworks.this);

                             final EditText password = new EditText(DetectedNetworks.this);
                             passBuild.setTitle("Password");
                             passBuild.setView(password);
                             passBuild.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
                             {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    dialog.cancel();

                                }
                             });
                              passBuild.setPositiveButton("Ok",new DialogInterface.OnClickListener()
                              {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    helper.addNetwork(list[wifiIndex].toString(),password.getText().toString(),results.get(wifiIndex).capabilities.toString());

                                }
                              });
                             AlertDialog passDialog = passBuild.create();
                             passDialog.show();
                         }
                     });
                     builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                     {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.cancel();
                        }
                     });

                    AlertDialog passDialog = passBuild.create();
                     AlertDialog dialog = builder.create();
                     dialog.show();



                }
            });
        }
    }
    private void scanSuccess() {
       results = wifiManager.getScanResults();
        wifiCount = results.size();
        list = new String[wifiCount];
        Log.d("RESULTS", "Results "+results.toString());
        for(int index = 0; index<wifiCount;index++)
        {
            list[index] = results.get(index).SSID.toString();
        }
        setList();
    }

    private void scanFailure() {
        results = wifiManager.getScanResults();
        wifiCount = results.size();
        list = new String[wifiCount];
        for(int index = 0; index<wifiCount;index++)
        {
            list[index] = results.get(index).SSID.toString();
        }
        setList();
    }
}
