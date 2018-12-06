package com.example.nbmb.datacurator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nbmb.datacurator.database.DisableDataHelper;
import com.example.nbmb.datacurator.service.ConnectHelper;
import com.example.nbmb.datacurator.service.WifiListAdapter;

public class SavedNetworks extends AppCompatActivity {
    static DisableDataHelper helper;
    WifiListAdapter adapter;
    Context context;
    int listIndex;
    String[] savedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_networks);
        helper = new DisableDataHelper(this);
        context = this.getApplicationContext();
        savedList = helper.getNetworkList();
        adapter = new WifiListAdapter(SavedNetworks.this,savedList);
        setList();
    }

    public void setList(){
        ListView list = (ListView) findViewById(R.id.networkList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listIndex = i;
                Log.d("RESULTS","Index:"+i);
                AlertDialog.Builder builder = new AlertDialog.Builder(SavedNetworks.this);
                TextView text = new TextView(SavedNetworks.this);

                text.setTextSize(20);
                text.setText(getResources().getString(R.string.connectToDetectedNetworksButtonText, savedList[listIndex]));
                builder.setView(text);
                builder.setNeutralButton("Connect",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String[] network = helper.getNetworkItem(savedList[listIndex]);
                        ConnectHelper.connectWifi(context,network[0],network[1],network[2]);

                    }
                });
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {
                        helper.removeNetworkItem(savedList[listIndex].toString());
                        ConnectHelper.removeWifi(context,savedList[listIndex].toString());
                    }
                });
                builder.setNegativeButton("Disassociate", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        ConnectHelper.removeWifi(context,savedList[listIndex].toString());
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();



            }
        });

    }
}
