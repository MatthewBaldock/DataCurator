package com.example.nbmb.datacurator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detected_networks);
        context = getApplicationContext();
        manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

        wifiCount = 0;
        setList();
    }


    public void setList(){

       registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                results = manager.getScanResults();
                wifiCount=results.size();
            }
        },new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        manager.startScan();
            try {
                wifiCount = wifiCount - 1;
                while (wifiCount >= 0) {
                    Log.d("RESULTS", results.get(wifiCount).SSID.toString());
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
                 AlertDialog dialog = builder.create();**/

            }
        });
    }
}
