package com.example.nbmb.datacurator.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nbmb.datacurator.R;

public class WifiListAdapter extends ArrayAdapter {
    String[] networks;
    Context context;
    public WifiListAdapter(Context context, String[] networks) {
        super(context, R.layout.list_layout);
        this.networks = networks;
        this.context = context;
    }
    @Override
    public int getCount() {
        return networks.length;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null)
        {
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflate.inflate(R.layout.list_layout, parent, false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textItem);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(networks[position].toString());
        return convertView;
    }

    static class ViewHolder
    {
        TextView textView;
    }

}
