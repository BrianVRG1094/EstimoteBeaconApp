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

import java.util.List;

/**
 * Created by Brian on 5/31/2016.
 */

public class BeaconListViewAdapter extends BaseAdapter {
    Context context;
    int resourceID;
    List<Beacon> beaconList;
    SharedPreferences sharedPref;

    public BeaconListViewAdapter(Context context, int resourceID, List<Beacon> beaconList, SharedPreferences sharedPref){
        this.context = context;
        this.resourceID = resourceID;
        this.beaconList = beaconList;
        this.sharedPref = sharedPref;
    }

    @Override
    public int getCount() {
        return beaconList.size();
    }

    @Override
    public Object getItem(int i) {
        return beaconList.get(i);
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
        ImageView imageView_Beacon = (ImageView) view.findViewById(R.id.imageView_Beacon);
        TextView textView_CurrentSetting = (TextView)view.findViewById(R.id.textView_CurrentSetting);

        //Set control's properties
        Beacon beacon = beaconList.get(i);
        String preferenceName;
        StringBuilder sb;

        //Setting the image for the beacon is a bit complicated.
        //To do this, I first get the major and minor values that are specific to the beacon
        //currently being handled.
        //Then, I identify it's color.
        //To get the image, there are different ways to go about this.
        //In a previous project, the image resourceID was passed to the adapter when initialized.
        //That resourceID was a property of a custom class.
        //Here, the object being used (Beacon) is predefined and does not have an image property.
        //So, instead I directly get the correct image from the drawable folder once the beacon has been identified.
        if(beacon.getMajor() == 65159
                && beacon.getMinor() == 25684){
            // ^ This is the light green beacon
            imageView_Beacon.setImageResource(R.drawable.greenbeacon);
            preferenceName = getPreferenceName(65159);
        }

        else if(beacon.getMajor() == 45089
                && beacon.getMinor() == 53500){
            // ^ This is the light blue beacon
            imageView_Beacon.setImageResource(R.drawable.bluebeacon);
            preferenceName = getPreferenceName(45089);
        }

        else if(beacon.getMajor() == 25338
                && beacon.getMinor() == 17103){
            // ^ This is the purple beacon
            imageView_Beacon.setImageResource(R.drawable.purplebeacon);
            preferenceName = getPreferenceName(25338);
        }

        else{
            //This is here in case something goes wrong, not because an a new beacon could show up.
            //Future code may be implemented for more than the current 3 beacons however.
            imageView_Beacon.setImageResource(R.drawable.greybeacon);
            preferenceName = "Unidentified Beacon";
        }

        //Handler for "No Setting"
        if(sharedPref.getString(preferenceName, "").equals("No Setting")){
            sb = new StringBuilder(sharedPref.getString(preferenceName, "No Setting"));
        }
        else {
            sb = new StringBuilder("Open ");
            sb.append(sharedPref.getString(preferenceName, "No Setting"));
        }
        textView_CurrentSetting.setText(sb.toString());
        return view;
    }

    private String getPreferenceName(int major){
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append("settingFor_");
        sb.append(major);
        return sb.toString();
    }
}
