package com.androidfinal.brian.beaconapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estimote.sdk.Beacon;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Brian on 6/11/2016.
 */

public class BeaconSettingsAdapter extends BaseAdapter{

    Context context;
    int resourceID;
    List<BeaconSettingsClass> beaconSettingsList;

    public BeaconSettingsAdapter(Context context, int resourceID, List<BeaconSettingsClass> beaconSettingsList){
        this.context = context;
        this.resourceID = resourceID;
        this.beaconSettingsList = beaconSettingsList;
    }

    @Override
    public int getCount() {
        return beaconSettingsList.size();
    }

    @Override
    public Object getItem(int i) {
        return beaconSettingsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Get LayoutInflater
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflate
        view = inflater.inflate(resourceID, null);

        //Get reference to controls
        TextView header = (TextView)view.findViewById(R.id.textView_settingheader);
        TextView content = (TextView)view.findViewById(R.id.textView_settingValue);
        BeaconSettingsClass bsc = beaconSettingsList.get(i);

        //Set control's properties
        header.setText(bsc.getHeader());
        content.setText(bsc.getContent());

        return view;
    }
}
