package com.example.nbmb.datacurator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nbmb.datacurator.database.DisableDataHelper;
import com.example.nbmb.datacurator.service.WifiListAdapter;

public class SavedNetworks extends AppCompatActivity {
    static DisableDataHelper helper;
    WifiListAdapter adapter;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_networks);
        helper = new DisableDataHelper(this);
        context = this.getApplicationContext();
        adapter = new WifiListAdapter(SavedNetworks.this,helper.getNetworkList());
        setList();
    }

    public void setList(){
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
