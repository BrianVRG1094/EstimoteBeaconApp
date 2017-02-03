package com.androidfinal.brian.beaconapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.MacAddress;
import com.estimote.sdk.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<Beacon> beaconList;
    private BeaconListViewAdapter adapter;
    private SharedPreferences preferences;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //=================== v Beacon Code v =======================
        ListView listView_Beacons = (ListView)findViewById(R.id.listView_Beacons);
        beaconList = new ArrayList<>();
        context = this;
        Generate3Beacons();
        /*The Beacon list will not be updated if new beacons come into range of the device, only when beacon settings
         have been changed.
        This application is currently designed to only work with the three beacons provided
        in an Estimote Beacon Kit.
         */

        preferences = this.getSharedPreferences("BeaconSettings", Context.MODE_PRIVATE);
        adapter = new BeaconListViewAdapter(context, R.layout.listiew_itemrow, beaconList,
                preferences);
        listView_Beacons.setAdapter(adapter);

        listView_Beacons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*When a beacon is selected in the listview, a new activity will be opened
                showing some information about the beacon, as well as it's current setting.
                The current setting can be found in the SharedReferences file that is created by the
                system for this application.
                */

                Beacon item = (Beacon)adapter.getItem(i);
                Intent intent = new Intent(context, BeaconSettingsActivity.class);
                intent.putExtra("selectedBeacon", item);
                intent.putExtra("beaconSetting", preferences.getString(getPreferenceName(item.getMajor()),
                        "No setting"));
                startActivityForResult(intent, 1);
            }
        });
        //End onCreate()
    }

    public String getPreferenceName(int major){
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append("settingFor_");
        sb.append(major);
        return sb.toString();
    }

    public void Generate3Beacons(){
        beaconList.add(new Beacon(UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                MacAddress.fromString("C4:31:42:CF:62:FA"), 25338, 17103, 1, 1));
        // ^This is Blueberry beacon
        beaconList.add(new Beacon(UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                MacAddress.fromString("C4:31:42:CF:62:FA"), 65159, 25684, 1, 1));
        // ^This is Mint beacon
        beaconList.add(new Beacon(UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                MacAddress.fromString("C4:31:42:CF:62:FA"), 45089, 53500, 1, 1));
        // ^This is Ice beacon
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                //Changes to the Beacon setings have been made
                //Refresh the textViews
                adapter.notifyDataSetChanged();
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                //No changes were made
            }
        }
    }
}
